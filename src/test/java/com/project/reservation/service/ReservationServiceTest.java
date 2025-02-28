package com.project.reservation.service;

import com.nimbusds.openid.connect.sdk.claims.Gender;
import com.project.reservation.dto.request.reservation.ReqReservation;
import com.project.reservation.dto.response.reservation.ResReservation;
import com.project.reservation.entity.member.*;
import com.project.reservation.entity.onlineReserve.AvailableDate;
import com.project.reservation.entity.onlineReserve.Department;
import com.project.reservation.entity.onlineReserve.Reservation;
import com.project.reservation.entity.onlineReserve.Slot;
import com.project.reservation.repository.member.MemberRepository;
import com.project.reservation.repository.member.PetRepository;
import com.project.reservation.repository.onlineReserve.AvailableDateRepository;
import com.project.reservation.repository.onlineReserve.DepartmentRepository;
import com.project.reservation.repository.onlineReserve.ReservationRepository;
import com.project.reservation.repository.onlineReserve.SlotRepository;
import com.project.reservation.service.onlineReserve.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private PetRepository petRepository;

    @MockBean
    private SlotRepository slotRepository;

    @MockBean
    private DepartmentRepository departmentRepository;

    @MockBean
    private AvailableDateRepository availableDateRepository;

    private Member member;
    private Pet pet;
    private Slot slot;
    private AvailableDate availableDate;
    private Department department;

    @BeforeEach
    void setUp() {
        member = new Member(1L, "예준", "yejun", "yejun@gmail.com", "1234", "경기도 시흥", "960601", "01011112222", Role.USER);

        department = new Department(1L, "정형외과");
        availableDate = new AvailableDate(1L,department, LocalDate.now());
        slot = new Slot(1L, true, LocalTime.now(),availableDate);
    }

    @Test
    void testRegister(){
        // 가짜 repo 객체
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(petRepository.findByName("똥개")).thenReturn(Optional.of(pet));
        when(slotRepository.findById(1L)).thenReturn(Optional.of(slot));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation(1L, member, pet, department.getName(), LocalDateTime.now()));

        // 테스트 서비스 메소드
        ReqReservation reqReservation = new ReqReservation("똥개",1L);


        // 메서드 실행
        verify(memberRepository).findById(1L);
        verify(petRepository).findByName("똥개");
        verify(slotRepository).findById(1L);
        verify(reservationRepository).save(any(Reservation.class));
    }


}
