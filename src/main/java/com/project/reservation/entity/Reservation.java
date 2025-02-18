package com.project.reservation.entity;

import com.project.reservation.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Pet pet;

    private String departmentName;

    private LocalDateTime reservationTime;


    public void setMember(Member member) {
        this.member = member;
        member.getReservations().add(this);
    }

}
