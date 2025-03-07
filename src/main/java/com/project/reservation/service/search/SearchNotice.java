package com.project.reservation.service.search;

import com.project.reservation.common.SearchDto;
import com.project.reservation.dto.response.notice.ResNoticeList;
import com.project.reservation.entity.notice.Notice;
import com.project.reservation.repository.notice.NoticeRepository;
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
public class SearchNotice {
    private final NoticeRepository noticeRepository;
    // 공지사항 검색
    public Page<ResNoticeList> listNotice(SearchDto searchDto, Pageable pageable) {
        // 조건을 만족하는 검색 결과를 저장할 Page 객체 선언
        Page<Notice> result;

        // 검색 조건에 따른 처리
        if (searchDto.getTitle()!=null&&!searchDto.getTitle().isEmpty()) {
            result = searchByTitle(searchDto.getTitle(), pageable);
        } else if (searchDto.getCreatedDate()!=null&&!searchDto.getCreatedDate().isEmpty()) {
            result = searchByCreatedDate(searchDto.getCreatedDate(), pageable);
        } else {
            throw new IllegalArgumentException("검색 조건이 없습니다.");
        }

        // 결과를 변환하여 리턴
        return convertToPage(result, pageable);
    }

    // 제목으로 검색하는 메서드
    private Page<Notice> searchByTitle(String title, Pageable pageable) {
        log.info("검색 요청한 타이틀에 포함된 검색어: {}", title);
        return noticeRepository.findByTitleContaining(title, pageable);
    }

    // 날짜로 검색하는 메서드
    private Page<Notice> searchByCreatedDate(String createdDate, Pageable pageable) {
        try {
            LocalDateTime searchData = LocalDateTime.parse(createdDate);
            log.info("검색 요청한 날짜에 포함된 검색어: {}", searchData);
            return noticeRepository.findByCreatedDateContaining(searchData, pageable);
        } catch (Exception e) {
            throw new IllegalArgumentException("날짜 형식이 잘못되었습니다.");
        }
    }

    // 결과를 변환하여 페이지 형태로 반환하는 메서드
    private Page<ResNoticeList> convertToPage(Page<Notice> result, Pageable pageable) {
        List<ResNoticeList> list = result.getContent().stream()
                .map(ResNoticeList::fromEntity)
                .collect(Collectors.toList());
        log.info("검색어에 해당하는 리스트: {}", list);
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }
}
