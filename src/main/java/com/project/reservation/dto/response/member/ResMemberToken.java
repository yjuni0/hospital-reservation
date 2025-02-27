package com.project.reservation.dto.response.member;

import com.project.reservation.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@NoArgsConstructor
public class ResMemberToken {

    private String nickName;
    private String token;

    @Builder
    public ResMemberToken(String nickName, String token) {
        this.nickName = nickName;
        this.token = token;
    }

    // Entity -> DTO
    public static ResMemberToken fromEntity(Member member, String token) {
        return ResMemberToken.builder()
                .nickName(member.getNickName())
                .token(token)
                .build();
    }
}