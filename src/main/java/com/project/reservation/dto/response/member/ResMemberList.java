package com.project.reservation.dto.response.member;

import com.project.reservation.entity.member.Member;


import java.time.LocalDate;

public record ResMemberList(Long id, String name, String nickName, String email,String phoneNum, LocalDate createdDate) {
    public static ResMemberList fromEntity(Member member){
        return new ResMemberList(
                member.getId(),
                member.getName(),
                member.getNickName(),
                member.getEmail(),
                member.getPhoneNum(),
                member.getCreatedDate().toLocalDate()
        );
    }

}
