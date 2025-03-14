package com.project.reservation.controller.customerReviews;

import com.project.reservation.common.SearchDto;
import com.project.reservation.dto.request.review.ReqReviewUpdate;
import com.project.reservation.dto.request.review.ReqReviewWrite;
import com.project.reservation.dto.response.review.ResReviewDetail;
import com.project.reservation.dto.response.review.ResReviewList;
import com.project.reservation.entity.member.Member;
import com.project.reservation.service.customerReviews.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 페이징 목록
    @GetMapping("/review")
    public ResponseEntity<Page<ResReviewList>> reviewList(
            @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ResReviewList> listDTO = reviewService.getReviews(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDTO);
    }

    // 리뷰 상세조회
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ResReviewDetail> detail(
            @PathVariable("reviewId")
            Long reviewId
    ) {
        ResReviewDetail getDetail = reviewService.readReview(reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(getDetail);
    }

    // 리뷰 작성
    @PostMapping("/member/review")
    public ResponseEntity<ResReviewDetail> write(
            @AuthenticationPrincipal Member member,
            @RequestBody ReqReviewWrite reqReviewWrite
    ) {
        ResReviewDetail saveReviewDTO = reviewService.createReview(member,reqReviewWrite);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveReviewDTO);
    }

    // 리뷰 수정
    @PutMapping("/member/review/{reviewId}")
    public ResponseEntity<ResReviewDetail> update(
            @PathVariable("reviewId") Long reviewId,
            @RequestBody ReqReviewUpdate reqReviewUpdate,
            @AuthenticationPrincipal Member member) {

        log.info("🔍 현재 로그인한 사용자: {}" ,member.getNickName());

        ResReviewDetail resReviewDetail = reviewService.updateReview(reviewId, reqReviewUpdate, member);
        return ResponseEntity.status(HttpStatus.OK).body(resReviewDetail);
    }
    
    // 리뷰 삭제
    @DeleteMapping("/member/review/{reviewId}")
    public ResponseEntity<Long> delete(
            @PathVariable("reviewId") Long reviewId,
            @AuthenticationPrincipal Member member) {
        reviewService.deleteReview(reviewId, member);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    // GET - 리뷰에 포함된 총 좋아요 개수 반환 API - 불필요
//    @GetMapping("/{reviewId}/likes")
//    public ResponseEntity<Integer> getLikes(
//            @PathVariable("reviewId")
//            @Param("reviewId") Long reviewId) {
//        int likes = reviewService.getLikes(reviewId);
//        return ResponseEntity.status(HttpStatus.OK).body(likes);
//    }
}
