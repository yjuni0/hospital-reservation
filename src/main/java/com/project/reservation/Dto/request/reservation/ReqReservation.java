package com.project.reservation.Dto.request.reservation;


import com.project.reservation.entity.Reservation;

public record ReqReservation(Long memberId, String availableDate, String slotTime) {
    public static Reservation toEntity(ReqReservation reqReservation){
        return new Reservation()
    }
}
