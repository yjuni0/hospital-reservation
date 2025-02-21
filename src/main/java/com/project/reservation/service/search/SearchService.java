package com.project.reservation.service.search;

import com.project.reservation.common.SearchDto;
import com.project.reservation.dto.response.member.ResMemberList;
import com.project.reservation.dto.response.notice.ResNoticeList;
import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.notice.Notice;
import com.project.reservation.repository.customerReviews.ReviewRepository;
import com.project.reservation.repository.member.MemberRepository;
import com.project.reservation.repository.member.PetRepository;
import com.project.reservation.repository.notice.NoticeRepository;
import com.project.reservation.repository.onlineReserve.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final PetRepository petRepository;

    // 공지사항 검색
    public Page<ResNoticeList> notice(SearchDto searchDto, Pageable pageable) {
        Page<Notice> result = null;
        if (!searchDto.getTitle().isEmpty()) {
            result = noticeRepository.findByTitleContaining(searchDto.getTitle(), pageable);
            log.info("검색 요청한 타이틀에 포함된 검색어{}", result);
        } else if (!searchDto.getContent().isEmpty()) {
            result = noticeRepository.findByContentContaining(searchDto.getContent(), pageable);
            log.info("검색 요청한 컨텐트에 포함된 검색어{}", result);
        }
        List<ResNoticeList> list = result.getContent().stream().map(ResNoticeList::fromEntity).collect(Collectors.toList());
        log.info("검색어에 해당하는 리스트{}", list);
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }
//    // 멤버 검색
//    public Page<ResMemberList> member(SearchDto searchDto, Pageable pageable) {
//        Page<Member> result = null;
//
//    }


}