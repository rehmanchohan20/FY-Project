package com.rehman.elearning.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONObject;

@Service
public class AIResponseService {

    private static final String API_URL = "https://api-inference.huggingface.co/models/facebook/blenderbot-400M-distill";
    private static final String API_TOKEN = "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"; // Your Hugging Face token

    // Placeholder for predefined FAQs (could be fetched from a database or static source)
    private static final String[] PREDEFINED_FAQS = {
            "What is e-learning?",
            "How do I register for a course?",
            "How can I track my progress?"
    };

    public String generateAnswer(String question) {
        try {
            // First, try to match the question to a predefined FAQ.
            String faqAnswer = getFAQAnswer(question);
            if (faqAnswer != null) {
                return faqAnswer;
            }

            // Otherwise, ask the Hugging Face model
            return generateAnswerFromModel(question);
        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to generate a response. Please try again later.";
        }
    }

    // Check if the question matches a predefined FAQ
    private String getFAQAnswer(String question) {
        for (String faq : PREDEFINED_FAQS) {
            if (question.toLowerCase().contains(faq.toLowerCase())) {
                // Return predefined FAQ response, this can be more elaborate
                return "This is a frequently asked question: " + faq;
            }
        }
        return null; // No match found, proceed with AI model response
    }

    // Generate response from Hugging Face model
    private String generateAnswerFromModel(String question) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(API_TOKEN);

            // Create request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("inputs", question);

            HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

            // Debugging: print request body and headers
            System.out.println("Request Body: " + requestBody.toString());
            System.out.println("Headers: " + headers);

            // Call the API
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL, entity, String.class);

            // Debugging: print response status and body
            System.out.println("Response Status: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());

            // Extract response
            if (response.getStatusCode() == HttpStatus.OK) {
                // Check if response is valid JSON
                String responseBody = response.getBody();
                if (responseBody != null && responseBody.startsWith("{")) {
                    try {
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        // Debugging: print parsed JSON response
                        System.out.println("Parsed JSON Response: " + jsonResponse.toString());
                        return jsonResponse.getJSONArray("generated_texts").getString(0); // Assuming API returns texts here
                    } catch (Exception parseException) {
                        // Handle JSON parsing error
                        parseException.printStackTrace();
                        return "Error parsing response from Hugging Face API.";
                    }
                } else {
                    return "Invalid JSON response from Hugging Face API.";
                }
            } else {
                return "Error: " + response.getStatusCode();
            }

        } catch (Exception e) {
            // Handle errors and print stack trace
            e.printStackTrace();
            return "Unable to generate a response. Please try again later.";
        }
    }

    // Placeholder method for contextual personalization (could be based on user data, etc.)
    private String getPersonalizedResponse(String question) {
        // For example, you can modify the response based on user data.
        // This could involve querying user preferences or history.
        // Here, we are simply adding a placeholder response.
        return "Personalized response based on user data: " + question;
    }

    // Placeholder method for action-oriented recommendations
    private String getActionRecommendations(String question) {
        // Based on the user's query, we could suggest actions like "Start a course" or "Check progress"
        return "Actionable recommendation based on your query: " + question;
    }
}
