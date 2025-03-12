package com.project.reservation.controller.customerReviews;
import com.project.reservation.dto.request.reviewLike.ReqReviewLike;

import com.project.reservation.entity.member.Member;
import com.project.reservation.service.customerReviews.ReviewLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member/review/{reviewId}/like")
public class ReviewLikeController {

    private final ReviewLikeService reviewLikeService;

    // Post - 좋아요를 토글(추가/취소)
    @PostMapping("/toggle")
    public ResponseEntity<String> toggleLike(
            @AuthenticationPrincipal Member member,
            @RequestBody ReqReviewLike reqReviewLike) {
        String result = reviewLikeService.toggleLike(member, reqReviewLike);
        return ResponseEntity.ok(result);
    }


    @GetMapping("/status")
    public ResponseEntity<Boolean> hasLiked(
            @PathVariable("reviewId") Long reviewId,
            @AuthenticationPrincipal Member member) {
        boolean hasLiked = reviewLikeService.hasLiked(reviewId, member.getId());
        return ResponseEntity.ok(hasLiked);
    }
}

