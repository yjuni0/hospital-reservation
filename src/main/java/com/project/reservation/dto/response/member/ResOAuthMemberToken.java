package com.project.reservation.dto.response.member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
public class ResOAuthMemberToken {

    private String email;
    private String token;

    @Builder
    public ResOAuthMemberToken(String email, String token) {
        this.email = email;
        this.token = token;
    }

    // Entity -> DTO
    public static ResOAuthMemberToken fromEntity(UserDetails user, String token) {
        return ResOAuthMemberToken.builder()
                .email(user.getUsername())
                .token(token)
                .build();
    }
}