package com.project.reservation.controller.redis;

import com.project.reservation.dto.response.notice.ResNoticeList;
import com.project.reservation.dto.response.review.ResReviewList;
import com.project.reservation.entity.customerReviews.Review;
import com.project.reservation.entity.notice.Notice;
import com.project.reservation.service.customerReviews.ReviewService;
import com.project.reservation.service.notice.NoticeService;
import com.project.reservation.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/redis")
public class RedisController {

    private final RedisService redisService;


    @GetMapping("/notices")
    public ResponseEntity<List<ResNoticeList>> getLatestNotices() {
        List<ResNoticeList> lastNotices = redisService.getLatestNotice();
        return ResponseEntity.ok(lastNotices);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ResReviewList>> getTopReviews() {

        log.info("🚀 /api/redis/reviews 엔드포인트 호출됨");
        List<ResReviewList> topReviews = redisService.getTopReview();
        return ResponseEntity.ok(topReviews);
    }

    @GetMapping("/check-cache")
    public ResponseEntity<String> checkCache() {
        redisService.deleteCacheNotices();
        redisService.deleteCacheReviews();
        redisService.checkCache();
        return ResponseEntity.ok("캐시 확인 완료");
    }

}
