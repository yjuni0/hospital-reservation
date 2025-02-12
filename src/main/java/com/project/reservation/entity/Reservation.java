package com.project.reservation.entity;

import com.project.reservation.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Member member;

    @OneToOne
    @JoinColumn(nullable = false)
    private Slot slot;


    @Builder
    public Reservation(Long id, Member member, Slot slot) {
        this.id = id;
        this.member = member;
        this.slot = slot;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getReservations().add(this);
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
        slot.setReservation(this);
    }
}
