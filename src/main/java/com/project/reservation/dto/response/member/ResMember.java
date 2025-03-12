package com.project.reservation.dto.response.member;

import com.project.reservation.dto.response.pet.ResPet;
import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.member.Pet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ResMember {
    private Long id;
    private String name;
    private String email;
    private String nickName;
    private String addr;
    private String birth;
    private String phoneNum;
    private LocalDateTime createdDate;
    private List<ResPet> pets; //응답에서 순환참조 문제 발생.

    @Builder
    public ResMember(Long id,String name, String email, String nickName, String addr, String birth, String phoneNum, LocalDateTime createdDate,List<ResPet> pets) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickName = nickName;
        this.addr = addr;
        this.birth = birth;
        this.phoneNum = phoneNum;
        this.createdDate = createdDate;
        this.pets = pets;
    }

    // Entity -> DTO
    public static ResMember fromEntity(Member member) {
        return ResMember.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .nickName(member.getNickName())
                .addr(member.getAddr())
                .birth(member.getBirth())
                .phoneNum(member.getPhoneNum())
                .createdDate(member.getCreatedDate())
                .pets(member.getPets() != null ?
                        member.getPets().stream()
                                .map(ResPet::fromEntity)
                                .collect(Collectors.toList())
                        : null)
                .build();
    }
}