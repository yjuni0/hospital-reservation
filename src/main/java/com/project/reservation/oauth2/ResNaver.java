package com.project.reservation.oauth2;

import lombok.RequiredArgsConstructor;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class ResNaver implements ResOAuth2 {
    private final Map<String, Object> attribute;

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return Optional.ofNullable(attribute.get("response"))
                .map(response -> (Map<String, Object>) response)
                .map(response -> response.get("id"))
                .map(Object::toString)
                .orElse("");
    }

    @Override
    public String getEmail() {
        return Optional.ofNullable(attribute.get("response"))
                .map(response -> (Map<String, Object>) response)
                .map(response -> response.get("email"))
                .map(Object::toString)
                .orElse("");
    }

    @Override
    public String getName() {
        return Optional.ofNullable(attribute.get("response"))
                .map(response -> (Map<String, Object>) response)
                .map(response -> response.getOrDefault("name", "Unknown"))
                .map(Object::toString)
                .orElse("Unknown");
    }
}