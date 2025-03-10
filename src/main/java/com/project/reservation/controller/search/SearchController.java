package com.project.reservation.controller.search;

import com.project.reservation.common.SearchDto;
import com.project.reservation.dto.response.member.ResMemberList;
import com.project.reservation.dto.response.notice.ResNoticeList;
import com.project.reservation.dto.response.question.ResQuestionList;
import com.project.reservation.dto.response.reservation.ResReservationList;
import com.project.reservation.dto.response.review.ResReviewList;
import com.project.reservation.service.search.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
    private final SearchMember searchMember;
    private final SearchQuestion searchQuestion;
    private final SearchNotice searchNotice;
    private final SearchReservation searchReservation;
    private final SearchReview searchReview;

    @PostMapping
    public ResponseEntity<?> search(@RequestParam("type") String type, @RequestBody SearchDto searchDto, @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        // 타입에 따라 해당하는 서비스 메서드를 호출
        switch (type) {
            case "member":
                Object memberPage = searchMember.searchMember(searchDto, pageable);
                return new ResponseEntity<>(memberPage, HttpStatus.OK);
            case "question":
                Object questionPage = searchQuestion.searchQuestions(searchDto, pageable);
                return new ResponseEntity<>(questionPage, HttpStatus.OK);
            case "notice":
                Object noticePage = searchNotice.listNotice(searchDto, pageable);
                return new ResponseEntity<>(noticePage, HttpStatus.OK);
            case "reservation":
                Object reservationPage = searchReservation.listReservation(searchDto, pageable);
                return new ResponseEntity<>(reservationPage, HttpStatus.OK);
            case "review":
                Object reviewPage = searchReview.listReview(searchDto, pageable);
                return new ResponseEntity<>(reviewPage, HttpStatus.OK);
            default:
                return new ResponseEntity<>("잘못된 검색 타입입니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
