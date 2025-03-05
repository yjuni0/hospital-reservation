package com.project.reservation.dto.request.member;

import com.project.reservation.entity.member.Pet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReqMemberUpdate {


    private String nickName;
    private String addr;
    private String phone;
    private List<Pet> pets;

    @Builder
    public ReqMemberUpdate( String nickName, String addr, String birth, String phone, List<Pet> pets) {

        this.nickName = nickName;
        this.addr = addr;
        this.phone = phone;
        this.pets = pets;
    }
}
