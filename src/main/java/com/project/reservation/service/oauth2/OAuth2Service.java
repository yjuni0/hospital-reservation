//package com.project.reservation.service.oauth2;
//
//import com.project.reservation.security.google.GoogleOAuth2UserInfo;
//import com.project.reservation.security.jwt.JwtTokenUtil;
//import com.project.reservation.service.member.MemberService;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class OAuth2Service {
//    private final JwtTokenUtil jwtTokenUtil;
//
//    @Value("${spring.security.oauth2.client.registration.google.client-id}")
//    private String clientId;
//
//    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
//    private String clientSecret;
//
//    public String googleCallBack(String code) {
//        // 1. 액세스 토큰을 구글에서 받기
//        String accessToken = getAccessTokenFromGoogle(code);
//
//        // 2. 구글 사용자 정보 가져오기
//         googleUser = getGoogleUserInfo(accessToken);
//
//        // 3. 구글 사용자 정보를 이용해 JWT 토큰 생성
//        String jwtToken = jwtTokenUtil.generateToken(googleUser);
//
//        // 4. 반환할 토큰
//        return jwtToken;
//    }
//
//    // 액세스 토큰을 구글에서 받는 메서드
//    private String getAccessTokenFromGoogle(String code) {
//        String tokenUrl = "https://oauth2.googleapis.com/token";
//        Map<String, String> params = new HashMap<>();
//        params.put("code", code);
//        params.put("client_id", clientId);
//        params.put("client_secret", clientSecret);
//        params.put("redirect_uri", "http://localhost:3000");
//        params.put("grant_type", "authorization_code");
//
//        RestTemplate restTemplate = new RestTemplate();
//        // POST 요청으로 액세스 토큰을 요청
//        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, params, Map.class);
//
//        // 실제 응답에서 액세스 토큰을 추출하여 반환
//        Map<String, Object> responseBody = response.getBody();
//        return responseBody != null ? (String) responseBody.get("access_token") : null;
//    }
//
//    // 액세스 토큰을 사용해 구글 사용자 정보 가져오는 메서드
//    private GoogleOAuth2UserInfo getGoogleUserInfo(String accessToken) {
//        String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + accessToken;
//        RestTemplate restTemplate = new RestTemplate();
//
//        // 구글 사용자 정보를 JSON 문자열로 받음
//        String response = restTemplate.getForObject(userInfoUrl, String.class);
//
//        try {
//            // JSON 파싱 (ObjectMapper 사용)
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(response);
//
//            String email = jsonNode.get("email").asText();
//            String name = jsonNode.get("name").asText();
//            String id = jsonNode.get("sub").asText();  // 사용자 고유 ID
//            String pictureUrl = jsonNode.get("picture").asText();  // 프로필 이미지 URL
//
//            // GoogleOAuth2UserInfo 객체 생성
//            return new GoogleOAuth2UserInfo(email, name, accessToken);
//        } catch (Exception e) {
//            log.error("Error parsing Google user info: {}", e.getMessage());
//            throw new RuntimeException("Failed to retrieve user info from Google", e);
//        }
//    }
//}
