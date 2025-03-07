package com.project.reservation.controller.onlineReserve;

import com.project.reservation.dto.request.reservation.ReqReservation;
import com.project.reservation.dto.response.reservation.ResReservation;
import com.project.reservation.dto.response.reservation.ResReservationList;
import com.project.reservation.entity.member.Member;
import com.project.reservation.service.onlineReserve.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    // 등록
    @PostMapping
    public ResponseEntity<ResReservation> register(@AuthenticationPrincipal Member member, @RequestBody ReqReservation reqReservation) {

        ResReservation ReservationRegister = reservationService.registerReservation(member,reqReservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(ReservationRegister);
    }
    // 예약 리스트 조회
    @GetMapping
    public ResponseEntity<Page<ResReservationList>> getListReservations(@AuthenticationPrincipal Member member, Pageable pageable) {
        Page<ResReservationList> lists = reservationService.listReservation(member,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(lists);
    }
    // 예약 상세 조회
    @GetMapping("/{reservationId}")
    public ResponseEntity<ResReservation> getReservationDetail(@AuthenticationPrincipal Member member, @PathVariable("reservationId") Long reservationId){
        ResReservation resReservation = reservationService.getReservation(member,reservationId);
        return ResponseEntity.status(HttpStatus.OK).body(resReservation);
    }
    // 예약 취소
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Member member, @PathVariable("reservationId") Long reservationId){
        reservationService.deleteReservation(member,reservationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
