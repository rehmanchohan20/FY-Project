package com.rehman.elearning.rest;

// Server.java


import static org.springframework.http.RequestEntity.post;

import static spark.Spark.*;
import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import com.stripe.model.checkout.Session;
import com.stripe.model.PaymentIntent;
import com.rehman.elearning.service.impl.PaymentServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;

public class Server {
    public static void main(String[] args) {
        // The library needs to be configured with your account's secret key.
        Stripe.apiKey = "sk_test_...";  // Use your actual secret key here

        // This is your Stripe CLI webhook secret for testing your endpoint locally.
        String endpointSecret = "whsec_23a78ccf3d1cf56206bc32100e94d4095cc6038789fda3960fdc4cd9de4805cd";  // Use your actual webhook secret here

        port(8080);

        post("/webhook", (request, response) -> {
            String payload = request.body();
            String sigHeader = request.headers("Stripe-Signature");
            Event event = null;

            try {
                event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            } catch (JsonSyntaxException e) {
                // Invalid payload
                response.status(400);
                return "Invalid payload";
            } catch (SignatureVerificationException e) {
                // Invalid signature
                response.status(400);
                return "Invalid signature";
            }

            // Deserialize the nested object inside the event
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;
            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            } else {
                // Deserialization failed, handle the failure case
                System.out.println("Failed to deserialize event data.");
                response.status(400);
                return "Failed to deserialize event data";
            }

            // Handle the event
            switch (event.getType()) {
                case "payment_intent.succeeded": {
                    PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                    // Extract student and course info
                    String studentId = paymentIntent.getMetadata().get("studentId");
                    String courseId = paymentIntent.getMetadata().get("courseId");

                    // Enroll student in the course
                    PaymentServiceImpl paymentService = new PaymentServiceImpl();
                    paymentService.enrollStudentInCourse(studentId, courseId);

                    System.out.println("PaymentIntent succeeded: " + paymentIntent.getId());
                    break;
                }
                case "checkout.session.completed": {
                    Session session = (Session) stripeObject;
                    // Process the checkout session completed event here
                    System.out.println("Checkout session completed: " + session.getId());
                    break;
                }
                // Handle other event types as necessary
                default:
                    System.out.println("Unhandled event type: " + event.getType());
                    break;
            }

            response.status(200);
            return "Event processed successfully";
        });
    }
}
