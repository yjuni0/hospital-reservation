package com.project.reservation.admin.controller;

import com.project.reservation.Dto.response.reservation.ResReservationList;
import com.project.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/reservation")
public class AdminReservationController {
    private final ReservationService reservationService;

    @GetMapping("/list")
    public ResponseEntity<Page<ResReservationList>> listReservation(Long memberId,Pageable pageable) {
        Page<ResReservationList> reservations = reservationService.listReservation(memberId,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(reservations);
    }

}
