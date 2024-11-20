package com.rehman.elearning.util;

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import java.util.Base64;
import java.io.*;

public class OAuth2AuthorizationRequestUtil {

    public static String serialize(OAuth2AuthorizationRequest authorizationRequest) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(authorizationRequest);
            return Base64.getUrlEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to serialize OAuth2AuthorizationRequest", e);
        }
    }

    public static OAuth2AuthorizationRequest deserialize(String serializedRequest) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                Base64.getUrlDecoder().decode(serializedRequest));
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (OAuth2AuthorizationRequest) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to deserialize OAuth2AuthorizationRequest", e);
        }
    }
}