package com.rehman.elearning.service.impl;

import com.rehman.elearning.constants.ErrorEnum;
import com.rehman.elearning.constants.PaymentEnum;
import com.rehman.elearning.constants.UserCreatedBy;
import com.rehman.elearning.exceptions.ResourceNotFoundException;
import com.rehman.elearning.model.Course;
import com.rehman.elearning.model.Payment;
import com.rehman.elearning.model.Student;
import com.rehman.elearning.repository.CourseRepository;
import com.rehman.elearning.repository.PaymentRepository;
import com.rehman.elearning.repository.StudentRepository;
import com.rehman.elearning.rest.dto.inbound.PaymentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.PaymentResponseDTO;
import com.rehman.elearning.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final String JAZZCASH_INTEGRITY_SALT = "22954z3v21";
    private static final String DATE_FORMAT = "yyyyMMddHHmmss";

    private final PaymentRepository paymentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public PaymentResponseDTO createPayment(Long courseId, PaymentRequestDTO paymentRequestDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.COURSE_NOT_FOUND));
        Student student = studentRepository.findById(paymentRequestDto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.STUDENT_NOT_FOUND));

        Payment payment = new Payment();
        payment.setTransactionId(paymentRequestDto.getTransactionId());
        payment.setStatus(PaymentEnum.PENDING.name());
        payment.setAmount(paymentRequestDto.getAmount());
        payment.setCreatedBy(UserCreatedBy.Self);
        payment.setCourse(course);
        payment.setStudent(student);
        Payment savedPayment = paymentRepository.save(payment);

        Map<String, String> paymentRequest = createJazzCashPaymentRequest(paymentRequestDto);
        paymentRequest.put("pp_SecureHash", generateSecureHash(paymentRequest));

        return mapToPaymentResponseDTO(savedPayment, paymentRequest);
    }

    private Map<String, String> createJazzCashPaymentRequest(PaymentRequestDTO paymentRequestDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        Map<String, String> paymentRequest = new HashMap<>();
        paymentRequest.put("pp_Version", "1.0");
        paymentRequest.put("pp_TxnType", "SALE");
        paymentRequest.put("pp_Language", "EN");
        paymentRequest.put("pp_MerchantID", "MC134536"); // Actual JazzCash Merchant ID
        paymentRequest.put("pp_Password", "su1t9xb12y"); // Actual JazzCash Password
        paymentRequest.put("pp_BankID", "TBANK");
        paymentRequest.put("pp_ProductID", "RETL");
        paymentRequest.put("pp_TxnRefNo", "T" + System.currentTimeMillis());
        paymentRequest.put("pp_Amount", String.valueOf(paymentRequestDto.getAmount()));
        paymentRequest.put("pp_TxnCurrency", "PKR");
        paymentRequest.put("pp_TxnDateTime", LocalDateTime.now().format(formatter));
        paymentRequest.put("pp_BillReference", "billRef");
        paymentRequest.put("pp_Description", "Course Payment");
        paymentRequest.put("pp_TxnExpiryDateTime", LocalDateTime.now().plusHours(1).format(formatter));
        paymentRequest.put("pp_ReturnURL", "http://127.0.0.1:8080/success");
        paymentRequest.put("ppmpf_1", "1");
        paymentRequest.put("ppmpf_2", "2");
        paymentRequest.put("ppmpf_3", "3");
        paymentRequest.put("ppmpf_4", "4");
        paymentRequest.put("ppmpf_5", "5");

        return paymentRequest;
    }

    private String generateSecureHash(Map<String, String> paymentRequest) {
        try {
            String data = paymentRequest.entrySet().stream()
                    .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
                    .sorted(Map.Entry.comparingByKey())
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&")) + "&pp_IntegritySalt=" + JAZZCASH_INTEGRITY_SALT;

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(JAZZCASH_INTEGRITY_SALT.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) hexString.append(String.format("%02x", b));
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating secure hash", e);
        }
    }

    @Override
    public PaymentResponseDTO getPaymentById(Long courseId, Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.PAYMENT_NOT_FOUND));
        return mapToPaymentResponseDTO(payment);
    }

    @Override
    public PaymentResponseDTO updatePayment(Long courseId, Long id, PaymentRequestDTO paymentRequestDto) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.PAYMENT_NOT_FOUND));
        payment.setTransactionId(paymentRequestDto.getTransactionId());
        payment.setStatus(paymentRequestDto.getPaymentStatus());
        payment.setAmount(paymentRequestDto.getAmount());
        Payment updatedPayment = paymentRepository.save(payment);
        return mapToPaymentResponseDTO(updatedPayment);
    }

    @Override
    public void deletePayment(Long courseId, Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.PAYMENT_NOT_FOUND));
        paymentRepository.delete(payment);
    }

    @Override
    public List<PaymentResponseDTO> getAllPaymentsForCourse(Long courseId) {
        List<Payment> payments = paymentRepository.findByCourseId(courseId);
        return payments.stream().map(this::mapToPaymentResponseDTO).collect(Collectors.toList());
    }

    @Override
    public PaymentResponseDTO verifyPayment(String transactionId) {
        // Call JazzCash API to verify payment status
        // Example: Simulating verification (implement actual verification logic here)
        Optional<Payment> paymentOptional = paymentRepository.findByTransactionId(transactionId);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            return mapToPaymentResponseDTO(payment);  // Returning updated payment status
        } else {
            throw new ResourceNotFoundException(ErrorEnum.PAYMENT_NOT_FOUND);
        }
    }

    private PaymentResponseDTO mapToPaymentResponseDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getTransactionId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt().toLocalDateTime(),
                payment.getUpdatedAt().toLocalDateTime(),
                payment.getCourse().getId(),
                payment.getStudent().getUserId()
        );
    }

    private PaymentResponseDTO mapToPaymentResponseDTO(Payment payment, Map<String, String> paymentRequest) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getTransactionId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt().toLocalDateTime(),
                payment.getUpdatedAt().toLocalDateTime(),
                payment.getCourse().getId(),
                payment.getStudent().getUserId(),
                paymentRequest
        );
    }
}




