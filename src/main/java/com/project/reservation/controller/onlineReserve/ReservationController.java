package com.project.reservation.controller.onlineReserve;

import com.project.reservation.dto.request.reservation.ReqReservation;
import com.project.reservation.dto.response.reservation.ResReservation;
import com.project.reservation.dto.response.reservation.ResReservationList;
import com.project.reservation.service.onlineReserve.ReservationService;
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

    // 등록
    @PostMapping("/reservation/register")
    public ResponseEntity<ResReservation> register(@RequestParam("memberId") Long memberId, @RequestBody ReqReservation reqReservation) {
        ResReservation ReservationRegister = reservationService.registerReservation(memberId,reqReservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(ReservationRegister);
    }
    // 예약 리스트 조회
    @GetMapping("/{memberId}/reservation")
    public ResponseEntity<Page<ResReservationList>> getListReservations(@PathVariable("memberId") Long memberId, Pageable pageable) {
        Page<ResReservationList> lists = reservationService.listReservation(memberId,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(lists);
    }
    // 예약 상세 조회
    @GetMapping("/{memberId}/reservation/{reservationId}")
    public ResponseEntity<ResReservation> getReservationDetail(@PathVariable("memberId") Long memberId, @PathVariable("reservationId") Long reservationId){
        ResReservation resReservation = reservationService.getReservation(memberId,reservationId);
        return ResponseEntity.status(HttpStatus.OK).body(resReservation);
    }
    // 예약 취소
    @DeleteMapping("/{memberId}/reservation/{reservationId}/delete")
    public ResponseEntity<Void> delete(@PathVariable("memberId") Long memberId, @PathVariable("reservationId") Long reservationId){
        reservationService.deleteReservation(memberId,reservationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
