package com.project.reservation.entity.onlineReserve;

import com.project.reservation.common.BaseTimeEntity;
import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.member.Pet;
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

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false)
    private Pet pet;

    private String departmentName;

    @Column(nullable = false,unique = true)
    private LocalDateTime reservationTime;


    public void setMember(Member member) {
        this.member = member;
        member.getReservations().add(this);
    }

}