//package com.sarfaraz.elearning.service.impl;
//
//import com.sarfaraz.elearning.constants.ErrorEnum;
//import com.sarfaraz.elearning.constants.PaymentEnum;
//import com.sarfaraz.elearning.constants.UserCreatedBy;
//import com.sarfaraz.elearning.exceptions.ResourceNotFoundException;
//import com.sarfaraz.elearning.model.Course;
//import com.sarfaraz.elearning.model.Payment;
//import com.sarfaraz.elearning.model.Student;
//import com.sarfaraz.elearning.repository.CourseRepository;
//import com.sarfaraz.elearning.repository.PaymentRepository;
//import com.sarfaraz.elearning.repository.StudentRepository;
//import com.sarfaraz.elearning.rest.dto.inbound.PaymentRequestDTO;
//import com.sarfaraz.elearning.rest.dto.outbound.PaymentResponseDTO;
//import com.sarfaraz.elearning.service.PaymentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class PaymentServiceImpl implements PaymentService {
//
//    private static final String JAZZCASH_INTEGRITY_SALT = "22954z3v21";
//
//    @Autowired
//    private PaymentRepository paymentRepository;
//
//    @Autowired
//    private CourseRepository courseRepository;
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Override
//    public PaymentResponseDTO createPayment(Long courseId, PaymentRequestDTO paymentRequestDto) {
//        // Step 1: Validate and retrieve course and student data
//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.COURSE_NOT_FOUND));
//        Student student = studentRepository.findById(paymentRequestDto.getStudentId())
//                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.STUDENT_NOT_FOUND));
//
//        // Step 2: Prepare Payment object and save it
//        Payment payment = new Payment();
//        payment.setTransactionId(paymentRequestDto.getTransactionId());
//        payment.setStatus(String.valueOf(PaymentEnum.PENDING));
//        payment.setAmount(paymentRequestDto.getAmount());
//        payment.setCreatedBy(UserCreatedBy.Self);
//        payment.setCourse(course);
//        payment.setStudent(student);
//        Payment savedPayment = paymentRepository.save(payment);
//
//        // Step 3: Prepare the JazzCash payment request data
//        Map<String, String> paymentRequest = createJazzCashPaymentRequest(paymentRequestDto);
//
//        // Step 4: Generate SecureHash for JazzCash request
//        String secureHash = generateSecureHash(paymentRequest);
//        paymentRequest.put("pp_SecureHash", secureHash);
//
//        // Step 5: Call to JazzCash API can be added here (pending integration)
//
//        // Returning saved payment data as part of the response DTO
//        return mapToPaymentResponseDTO(savedPayment, paymentRequest);
//    }
//
//    private Map<String, String> createJazzCashPaymentRequest(PaymentRequestDTO paymentRequestDto) {
//        Map<String, String> paymentRequest = new HashMap<>();
//        paymentRequest.put("pp_Version", "1.0");
//        paymentRequest.put("pp_TxnType", "SALE"); // Specify transaction type
//        paymentRequest.put("pp_Language", "EN");
//        paymentRequest.put("pp_MerchantID", "MC134536"); // Replace with actual JazzCash Merchant ID
//        paymentRequest.put("pp_Password", "su1t9xb12y"); // Replace with actual JazzCash Password
//        paymentRequest.put("pp_BankID", "TBANK");
//        paymentRequest.put("pp_ProductID", "RETL");
//        paymentRequest.put("pp_TxnRefNo", "T" + System.currentTimeMillis()); // Unique reference number
//        paymentRequest.put("pp_Amount", String.valueOf(paymentRequestDto.getAmount()));
//        paymentRequest.put("pp_TxnCurrency", "PKR");
//        paymentRequest.put("pp_TxnDateTime", new Date().toString().replace(" ", ""));
//        paymentRequest.put("pp_BillReference", "billRef");
//        paymentRequest.put("pp_Description", "Course Payment");
//        paymentRequest.put("pp_TxnExpiryDateTime", new Date(System.currentTimeMillis() + 3600000).toString().replace(" ", ""));
//        paymentRequest.put("pp_ReturnURL", "http://127.0.0.1:8080/success");
//        paymentRequest.put("ppmpf_1", "1");
//        paymentRequest.put("ppmpf_2", "2");
//        paymentRequest.put("ppmpf_3", "3");
//        paymentRequest.put("ppmpf_4", "4");
//        paymentRequest.put("ppmpf_5", "5");
//
//        return paymentRequest;
//    }
//
//    private String generateSecureHash(Map<String, String> paymentRequest) {
//        try {
//            String sortedString = paymentRequest.entrySet().stream()
//                    .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
//                    .sorted(Map.Entry.comparingByKey())
//                    .map(entry -> entry.getKey() + "=" + entry.getValue())
//                    .collect(Collectors.joining("&"));
//
//            String dataWithSalt = sortedString + "&pp_IntegritySalt=" + JAZZCASH_INTEGRITY_SALT;
//            Mac mac = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secretKey = new SecretKeySpec(JAZZCASH_INTEGRITY_SALT.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
//            mac.init(secretKey);
//            byte[] hash = mac.doFinal(dataWithSalt.getBytes(StandardCharsets.UTF_8));
//            StringBuilder hexString = new StringBuilder();
//            for (byte b : hash) {
//                hexString.append(String.format("%02x", b));
//            }
//            return hexString.toString();
//        } catch (Exception e) {
//            throw new RuntimeException("Error generating secure hash", e);
//        }
//    }
//
//    @Override
//    public PaymentResponseDTO getPaymentById(Long courseId, Long id) {
//        Payment payment = paymentRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.PAYMENT_NOT_FOUND));
//        return mapToPaymentResponseDTO(payment);
//    }
//
//    @Override
//    public PaymentResponseDTO updatePayment(Long courseId, Long id, PaymentRequestDTO paymentRequestDto) {
//        Payment payment = paymentRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.PAYMENT_NOT_FOUND));
//        payment.setTransactionId(paymentRequestDto.getTransactionId());
//        payment.setStatus(paymentRequestDto.getPaymentStatus());
//        payment.setAmount(paymentRequestDto.getAmount());
//        Payment updatedPayment = paymentRepository.save(payment);
//        return mapToPaymentResponseDTO(updatedPayment);
//    }
//
//    @Override
//    public void deletePayment(Long courseId, Long id) {
//        Payment payment = paymentRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(ErrorEnum.PAYMENT_NOT_FOUND));
//        paymentRepository.delete(payment);
//    }
//
//    @Override
//    public List<PaymentResponseDTO> getAllPaymentsForCourse(Long courseId) {
//        List<Payment> payments = paymentRepository.findByCourseId(courseId);
//        return payments.stream().map(this::mapToPaymentResponseDTO).collect(Collectors.toList());
//    }
//
//    @Override
//    public PaymentResponseDTO verifyPayment(String transactionId) {
//        // Verification API call to JazzCash (integration pending)
//        return null;
//    }
//
//    private PaymentResponseDTO mapToPaymentResponseDTO(Payment payment) {
//        return new PaymentResponseDTO(
//                payment.getId(),
//                payment.getTransactionId(),
//                payment.getAmount(),
//                payment.getStatus(),
//                payment.getCreatedAt().toLocalDateTime(),
//                payment.getUpdatedAt().toLocalDateTime(),
//                payment.getCourse().getId(),
//                payment.getStudent().getUserId()
//        );
//    }
//
//    private PaymentResponseDTO mapToPaymentResponseDTO(Payment payment, Map<String, String> paymentRequest) {
//        return new PaymentResponseDTO(
//                payment.getId(),
//                payment.getTransactionId(),
//                payment.getAmount(),
//                payment.getStatus(),
//                payment.getCreatedAt().toLocalDateTime(),
//                payment.getUpdatedAt().toLocalDateTime(),
//                payment.getCourse().getId(),
//                payment.getStudent().getUserId(),
//                paymentRequest
//        );
//    }
//}
