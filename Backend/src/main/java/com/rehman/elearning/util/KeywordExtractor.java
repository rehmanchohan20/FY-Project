package com.rehman.elearning.util;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;

public class KeywordExtractor {

    // Define stop words to ignore (can be enhanced)
    private static final Set<String> STOP_WORDS = Set.of(
            "i", "want", "to", "learn", "is", "the", "a", "an", "help", "can", "you", "me", "do",
            "should", "which", "please", "of", "my", "this", "that", "in", "on", "at", "by", "with", "about",
            "through", "for", "from", "as", "it", "be", "become", "are", "was", "were", "will", "would",
            "have", "has", "had", "your", "thank", "thanks", "understand", "understood", "okay", "good",
            "really", "quite", "sure", "exactly", "need", "more", "how", "much", "any", "tell", "offer",
            "too", "just", "like", "say", "speak", "write", "feel", "ask", "give", "find", "make", "try",
            "ready", "know", "possible", "show", "set", "check", "before", "that", "be", "all", "these",
            "here", "there", "now", "when", "where", "why", "who", "which", "with", "without", "please", "can"
    );

    // Extract a list of keywords from the input query
    public static List<String> extractKeywords(String input) {
        List<String> keywords = new ArrayList<>();

        // Convert to lowercase to ensure case-insensitive comparison
        input = input.toLowerCase();

        // Tokenize the input and remove stop words
        StringTokenizer tokenizer = new StringTokenizer(input, " ,?");

        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken();
            if (!STOP_WORDS.contains(word)) {
                keywords.add(word); // Add meaningful keywords to the list
            }
        }

        return keywords; // Return list of keywords
    }

}