package com.project.reservation.security.google;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.Map;
@Getter
@AllArgsConstructor
public class GoogleOAuth2UserInfo implements OAuth2UserInfo  {

    private final Map<String, Object> attributes;
    private final String accessToken;
    private final String id;
    private final String email;
    private final String name;
    private final String nickName;

    @Builder
    public GoogleOAuth2UserInfo(String accessToken, Map<String, Object> attributes) {
        this.accessToken = accessToken;
        this.attributes = attributes;
        // Null 체크 추가
        this.id = attributes != null ? (String) attributes.get("sub") : null;
        this.email = attributes != null ? (String) attributes.get("email") : null;
        this.name = attributes != null ? (String) attributes.get("name") : null;
        this.nickName = attributes != null ? (String) attributes.get("nickname") : null;
    }


    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.GOOGLE;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFirstName() {
        return "";
    }

    @Override
    public String getLastName() {
        return "";
    }

    @Override
    public String getNickname() {
        return nickName;
    }

    @Override
    public String getProfileImageUrl() {
        // 구글에서 제공하는 사용자 프로필 이미지를 반환
        return attributes != null ? (String) attributes.get("picture") : "";
    }
}

