package com.project.reservation.Dto.request.reservation;


import com.project.reservation.entity.Member;
import com.project.reservation.entity.Pet;
import com.project.reservation.entity.Reservation;

import java.time.LocalDateTime;

public record ReqReservation(String petName,Long SlotId) {

    public static Reservation toEntity(Member member, Pet pet,String departmentName, LocalDateTime reservationDate) {
        return Reservation.builder()
                .member(member)
                .pet(pet)
                .departmentName(departmentName)
                .reservationTime(reservationDate)
                .build();
    }
}