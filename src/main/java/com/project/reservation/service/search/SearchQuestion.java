package com.project.reservation.service.search;

import com.project.reservation.common.SearchDto;
import com.project.reservation.dto.response.question.ResQuestionList;
import com.project.reservation.entity.onlineConsult.Question;
import com.project.reservation.repository.onlineConsult.QuestionRepository;
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
public class SearchQuestion {
    private final QuestionRepository questionRepository;
    // 문의 검색
    public Page<ResQuestionList> searchQuestions(SearchDto searchDto, Pageable pageable) {
        Page<Question> result;

        // 조건에 맞는 검색을 메서드로 분리
        if (searchDto.getTitle()!=null&&!searchDto.getTitle().isEmpty()) {
            result = searchByTitle(searchDto.getTitle(), pageable);
        } else if (searchDto.getWriter()!=null&&!searchDto.getWriter().isEmpty()) {
            result = searchByWriter(searchDto.getWriter(), pageable);
        } else if (searchDto.getCreatedDate()!=null&&!searchDto.getCreatedDate().isEmpty()) {
            result = searchByCreatedDate(searchDto.getCreatedDate(), pageable);
        } else {
            throw new IllegalArgumentException("검색 조건이 없습니다.");
        }

        // 결과를 변환하여 리턴
        return convertToPage(result, pageable);
    }

    // 제목으로 검색하는 메서드
    private Page<Question> searchByTitle(String title, Pageable pageable) {
        log.info("검색 요청한 타이틀에 포함된 검색어: {}", title);
        return questionRepository.findByTitleContaining(title, pageable);
    }

    // 작성자로 검색하는 메서드
    private Page<Question> searchByWriter(String writer, Pageable pageable) {
        log.info("검색 요청한 작성자에 포함된 검색어: {}", writer);
        return questionRepository.findByMember_NickNameContaining(writer, pageable);
    }

    // 날짜로 검색하는 메서드
    private Page<Question> searchByCreatedDate(String createdDate, Pageable pageable) {
        try {
            LocalDateTime searchData = LocalDateTime.parse(createdDate);
            log.info("검색 요청한 날짜에 포함된 검색어: {}", searchData);
            return questionRepository.findByCreatedDateContaining(searchData, pageable);
        } catch (Exception e) {
            throw new IllegalArgumentException("날짜 형식이 잘못되었습니다.");
        }
    }

    // 결과를 변환하여 페이지 형태로 반환하는 메서드
    private Page<ResQuestionList> convertToPage(Page<Question> result, Pageable pageable) {
        List<ResQuestionList> list = result.getContent().stream()
                .map(ResQuestionList::fromEntity)
                .collect(Collectors.toList());
        log.info("검색어에 해당하는 리스트: {}", list);
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }
}
