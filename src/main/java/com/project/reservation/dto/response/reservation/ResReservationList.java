package com.project.reservation.dto.response.reservation;

import com.project.reservation.entity.onlineReserve.Reservation;

import java.time.LocalDateTime;

public record ResReservationList(Long id,
                                 String nickName,
                                 String departmentName,
                                 LocalDateTime reservationDateTime,
                                 String petName,
                                 LocalDateTime createdDate) {

    public static ResReservationList fromEntity(Reservation reservation) {
        return new ResReservationList(
                reservation.getId(),
                reservation.getMember().getNickName(),
                reservation.getDepartmentName(),
                reservation.getReservationTime(),
                reservation.getPet().getName(),
                reservation.getCreatedDate()
        );
    }
}
