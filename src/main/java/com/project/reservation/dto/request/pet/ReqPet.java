package com.project.reservation.dto.request.pet;

import com.project.reservation.entity.member.Breed;
import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.member.Pet;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReqPet {
    private Long id;
    private String name;
    private Breed breed;
    private int age;

    @Builder
    public ReqPet(Long id,String name, Breed breed, int age) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
    }
    public static Pet toEntity(ReqPet pet, Member member){
        return Pet.builder()
                .id(pet.getId())
                .name(pet.getName())
                .breed(pet.getBreed())
                .age(pet.getAge())
                .member(member)
                .build();
    }
}