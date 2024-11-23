package com.rehman.elearning.util;

import com.rehman.elearning.constants.RoleEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorityUtils {

    public static List<SimpleGrantedAuthority> convertToSimpleGrantedAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
    }

    public static String getUserRole(Collection<? extends GrantedAuthority> authorities){
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)  // Extract the authority string
                .findFirst()                          // Get the first authority
                .orElse(null);
    }
}

