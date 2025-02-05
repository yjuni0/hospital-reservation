package com.project.reservation.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Pet> pets;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    public Reservation reservation;

    @Builder
    public User(Long id, String name, List<Pet> pets) {
        this.id = id;
        this.name = name;
        this.pets = pets;
    }


}
