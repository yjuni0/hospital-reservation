package com.project.reservation.dto.response.reservation;

import com.project.reservation.entity.Reservation;

import java.time.LocalDateTime;

public record ResReservationList(Long reservationId,
                                 String memberName,
                                 LocalDateTime reservationDateTime,
                                 String petName,
                                 LocalDateTime createdDate) {

    public static ResReservationList fromEntity(Reservation reservation) {
        return new ResReservationList(
                reservation.getId(),
                reservation.getMember().getName(),
                reservation.getReservationTime(),
                reservation.getPet().getName(),
                reservation.getCreatedDate()
        );
    }
}
