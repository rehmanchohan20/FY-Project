package com.rehman.elearning.util;

import java.util.Set;

public class KeywordUtil {

    public static final Set<String> RESPONSE_TRIGGERS = Set.of(
            "ok", "Ok", "thanks", "thank you", "great", "sure", "please", "help", "thanks a lot",
            "many thanks", "thank you so much", "perfect", "awesome", "fantastic", "wonderful",
            "good", "okay", "got it", "all right", "appreciate it", "appreciated",
            "much appreciated", "cheers", "thanks again", "I'm good", "sounds good",
            "understood", "cool", "fine", "no problem", "noted", "will do", "absolutely",
            "I'm all set", "sounds great", "I'm in", "deal", "right", "roger that",
            "I see", "gotcha", "okay thanks", "I appreciate it", "thank you very much",
            "no worries", "got it thanks", "okay cool", "thanks for your help", "that works",
            "much obliged", "appreciate it a lot", "thanks a ton", "thanks a million",
            "all good", "okay awesome", "done", "I'm on it", "I’m good now", "all set",
            "great job", "excellent", "I’m fine", "thanks, I’m good", "thanks a bunch",
            "good to go", "I’m ready", "that’s great", "I’m happy with this", "I got it",
            "perfect, thanks", "appreciate your help", "thanks for that", "I got it now",
            "all set, thanks", "I’ll do that", "will do, thanks", "I’m clear now",
            "no issues", "thank you for the info", "it’s all clear now", "I’ll take care of it",
            "understood, thanks", "sounds perfect", "I’m on board", "all understood",
            "thanks for your time", "thank you for the help", "I’m set", "ready to go",
            "perfect, got it", "thanks for clarifying", "I’m all good", "appreciate it very much",
            "thank you for your support", "thanks for the update", "this works for me",
            "I’m ready to proceed", "all clear", "no further questions", "thanks for everything",
            "thanks for the guidance", "everything’s good", "okay, got it", "thank you kindly",
            "everything is clear", "thanks, it makes sense", "I’ve got everything",
            "I appreciate the help", "thank you, it’s much appreciated", "I can move forward now",
            "this helps a lot", "I’m satisfied", "I understand now", "thanks, I’m set",
            "got it, thanks", "this is perfect", "good to know", "this is exactly what I needed",
            "that’s helpful, thanks", "this works for me, thanks", "thank you, everything’s clear"
    );

    public static final Set<String> PROGRAMMING_KEYWORDS = Set.of(
            "Code", "Coding", "Programming", "Development", "App Dev", "Software Engineer", " Computer Science",
            "Java", "Python", "C++", "JavaScript", "Ruby", "PHP", "C#", "Swift", "Kotlin",
            "Go", "TypeScript", "Rust", "HTML", "CSS", "SQL", "R", "software development",
            "web development", "mobile development", "backend development", "frontend development",
            "full-stack development", "application development", "API development", "game development",
            "desktop development", "cloud computing", "DevOps", "Agile development", "Scrum",
            "object-oriented programming", "functional programming", "data structures", "algorithms",
            "databases", "AI", "machine learning", "deep learning", "data science", "blockchain",
            "IoT", "microservices", "Spring", "Django", "Flask", "React Native", "Flutter",
            "unit testing", "TDD", "Git", "Docker", "Kubernetes", "Jenkins", "Visual Studio",
            "IntelliJ IDEA", "Eclipse", "Android Studio", "Xcode", "Postman", "MongoDB",
            "PostgreSQL", "MySQL", "Redis", "Node.js", "React", "Vue.js", "Angular", "Express"
    );

    public static final Set<String> DESIGN_KEYWORDS = Set.of(
            "graphic design", "UI/UX design", "web design", "product design", "industrial design",
            "fashion design", "interior design", "packaging design", "print design", "animation design",
            "Adobe Photoshop", "Adobe Illustrator", "Adobe XD", "Figma", "Sketch", "InDesign",
            "Canva", "CorelDRAW", "Affinity Designer", "Autodesk Sketchbook", "Blender", "Procreate",
            "typography", "color theory", "branding", "layout design", "illustration", "visual storytelling",
            "iconography", "digital art", "motion graphics", "3D modeling", "responsive design",
            "interaction design", "design thinking", "user-centered design", "UI", "UX", "prototyping",
            "wireframing", "HCI", "usability testing"
    );

    public static final Set<String> DIGITAL_MARKETING_KEYWORDS = Set.of(
            "digital marketing", "online marketing","SEO", "SEM",
            "content marketing", "influencer marketing", "affiliate marketing", "email marketing",
            "mobile marketing", "e-commerce marketing", "marketing automation", "Google Ads",
            "Facebook Ads", "Instagram Ads", "YouTube Ads", "LinkedIn Ads", "Twitter Ads",
            "Google Analytics", "SEMrush", "Ahrefs", "HubSpot", "Hootsuite", "Mailchimp", "Canva",
            "Buffer", "keyword research", "link building", "PPC", "CRO", "content strategy",
            "copywriting", "A/B testing", "lead generation", "marketing funnels", "brand awareness",
            "customer segmentation", "retargeting", "social media marketing", "content creation",
            "video marketing", "blogging", "podcasting", "display advertising"
    );

    public static final Set<String> OTHERS_KEYWORDS = Set.of(
            "soft skills", "personal development", "public speaking", "time management", "leadership",
            "project management", "communication skills", "negotiation", "critical thinking","content writing",
            "problem solving", "teamwork", "photography", "music production", "language learning",
            "entrepreneurship", "finance", "accounting", "health & wellness", "yoga", "fitness",
            "nutrition", "cooking", "meditation", "resume building", "interview preparation",
            "job search strategies", "freelancing", "remote work skills", "career coaching",
            "professional certifications", "business analysis", "management principles", "strategy",
            "sales", "marketing fundamentals", "risk management", "business operations",
            "customer service", "human resources", "supply chain management"
    );
}
