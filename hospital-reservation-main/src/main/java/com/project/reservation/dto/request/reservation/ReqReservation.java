package com.project.reservation.dto.request.reservation;


import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.member.Pet;
import com.project.reservation.entity.onlineReserve.Reservation;

import java.time.LocalDateTime;

public record ReqReservation(String petName,Long slotId) {

    public static Reservation toEntity(Member member, Pet pet,String departmentName, LocalDateTime reservationTime) {
        return Reservation.builder()
                .member(member)
                .pet(pet)
                .departmentName(departmentName)
                .reservationTime(reservationTime)
                .build();
    }
}