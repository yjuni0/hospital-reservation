package com.project.reservation.admin.controller;

import com.project.reservation.dto.response.reservation.ResReservationList;
import com.project.reservation.entity.member.Member;
import com.project.reservation.service.onlineReserve.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/reservation")
public class AdminReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<Page<ResReservationList>> listReservation(@AuthenticationPrincipal Member member, Pageable pageable) {
        Page<ResReservationList> reservations = reservationService.listReservation(member,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(reservations);
    }

}
