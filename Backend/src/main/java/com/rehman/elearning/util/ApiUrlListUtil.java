package com.rehman.elearning.util;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;

public class ApiUrlListUtil {
    public static List<RequestMatcher> getStudentApiUrls(){
        List<RequestMatcher> list = new ArrayList<>();
        list.add(new AntPathRequestMatcher("/api/courses/**"));
        list.add(new AntPathRequestMatcher("/api/tickets/student/{studentId}"));
        list.add(new AntPathRequestMatcher("/api/modules/**"));
        list.add(new AntPathRequestMatcher("/api/media/**"));
        list.add(new AntPathRequestMatcher("/api/tickets/**"));
        list.add(new AntPathRequestMatcher("/api/payment/**"));
        list.add(new AntPathRequestMatcher("/api/guidance/**"));
        return list;
    }

    public static List<RequestMatcher> getCommonApiURLS(){
        List<RequestMatcher> list = new ArrayList<>();
        list.add(new AntPathRequestMatcher("/api/users/me"));
        list.add(new AntPathRequestMatcher("/api/users/*"));
        return list;
    }

    public static List<RequestMatcher> getTeacherApiUrls(){
        List<RequestMatcher> list = new ArrayList<>();
        list.add(new AntPathRequestMatcher("/api/courses/**"));
        list.add(new AntPathRequestMatcher("/api/modules/{moduleId}/lessons/**"));

        list.add(new AntPathRequestMatcher("/api/courses/{courseId}/offers/**"));
        list.add(new AntPathRequestMatcher("/api/courses/{courseId}/modules/{moduleId}"));

        return list;
    }

    public static List<RequestMatcher> getAdminApiUrls(){
        List<RequestMatcher> list = new ArrayList<>();
        list.add(new AntPathRequestMatcher("/api/users/admin/**"));
        list.add(new AntPathRequestMatcher("/api/tickets/**"));
        return list;
    }

    public static List<RequestMatcher> getGuestApiUrls(){
        List<RequestMatcher> list = new ArrayList<>();
        list.add(new AntPathRequestMatcher("v1/oauth2/*"));
        list.add(new AntPathRequestMatcher("/api/payment/**"));
        return list;
    }

    public static List<RequestMatcher> getApiIgnoreUrlList(){
        List<RequestMatcher> list = new ArrayList<>();
        list.add(new AntPathRequestMatcher("/v3/api-docs/**"));
        list.add(new AntPathRequestMatcher("/swagger-ui/**"));
        list.add(new AntPathRequestMatcher("/swagger-ui.html"));
        list.add(new AntPathRequestMatcher("/v1/auth/**"));
        list.add(new AntPathRequestMatcher("/oauth2/**"));
        list.add(new AntPathRequestMatcher("/api/public/**"));
        list.add(new AntPathRequestMatcher("/api/**"));
        list.add(new AntPathRequestMatcher("/api/payments/**"));
        list.add(new AntPathRequestMatcher("/login/oauth2/**"));
        list.add(new AntPathRequestMatcher("/oauth2/callback/google"));
        list.add(new AntPathRequestMatcher("/api/modules/{moduleId}/lessons"));
        list.add(new AntPathRequestMatcher("/api/courses/**"));
        list.add(new AntPathRequestMatcher("/api/media/**"));
        list.add(new AntPathRequestMatcher("/api/guidance/**"));
        list.add(new AntPathRequestMatcher("/api/payment/**"));


        return list;
    }
}