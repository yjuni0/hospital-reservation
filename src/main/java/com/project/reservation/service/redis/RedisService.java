package com.project.reservation.service.redis;

import com.project.reservation.dto.response.notice.ResNoticeList;
import com.project.reservation.dto.response.review.ResReviewList;
import com.project.reservation.entity.customerReviews.Review;
import com.project.reservation.entity.notice.Notice;
import com.project.reservation.repository.customerReviews.ReviewRepository;
import com.project.reservation.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RedisService {
    private final NoticeRepository noticeRepository;
    private final ReviewRepository reviewRepository;
    private final CacheManager cacheManager;


    // 공지사항 최근 4개 캐시히트 불러오기
    @Cacheable(value = "notices", key = "'latestNotices'")
    public List<ResNoticeList> getLatestNotice(){
        log.info("공지사항 캐시 데이터 없음 DB 조회 실행");
        List<Notice> notices = noticeRepository.findTop4ByOrderByCreatedDateDesc();
        return notices.stream().map(ResNoticeList::fromEntity).toList();
    }

    // 리뷰 4개 캐시 ( 조회수 기준 )
    @Cacheable(value = "reviews", key = "'topReviews'")
    public List<ResReviewList> getTopReview(){
        log.info("리뷰 캐시 데이터 없음 DB 조회 실행");
        List<Review> reviews = reviewRepository.findTop4ByOrderByLikesDescViewsDesc();
        log.info("조회된 리뷰 개수: {}", reviews.stream().map(Review::getId).toList());

        return reviews.stream().map(ResReviewList::fromEntity).toList();
    }

    // 새로운 공지사항 등록 시 캐시 삭제 및 새로 캐싱
    @CacheEvict(value = "notices", key = "'latestNotices'")
    public void deleteCacheNotices(){
        log.info("신규 공지사항 등록 이전 캐시 삭제");
    }
    @CacheEvict(value = "reviews", key = "'topReviews'")
    public void deleteCacheReviews(){
        log.info("신규 공지사항 등록 이전 캐시 삭제");
    }

    public void checkCache() {
        Object cacheData = cacheManager.getCache("notices").get("latestNotices");
        Object cacheData2 = cacheManager.getCache("reviews").get("topReviews");

        if (cacheData == null) {
            log.info("🚨 Redis에서 'latestNotices' 키를 찾을 수 없음");
        } else {
            log.info("✅ Redis에서 캐시 데이터 확인: {}", cacheData);
            log.info("✅ Redis에서 캐시 데이터 확인: {}", cacheData2);

        }
    }
}
