package com.project.reservation.controller;

import com.project.reservation.Dto.request.reservation.ReqReservation;
import com.project.reservation.Dto.response.reservation.ResReservation;
import com.project.reservation.Dto.response.reservation.ResReservationList;
import com.project.reservation.entity.Member;
import com.project.reservation.repository.ReservationRepository;
import com.project.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class ReservationController {
    private final ReservationService reservationService;


    @PostMapping("/reservation/register")
    public ResponseEntity<ResReservation> register(@RequestParam("memberId") Long memberId, @RequestBody ReqReservation reqReservation) {
        ResReservation ReservationRegister = reservationService.registerReservation(memberId,reqReservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(ReservationRegister);
    }

    @GetMapping("/{memberId}/reservation")
    public ResponseEntity<Page<ResReservationList>> getListReservations(@PathVariable Long memberId, Pageable pageable) {
        Page<ResReservationList> lists = reservationService.listReservation(memberId,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(lists);
    }

    @GetMapping("/{memberId}/reservation/{reservationId}")
    public ResponseEntity<ResReservation> getReservationDetail(@PathVariable Long memberId, @PathVariable Long reservationId){
        ResReservation resReservation = reservationService.getReservation(reservationId,memberId);
        return ResponseEntity.status(HttpStatus.OK).body(resReservation);
    }

    @DeleteMapping("/{memberId}/reservation/{reservationId}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long memberId, @PathVariable Long reservationId){
        reservationService.deleteReservation(memberId,reservationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
