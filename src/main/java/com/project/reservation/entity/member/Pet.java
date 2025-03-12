package com.project.reservation.entity.member;

import com.project.reservation.common.BaseTimeEntity;
import com.project.reservation.entity.onlineReserve.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class Pet extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Breed breed;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn
    private Member member;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reservation> reservations=new ArrayList<>();


    public void updatePet(String name, Breed breed, Integer age) {
        this.name=name;
        this.breed = breed;
        this.age = age;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getPets().add(this);
    }
}
