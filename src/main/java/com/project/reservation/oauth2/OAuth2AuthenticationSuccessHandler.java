package com.project.reservation.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reservation.dto.response.member.ResMemberToken;
import com.project.reservation.dto.response.member.ResOAuthMemberToken;
import com.project.reservation.entity.member.Member;
import com.project.reservation.repository.member.MemberRepository;
import com.project.reservation.security.jwt.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenUtil jwtTokenUtil;
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String nickname = customOAuth2User.getName();


        String modifiedNickname = "social_" + nickname;
        String token = jwtTokenUtil.generateToken(customOAuth2User, modifiedNickname);

        // 리다이렉트 URL 지정 (여기서는 localhost:3000으로 리다이렉트)
        String redirectUrl = "http://localhost:3000/oauth2?access_token=" + token;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);

    }
}