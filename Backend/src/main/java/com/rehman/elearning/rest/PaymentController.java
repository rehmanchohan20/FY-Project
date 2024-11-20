package com.rehman.elearning.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses/{courseId}/payments")
public class PaymentController {

    // Define constants for JazzCash credentials and configurations
    private static final String JAZZCASH_INTEGRITY_SALT = "22954z3v21";  // Replace with actual salt
    private static final String JAZZCASH_MERCHANT_ID = "MC134536";      // Replace with your actual merchant ID
    private static final String JAZZCASH_PASSWORD = "su1t9xb12y";       // Replace with your actual password
    private static final String JAZZCASH_RETURN_URL = "http://127.0.0.1:3000/confirmPayment";  // Replace with your actual return URL

    @PostMapping
    public ResponseEntity<Map<String, String>> initiatePayment(@PathVariable Long courseId, @RequestBody Map<String, String> paymentRequest) {
        // Step 1: Generate SecureHash
        String txnDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String txnRefNo = "T" + txnDateTime;

        paymentRequest.put("pp_Version", "1.0");
        paymentRequest.put("pp_TxnRefNo", txnRefNo);  // Unique transaction reference
        paymentRequest.put("pp_MerchantID", JAZZCASH_MERCHANT_ID);
        paymentRequest.put("pp_Password", JAZZCASH_PASSWORD);
        paymentRequest.put("pp_Amount", "100");  // Example amount, replace as needed
        paymentRequest.put("pp_TxnCurrency", "PKR");
        paymentRequest.put("pp_TxnDateTime", txnDateTime);
        paymentRequest.put("pp_BillReference", "billRef");
        paymentRequest.put("pp_Description", "Course payment for course ID: " + courseId);
        paymentRequest.put("pp_TxnExpiryDateTime", LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        paymentRequest.put("pp_ReturnURL", JAZZCASH_RETURN_URL);

        // Step 2: Generate SecureHash and append to the payment request
        String sortedString = paymentRequest.entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        String secureHash = generateSecureHash(sortedString, JAZZCASH_INTEGRITY_SALT);
        paymentRequest.put("pp_SecureHash", secureHash);

        // Step 3: Generate the JazzCash payment URL
        String paymentUrl = generateJazzCashUrl(paymentRequest);

        // Step 4: Return the payment URL to the client
        return ResponseEntity.ok(Map.of("paymentUrl", paymentUrl));
    }

    private String generateSecureHash(String data, String salt) {
        try {
            String dataWithSalt = data + "&" + "pp_IntegritySalt=" + salt;
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(salt.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);
            byte[] hash = mac.doFinal(dataWithSalt.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating secure hash", e);
        }
    }

    private String generateJazzCashUrl(Map<String, String> paymentRequest) {
        // Construct the base URL for JazzCash payment
        StringBuilder urlBuilder = new StringBuilder("https://sandbox.jazzcash.com.pk/CustomerPortal/transactionmanagement/merchantform/");

        // Append query parameters to the URL (you can add other necessary parameters as well)
        paymentRequest.forEach((key, value) -> urlBuilder.append(key).append("=").append(value).append("&"));

        // Remove the last "&"
        urlBuilder.deleteCharAt(urlBuilder.length() - 1);

        return urlBuilder.toString();
    }

    // Success handler (After payment is successful)
//    @GetMapping("/success")
//    public String success(@RequestParam Map<String, String> response) {
//        // Handle the success response here (e.g., log the payment data, notify user)
//        String txnRefNo = response.get("pp_TxnRefNo");
//        String amount = response.get("pp_Amount");
//        return "Payment was successful! Transaction Ref No: " + txnRefNo + ", Amount: " + amount;
//    }

    // Failure handler (When payment fails)
    @GetMapping("/failure")
    public String failure(@RequestParam Map<String, String> response) {
        // Handle failure response (e.g., log the error, notify the user)
        String errorMessage = response.get("pp_ErrorMessage");
        return "Payment failed: " + errorMessage;
    }
}



//package com.sarfaraz.elearning.rest;
//
//import org.springframework.web.bind.annotation.*;
//import org.springframework.http.ResponseEntity;
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/courses/{courseId}/payments")
//public class PaymentController {
//
//    private static final String JAZZCASH_INTEGRITY_SALT = "22954z3v21";
//    private static final String JAZZCASH_MERCHANT_ID = "MC134536";
//    private static final String JAZZCASH_PASSWORD = "su1t9xb12y";
//    private static final String JAZZCASH_RETURN_URL = "http://127.0.0.1:8080/success"; // You can update this to the actual return URL
//
//    @PostMapping
//    public ResponseEntity<Map<String, String>> initiatePayment(@PathVariable Long courseId, @RequestBody Map<String, String> paymentRequest) {
//        // Step 1: Generate SecureHash
//        String sortedString = paymentRequest.entrySet().stream()
//                .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
//                .sorted(Map.Entry.comparingByKey())
//                .map(entry -> entry.getKey() + "=" + entry.getValue())
//                .collect(Collectors.joining("&"));
//
//        String secureHash = generateSecureHash(sortedString, JAZZCASH_INTEGRITY_SALT);
//
//        // Step 2: Add the SecureHash to the request
//        paymentRequest.put("pp_SecureHash", secureHash);
//
//        // Step 3: Create JazzCash payment URL
//        String paymentUrl = generateJazzCashUrl(paymentRequest);
//
//        // Step 4: Redirect the user to JazzCash
//        return ResponseEntity.ok(Map.of("paymentUrl", paymentUrl));
//    }
//
//    private String generateSecureHash(String data, String salt) {
//        try {
//            String dataWithSalt = data + "&" + "pp_IntegritySalt=" + salt;
//            Mac mac = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secretKey = new SecretKeySpec(salt.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
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
//    private String generateJazzCashUrl(Map<String, String> paymentRequest) {
//        // Construct the base URL for JazzCash payment
//        StringBuilder urlBuilder = new StringBuilder("https://sandbox.jazzcash.com.pk/CustomerPortal/transactionmanagement/merchantform/");
//
//        // Append query parameters to the URL (you can add other necessary parameters as well)
//        paymentRequest.forEach((key, value) -> urlBuilder.append(key).append("=").append(value).append("&"));
//
//        // Remove the last "&"
//        urlBuilder.deleteCharAt(urlBuilder.length() - 1);
//
//        return urlBuilder.toString();
//    }
//}







//
//import com.sarfaraz.elearning.rest.dto.inbound.PaymentRequestDTO;
//import com.sarfaraz.elearning.rest.dto.outbound.PaymentResponseDTO;
//import com.sarfaraz.elearning.service.PaymentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/courses/{courseId}/payments")
//public class PaymentController {
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @PostMapping
//    public ResponseEntity<PaymentResponseDTO> createPayment(@PathVariable Long courseId, @RequestBody PaymentRequestDTO paymentRequestDto) {
//        PaymentResponseDTO response = paymentService.createPayment(courseId, paymentRequestDto);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long courseId, @PathVariable Long id) {
//        PaymentResponseDTO response = paymentService.getPaymentById(courseId, id);
//        return ResponseEntity.ok(response);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<PaymentResponseDTO> updatePayment(@PathVariable Long courseId, @PathVariable Long id, @RequestBody PaymentRequestDTO paymentRequestDto) {
//        PaymentResponseDTO response = paymentService.updatePayment(courseId, id, paymentRequestDto);
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletePayment(@PathVariable Long courseId, @PathVariable Long id) {
//        paymentService.deletePayment(courseId, id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping
//    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments(@PathVariable Long courseId) {
//        List<PaymentResponseDTO> payments = paymentService.getAllPaymentsForCourse(courseId);
//        return ResponseEntity.ok(payments);
//    }
//
//    @PostMapping("/verify")
//    public ResponseEntity<PaymentResponseDTO> verifyPayment(@RequestParam String transactionId) {
//        PaymentResponseDTO response = paymentService.verifyPayment(transactionId);
//        return ResponseEntity.ok(response);
//    }
//}
//
//
//
//
//
//
//
////package com.sarfaraz.elearning.rest;
////
////import com.sarfaraz.elearning.rest.dto.inbound.PaymentRequestDTO;
////import com.sarfaraz.elearning.rest.dto.outbound.PaymentResponseDTO;
////import com.sarfaraz.elearning.service.PaymentService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////
////import java.util.List;
////
////@RestController
////@RequestMapping("/api/courses/{courseId}/payments")
////public class PaymentController {
////
////    @Autowired
////    private PaymentService paymentService;
////
////    @PostMapping
////    public ResponseEntity<PaymentResponseDTO> createPayment(@PathVariable Long courseId, @RequestBody PaymentRequestDTO paymentRequestDto) {
////        PaymentResponseDTO response = paymentService.createPayment(courseId, paymentRequestDto);
////        return new ResponseEntity<>(response, HttpStatus.CREATED);
////    }
////
////    @GetMapping("/{id}")
////    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long courseId, @PathVariable Long id) {
////        PaymentResponseDTO response = paymentService.getPaymentById(courseId, id);
////        return ResponseEntity.ok(response);
////    }
////
////    @PutMapping("/{id}")
////    public ResponseEntity<PaymentResponseDTO> updatePayment(@PathVariable Long courseId, @PathVariable Long id, @RequestBody PaymentRequestDTO paymentRequestDto) {
////        PaymentResponseDTO response = paymentService.updatePayment(courseId, id, paymentRequestDto);
////        return ResponseEntity.ok(response);
////    }
////
////    @DeleteMapping("/{id}")
////    public ResponseEntity<Void> deletePayment(@PathVariable Long courseId, @PathVariable Long id) {
////        paymentService.deletePayment(courseId, id);
////        return ResponseEntity.noContent().build();
////    }
////
////    @GetMapping
////    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments(@PathVariable Long courseId) {
////        List<PaymentResponseDTO> payments = paymentService.getAllPaymentsForCourse(courseId);
////        return ResponseEntity.ok(payments);
////    }
////
////    @PostMapping("/verify")
////    public ResponseEntity<PaymentResponseDTO> verifyPayment(@RequestParam String transactionId) {
////        PaymentResponseDTO response = paymentService.verifyPayment(transactionId);
////        return ResponseEntity.ok(response);
////    }
////}
