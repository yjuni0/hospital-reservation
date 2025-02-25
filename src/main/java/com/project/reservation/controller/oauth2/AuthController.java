package com.project.reservation.controller.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reservation.dto.response.member.ResMemberToken;
import com.project.reservation.security.google.*;
import com.project.reservation.security.jwt.JwtTokenUtil;
import com.project.reservation.service.member.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final MemberService memberService;
    private final JwtTokenUtil jwtTokenUtil;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @GetMapping("/auth/google-login")
    public ResponseEntity<String> getGoogleLoginUrl() throws IOException {

        // 구글 로그인 페이지로 리디렉트 URL을 구성
        String googleAuthUrl = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + clientId +
                "&redirect_uri=" + "https://5889-211-197-18-247.ngrok-free.app/api/oauth2/callback" +
                "&response_type=code" +
                "&scope=profile email" +
                "&mode=" + "login";

        return ResponseEntity.ok(googleAuthUrl);
    }
    @GetMapping("/oauth2/callback")
    public ResponseEntity<ResMemberToken> googleCallBack(@RequestParam("code") String code) throws IOException {
        String redirectUri = "https://5889-211-197-18-247.ngrok-free.app/api/oauth2/callback"; // 구글에서 리디렉션될 URI
        log.info("요청 코드 는 {}",code);
        // 토큰 요청 파라미터
        String body = "code=" + code +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&redirect_uri=" + redirectUri +
                "&grant_type=authorization_code";

        // 구글 토큰 엔드포인트로 POST 요청 보내기
        String accessToken = getAccessTokenFromGoogle(body);
        log.info("구글에서 받아온 token{}",accessToken);
        // 2. 액세스 토큰을 사용하여 구글 사용자 정보 가져오기
        OAuth2UserPrincipal userPrincipal = getUserInfoFromGoogle(accessToken);
        log.info("구글에서 받아온 정보{}",userPrincipal);
        // 3. 사용자 정보 DB에 저장 및 `ROLE_USER` 권한 부여
        memberService.saveOrUpdateMember(userPrincipal);

        // 4. JWT 토큰 발급
        String jwtToken = jwtTokenUtil.googleToken(userPrincipal);
        log.info("발급한 토큰 {}",jwtToken);
        // 5. JWT 토큰을 응답에 포함
        ResMemberToken resMemberToken = new ResMemberToken(userPrincipal.getUsername(),jwtToken);
        return ResponseEntity.ok(resMemberToken);
    }

    // 구글에서 받은 `code`를 통해 액세스 토큰을 얻는 메서드
    private String getAccessTokenFromGoogle(String code) {
        String tokenUrl = "https://oauth2.googleapis.com/token";
        String redirectUri = "https://5889-211-197-18-247.ngrok-free.app/api/oauth2/callback";
        log.info("요청한 Authorization Code: {}", code); // 요청 코드 확인

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<Map> responseEntity = restTemplate.exchange(
                    tokenUrl, HttpMethod.POST, requestEntity, Map.class
            );

            Map<String, Object> responseBody = responseEntity.getBody();
            log.info("응답 바디: {}", responseBody); // 응답 로그 추가

            if (responseBody != null && responseBody.containsKey("access_token")) {
                return responseBody.get("access_token").toString();
            } else {
                throw new RuntimeException("Failed to obtain access token from Google.");
            }
        } catch (HttpClientErrorException e) {
            log.error("Google API 요청 실패! 상태 코드: {}, 응답: {}",
                    e.getStatusCode(), e.getResponseBodyAsString());
            throw e;
        }
    }

    // 구글 API로부터 사용자 정보를 가져오는 메서드
    private Map<String, Object> getGoogleUserInfoFromAPI(String accessToken) throws IOException {
        String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        // HTTP 요청 객체 생성
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // RestTemplate을 이용해 요청 보내기
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                entity,
                Map.class
        );

        // 응답에서 사용자 정보 추출
        Map<String, Object> userInfo = responseEntity.getBody();
        if (userInfo == null) {
            throw new RuntimeException("Failed to obtain user info from Google.");
        }

        return userInfo;
    }

    // 구글 액세스 토큰을 통해 사용자 정보 가져오기
    public OAuth2UserPrincipal getUserInfoFromGoogle(String accessToken) throws IOException {
        // 구글 API를 사용하여 사용자 정보 가져오기
        Map<String, Object> attributes = getGoogleUserInfoFromAPI(accessToken);

        // GoogleOAuth2UserInfo 생성
        GoogleOAuth2UserInfo userInfo = GoogleOAuth2UserInfo.builder()
                .accessToken(accessToken)
                .attributes(attributes)
                .build();

        // OAuth2UserPrincipal 객체 생성하여 반환
        return new OAuth2UserPrincipal(userInfo);
    }

}
