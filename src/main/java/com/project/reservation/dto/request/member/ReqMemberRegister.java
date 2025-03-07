package com.project.reservation.dto.request.member;

import com.project.reservation.dto.request.pet.ReqPet;
import com.project.reservation.entity.member.Role;
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
public class ReqMemberRegister {

    private String name;
    private String email;
    private String password;
    private String passwordCheck;
    private String nickName;
    private String addr;
    private String birth;
    private String phoneNum;
    private List<ReqPet> pets;

    @Builder
    public ReqMemberRegister(
            String name, String email, String password, String passwordCheck,
            String nickName, String addr, String birth, String phoneNum, List<ReqPet> pets) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.nickName = nickName;
        this.addr = addr;
        this.birth = birth;
        this.phoneNum = phoneNum;
        this.pets = pets;
    }

    // DTO -> Entity
    public static Member ofEntity(ReqMemberRegister reqMemberRegister) {
        Member member = Member.builder()
                .name(reqMemberRegister.getName())
                .email(reqMemberRegister.getEmail())
                .password(reqMemberRegister.getPassword())
                .nickName(reqMemberRegister.getNickName())
                .addr(reqMemberRegister.getAddr())
                .birth(reqMemberRegister.getBirth())
                .phoneNum(reqMemberRegister.getPhoneNum())
                .roles(Role.USER)
                .build();

        if (reqMemberRegister.getPets() != null) {
            List<Pet> pets = reqMemberRegister.getPets().stream()
                    .map(reqPet -> Pet.builder()
                            .name(reqPet.getName())
                            .breed(reqPet.getBreed())
                            .age(reqPet.getAge())
                            .member(member)  // 여기서 member 설정
                            .build())
                    .collect(Collectors.toList());
            member.setPets(pets);
        }

        return member;
    }
}