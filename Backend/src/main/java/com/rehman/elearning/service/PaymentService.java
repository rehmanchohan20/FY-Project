package com.rehman.elearning.service;

import com.rehman.elearning.rest.dto.inbound.PaymentRequestDTO;
import com.rehman.elearning.rest.dto.outbound.PaymentResponseDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.rehman.elearning.constants.PaymentStatus;

import java.io.IOException;

public interface PaymentService {

    PaymentResponseDTO createAndProcessPayment(PaymentRequestDTO paymentRequestDTO) throws StripeException, IOException;
    PaymentStatus updatePaymentStatus(String paymentIntentId) throws StripeException;
     String getFailureUrl();
     String getSuccessUrl();

}