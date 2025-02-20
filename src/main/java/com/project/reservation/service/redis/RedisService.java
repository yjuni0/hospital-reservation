package com.project.reservation.service.redis;

import com.project.reservation.dto.response.notice.ResNoticeList;
import com.project.reservation.dto.response.review.ResReviewList;
import com.project.reservation.entity.customerReviews.Review;
import com.project.reservation.entity.notice.Notice;
import com.project.reservation.repository.customerReviews.ReviewRepository;
import com.project.reservation.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RedisService {
    private final NoticeRepository noticeRepository;
    private final ReviewRepository reviewRepository;


    // 공지사항 최근 4개 캐시히트 불러오기
    @Cacheable(value = "notices", key = "'latestNotices'")
    public List<ResNoticeList> getLatestNotice(){
        List<Notice> notices = noticeRepository.findTop4ByOrderByCreatedDateDesc();
        return notices.stream().map(ResNoticeList::fromEntity).toList();
    }

    // 리뷰 4개 캐시 ( 조회수 기준 )
    @Cacheable(value = "reviews", key = "'topReviews'")
    public List<ResReviewList> getTopReview(){
        List<Review> reviews = reviewRepository.findTop4ByOrderByViewsDesc();
        return reviews.stream().map(ResReviewList::fromEntity).toList();
    }

    // 새로운 공지사항 등록 시 캐시 삭제 및 새로 캐싱
    @CacheEvict(value = "notices", key = "'latestNotices'")
    public void deleteCacheNotices(){
    }
}
