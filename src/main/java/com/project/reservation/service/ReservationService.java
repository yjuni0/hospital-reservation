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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
    public ResReservation registerReservation(Member member, ReqReservation reqReservation){
        // 멤버 찾기
        Member rvMember = memberRepository.findById(member.getId()).orElseThrow(()->new IllegalArgumentException("없음"));
        // pet 찾기
        Pet rvPet = petRepository.findByName(reqReservation.petName()).orElseThrow(()->new IllegalArgumentException("없음"));
        // slot Id 로 slot 찾기
        Slot rvSlot = slotRepository.findById(reqReservation.SlotId()).orElseThrow(()->new IllegalArgumentException("없음"));
        // slot Id 로 찾은 slot 으로  예약 날짜 찾기
        AvailableDate rvAvailableDate = rvSlot.getAvailableDate();
        // 예약 날짜 로 department 찾기
        Department rvDepartment = rvAvailableDate.getDepartment();
        // 날짜 데이터 DB 에 저장할 수 있게 파싱
        LocalDateTime rvDateTime = LocalDateTime.of(rvAvailableDate.getDate(),rvSlot.getSlotTime());
        // 찾은 정보로 Reservation 디비에 저장
        Reservation rvSave = ReqReservation.toEntity(rvMember,rvPet,rvDepartment.getName(),rvDateTime);
        reservationRepository.save(rvSave);

        return ResReservation.fromEntity(rvSave);
    }

}
