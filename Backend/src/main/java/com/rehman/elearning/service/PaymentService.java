package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.PaymentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.PaymentResponseDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.rehman.elearning.constants.PaymentStatus;

import java.io.IOException;

public interface PaymentService {

    public PaymentResponseDTO createAndProcessPayment(PaymentRequestDTO paymentRequestDTO) throws StripeException, IOException;

//    PaymentIntent confirmPaymentIntent(String paymentIntentId, String paymentMethodId) throws StripeException;

    PaymentStatus updatePaymentStatus(String paymentIntentId) throws StripeException;

    public String getFailureUrl();
    public String getSuccessUrl();



}



//package com.rehman.elearning.service;
//
//import com.rehman.elearning.rest.dto.inbound.PaymentRequestDTO;
//import com.rehman.elearning.rest.dto.outbound.PaymentResponseDTO;
//
//import java.util.List;
//
//public interface PaymentService {
//    PaymentResponseDTO createPayment(Long courseId, PaymentRequestDTO paymentRequestDto);
//    PaymentResponseDTO verifyPayment(String transactionId);
//    PaymentResponseDTO updatePayment(Long courseId, Long id, PaymentRequestDTO paymentRequestDto);
//    PaymentResponseDTO getPaymentById(Long courseId, Long id);
//    List<PaymentResponseDTO> getAllPaymentsForCourse(Long courseId);
//    void deletePayment(Long courseId, Long id);
//}
