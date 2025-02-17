package com.project.reservation.service;

import com.project.reservation.Dto.request.reservation.ReqReservation;
import com.project.reservation.Dto.response.reservation.ResReservation;
import com.project.reservation.Dto.response.reservation.ResReservationList;
import com.project.reservation.entity.*;
import com.project.reservation.repository.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final PetRepository petRepository;
    private final SlotRepository slotRepository;

    // 예약 등록
    public ResReservation registerReservation(Member member, ReqReservation reqReservation) {
        log.info("예약 등록 요청: memberId={}, petName={}, slotId={}", member.getId(), reqReservation.petName(), reqReservation.SlotId());

        Member rvMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> {
                    log.error("멤버가 존재하지 않음: memberId={}", member.getId());
                    return new IllegalArgumentException("멤버가 없음");
                });

        Pet rvPet = petRepository.findByName(reqReservation.petName())
                .orElseThrow(() -> {
                    log.error("펫이 존재하지 않음: petName={}", reqReservation.petName());
                    return new IllegalArgumentException("펫이 없음");
                });

        Slot rvSlot = slotRepository.findById(reqReservation.SlotId())
                .orElseThrow(() -> {
                    log.error("슬롯이 존재하지 않음: slotId={}", reqReservation.SlotId());
                    return new IllegalArgumentException("슬롯이 없음");
                });

        AvailableDate rvAvailableDate = rvSlot.getAvailableDate();
        Department rvDepartment = rvAvailableDate.getDepartment();
        LocalDateTime rvDateTime = LocalDateTime.of(rvAvailableDate.getDate(), rvSlot.getSlotTime());

        log.info("예약 정보: department={}, dateTime={}", rvDepartment.getName(), rvDateTime);

        Reservation rvSave = ReqReservation.toEntity(rvMember, rvPet, rvDepartment.getName(), rvDateTime);
        rvSave.setMember(rvMember);
        rvSlot.setIsAvailable(false);
        reservationRepository.save(rvSave);

        log.info("예약 저장 완료: reservationId={}", rvSave.getId());
        return ResReservation.fromEntity(rvSave);
    }

    // 예약 조회 - 본인 예약만 확인 가능
    public ResReservation getReservation(Member member, Long reservationId) {
        log.info("예약 조회 요청: memberId={}, reservationId={}", member.getId(), reservationId);

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> {
                    log.error("예약이 존재하지 않음: reservationId={}", reservationId);
                    return new IllegalArgumentException("해당 아이디의 예약이 없음");
                });

        if (!reservation.getMember().getId().equals(member.getId())) {
            log.warn("예약 조회 권한 없음: 요청자={}, 예약 소유자={}", member.getId(), reservation.getMember().getId());
            throw new IllegalArgumentException("자신의 예약만 조회 가능합니다.");
        }

        log.info("예약 조회 성공: reservationId={}", reservationId);
        return ResReservation.fromEntity(reservation);
    }

    // 예약 목록 조회 - 관리자와 사용자 구분
    public Page<ResReservationList> listReservation(Member member, Pageable pageable) {
        log.info("예약 목록 조회 요청: memberId={}, role={}", member.getId(), member.getRole());

        Page<Reservation> reservations;
        if (member.getRole().equals(Role.ADMIN)) {
            reservations = reservationRepository.findAll(pageable);
            log.info("관리자 모든 예약 조회: total={}", reservations.getTotalElements());
        } else {
            reservations = reservationRepository.findByMember(member, pageable);
            log.info("사용자 본인 예약 조회: total={}", reservations.getTotalElements());
        }

        List<ResReservationList> resReservationList = reservations.getContent().stream()
                .map(ResReservationList::fromEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(resReservationList, pageable, reservations.getTotalElements());
    }

    // 예약 취소
    public void deleteReservation(Member member, Long reservationId) {
        log.info("예약 취소 요청: memberId={}, reservationId={}", member.getId(), reservationId);

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> {
                    log.error("예약이 존재하지 않음: reservationId={}", reservationId);
                    return new IllegalArgumentException("해당 아이디의 예약이 없음");
                });

        if (!reservation.getMember().getId().equals(member.getId()) && member.getRole().equals(Role.ADMIN)) {
            log.warn("예약 삭제 권한 없음: 요청자={}, 예약 소유자={}", member.getId(), reservation.getMember().getId());
            throw new IllegalArgumentException("자신의 예약만 삭제 가능합니다.");
        }

        reservationRepository.delete(reservation);
        log.info("예약 취소 성공: reservationId={}", reservationId);
    }
}
