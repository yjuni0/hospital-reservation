package com.project.reservation.dto.response.member;

import com.project.reservation.dto.response.pet.ResPet;
import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.member.Pet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ResMember {

    private String name;
    private String email;
    private String nickName;
    private String addr;
    private String birth;
    private String phoneNum;
    private List<ResPet> pets;

    @Builder
    public ResMember(String name, String email, String password, String nickName, String addr, String birth, String phoneNum, List<ResPet> pets) {
        this.name = name;
        this.email = email;
        this.nickName = nickName;
        this.addr = addr;
        this.birth = birth;
        this.phoneNum = phoneNum;
        this.pets = pets;
    }

    // Entity -> DTO
    public static ResMember fromEntity(Member member) {
        return ResMember.builder()
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickName(member.getNickName())
                .addr(member.getAddr())
                .birth(member.getBirth())
                .phoneNum(member.getPhoneNum())
                .pets(member.getPets().stream().map(ResPet::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
