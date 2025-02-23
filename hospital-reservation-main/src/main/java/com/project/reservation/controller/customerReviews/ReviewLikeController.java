package com.project.reservation.controller.customerReviews;

import com.project.reservation.dto.request.reviewLike.ReqReviewLike;
import com.project.reservation.service.customerReviews.ReviewLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/review/{reviewId}/like")
public class ReviewLikeController {

    private final ReviewLikeService reviewLikeService;

    // Post - 좋아요를 토글(추가/취소)
    @PostMapping("/toggle")
    public ResponseEntity<String> toggleLike(@RequestBody ReqReviewLike reqReviewLike) {
        String result = reviewLikeService.toggleLike(reqReviewLike);
        return ResponseEntity.ok(result);
    }

    // Get - 사용자가 특정 리뷰에 좋아요를 눌렀는지 여부 반환
    @GetMapping("/status")
    public ResponseEntity<Boolean> hasLiked(@PathVariable Long reviewId, @RequestParam Long memberId) {
        boolean hasLiked = reviewLikeService.hasLiked(reviewId, memberId);
        return ResponseEntity.ok(hasLiked);
    }

    // GET - 총 좋아요 수 정수로 반환
    @GetMapping("/countLikes")
    public ResponseEntity<Integer> getLikesCount(@PathVariable Long reviewId) {
        int likesCount = reviewLikeService.countLike(reviewId);
        return ResponseEntity.ok(likesCount);
    }
}
