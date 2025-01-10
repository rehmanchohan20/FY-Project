//package com.rehman.elearning.rest;
//
//import com.rehman.elearning.service.PaymentService;
//import com.stripe.exception.SignatureVerificationException;
//import com.stripe.model.Event;
//import com.stripe.model.PaymentIntent;
//import com.stripe.net.Webhook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/payment")
//public class PaymentWebhookController {
//
//    private static final Logger logger = LoggerFactory.getLogger(PaymentWebhookController.class);
//
//    @Value("${stripe.webhook.secret}")
//    private String webhookSecret;
//
//    private final PaymentService paymentService;
//
//    public PaymentWebhookController(PaymentService paymentService) {
//        this.paymentService = paymentService;
//    }
//
//    @PostMapping("/webhook")
//    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
//        logger.info("Received webhook event");
//
//        Event event;
//        try {
//            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
//        } catch (SignatureVerificationException e) {
//            logger.error("Invalid signature", e);
//            return ResponseEntity.badRequest().body("Invalid signature");
//        }
//
//        String eventType = event.getType();
//        logger.info("Processing event type: {}", eventType);
//
//        // Handle the event
//        if ("payment_intent.succeeded".equals(eventType)) {
//            PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
//            if (paymentIntent != null) {
//                String transactionId = paymentIntent.getId();
//                logger.info("Processing payment_intent.succeeded for transaction ID: {}", transactionId);
//                paymentService.handlePaymentSuccess(transactionId); // This line ensures the payment success is handled
//            }
//        } else if ("payment_intent.payment_failed".equals(eventType)) {
//            PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
//            if (paymentIntent != null) {
//                String transactionId = paymentIntent.getId();
//                logger.info("Processing payment_intent.payment_failed for transaction ID: {}", transactionId);
//                paymentService.handlePaymentFailure(transactionId);
//            }
//        }
//
//        return ResponseEntity.ok("Event processed");
//    }
//}