package com.project.reservation.dto.request.pet;

import com.project.reservation.entity.member.Breed;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ReqPet {

    private String name;
    private Breed breed;
    private int age;

    @Builder
    public ReqPet(String name, Breed breed, int age) {
        this.name = name;
        this.breed = breed;
        this.age = age;
    }
}