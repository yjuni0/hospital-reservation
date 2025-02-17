package com.project.reservation.Dto.response.reservation;


import com.project.reservation.entity.Reservation;

import java.time.LocalDateTime;

public record ResReservation(
        Long reservationId,
        String memberName,
        String petName,
        String departmentName,
        String availableDate,
        String slotTime,
        LocalDateTime createdDate
) {
    public static ResReservation fromEntity(Reservation reservation){
        return new ResReservation(
                reservation.getId(),
                reservation.getMember().getName(),
                reservation.getPet().getName(),
                reservation.getDepartmentName(),
                reservation.getReservationTime().toLocalDate().toString(),
                reservation.getReservationTime().toLocalTime().toString(),
                reservation.getCreatedDate()
                );

    }
}
