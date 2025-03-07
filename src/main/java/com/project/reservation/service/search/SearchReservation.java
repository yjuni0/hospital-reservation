package com.project.reservation.service.search;

import com.project.reservation.common.SearchDto;
import com.project.reservation.dto.response.reservation.ResReservationList;
import com.project.reservation.entity.onlineReserve.Reservation;
import com.project.reservation.repository.onlineReserve.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchReservation {
    private final ReservationRepository reservationRepository;

    // 예약 검색
    public Page<ResReservationList> listReservation(SearchDto searchDto, Pageable pageable) {
        Page<Reservation> result;

        // 조건에 맞는 검색을 메서드로 분리
        if (searchDto.getPetName()!=null&&!searchDto.getPetName().isEmpty()) {
            result = searchByPetName(searchDto.getPetName(), pageable);
        } else if (searchDto.getNickName()!=null&&!searchDto.getNickName().isEmpty()) {
            result = searchByNickName(searchDto.getNickName(), pageable);
        } else if (searchDto.getCreatedDate()!=null&&!searchDto.getCreatedDate().isEmpty()) {
            result = searchByCreatedDate(searchDto.getCreatedDate(), pageable);
        } else {
            throw new IllegalArgumentException("검색 조건이 없습니다.");
        }

        // 결과를 변환하여 리턴
        return convertToPage(result, pageable);
    }

    // 반려동물 이름으로 검색하는 메서드
    private Page<Reservation> searchByPetName(String petName, Pageable pageable) {
        log.info("검색 요청한 반려동물 이름에 포함된 검색어: {}", petName);
        return reservationRepository.findByDepartmentName(petName, pageable);
    }

    // 회원 닉네임으로 검색하는 메서드
    private Page<Reservation> searchByNickName(String nickName, Pageable pageable) {
        log.info("검색 요청한 회원 닉네임에 포함된 검색어: {}", nickName);
        return reservationRepository.findByMember_NickNameContaining(nickName, pageable);
    }

    // 날짜로 검색하는 메서드
    private Page<Reservation> searchByCreatedDate(String createdDate, Pageable pageable) {
        try {
            LocalDateTime searchData = LocalDateTime.parse(createdDate);
            log.info("검색 요청한 날짜에 포함된 검색어: {}", searchData);
            return reservationRepository.findByCreatedDateContaining(searchData, pageable);
        } catch (Exception e) {
            throw new IllegalArgumentException("날짜 형식이 잘못되었습니다.");
        }
    }

    // 결과를 변환하여 페이지 형태로 반환하는 메서드
    private Page<ResReservationList> convertToPage(Page<Reservation> result, Pageable pageable) {
        List<ResReservationList> list = result.getContent().stream()
                .map(ResReservationList::fromEntity)
                .collect(Collectors.toList());
        log.info("검색어에 해당하는 리스트: {}", list);
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }
}