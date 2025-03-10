package com.project.reservation.repository.onlineReserve;

import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.onlineReserve.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findByMember(Member member, Pageable pageable);

    Page<Reservation> findByDepartmentName(String departmentName, Pageable pageable);

    Page<Reservation> findByMember_NickNameContaining(String nickName, Pageable pageable);

    Page<Reservation> findByCreatedDateContaining(LocalDateTime createdDate, Pageable pageable);


    boolean existsByReservationTime(LocalDateTime reservationTime);

}
