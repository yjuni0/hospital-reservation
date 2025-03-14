package com.project.reservation.service.customerReviews;

import com.project.reservation.common.exception.ResourceNotFoundException;

import com.project.reservation.common.exception.ReviewException;
import com.project.reservation.dto.request.review.ReqReviewUpdate;
import com.project.reservation.dto.request.review.ReqReviewWrite;
import com.project.reservation.dto.response.review.ResReviewDetail;
import com.project.reservation.dto.response.review.ResReviewList;

import com.project.reservation.entity.customerReviews.Review;
import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.member.Role;
import com.project.reservation.repository.customerReviews.ReviewRepository;
import com.project.reservation.repository.member.MemberRepository;
import com.project.reservation.service.redis.RedisService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;
    private final RedisService redisService;

    // 모든 리뷰 조회(페이징) - 페이징
    @Transactional
    public Page<ResReviewList> getReviews(Pageable pageable) {
        // 페이지네이션된 리뷰 목록 조회
        Page<Review> reviews = reviewRepository.findAllWithMemberAndComments(pageable);
        // 리뷰 목록을 ResReviewList로 변환
        List<ResReviewList> resReviewLists = reviews.getContent().stream()
                .map(ResReviewList::fromEntity)
                .collect(Collectors.toList());
        // 페이지네이션된 결과 반환
        return new PageImpl<>(resReviewLists, pageable, reviews.getTotalElements());
    }

    // 리뷰 등록
    @Transactional
    public ResReviewDetail createReview(Member member, ReqReviewWrite reqReviewWrite) {
        // 요청 데이터를 Review 엔티티로 변환
        Review review = ReqReviewWrite.ofEntity(reqReviewWrite);
        // 작성자 회원 정보 조회
        Member writerMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Member", "Member Email", member.getEmail())
        );
        // 리뷰에 작성자 매핑
        review.setMember(writerMember);
        // 리뷰 저장
        Review saveReview = reviewRepository.save(review);
//        redisService.deleteCacheReviews();
        // 저장된 리뷰 데이터 반환
        return ResReviewDetail.fromEntity(saveReview);
    }

    // 리뷰 상세보기
    @Transactional
    public ResReviewDetail readReview(Long reviewId) {
        // 리뷰 ID로 리뷰 조회
        Review findReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "Review Id", String.valueOf(reviewId))
                );
        // 조회수 증가
        findReview.upViews();
        // 조회된 리뷰 데이터 반환
        return ResReviewDetail.fromEntity(findReview);
    }

    // 리뷰 수정
    @Transactional
    public ResReviewDetail updateReview(Long reviewId, ReqReviewUpdate reqReviewUpdate, Member member) {
        // 리뷰 ID로 기존 리뷰 조회
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "Review Id", String.valueOf(reviewId))
                );
        log.info(review.getMember().getEmail());
        // 현재 로그인한 사용자와 리뷰 작성자 비교
        if (!review.getMember().getEmail().equals(member.getUsername())) {
            throw new ReviewException("후기 작성자만 수정할 수 있습니다.", HttpStatus.BAD_REQUEST);
        }

        // 리뷰 내용 수정
        review.update(reqReviewUpdate.getTitle(), reqReviewUpdate.getContent());
        // 수정된 리뷰 저장
        Review savedReview = reviewRepository.save(review);
        // 수정된 리뷰 데이터 반환
        return ResReviewDetail.fromEntity(savedReview);
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId, Member currentMember) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "Review Id", String.valueOf(reviewId)));

        // 현재 로그인한 사용자와 리뷰 작성자 비교
        if (!review.getMember().getId().equals(currentMember.getId()) && !currentMember.getRoles().equals(Role.ADMIN)) {
            throw new ReviewException("후기 작성자만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST);
        }

        // 리뷰 삭제
        reviewRepository.deleteById(reviewId);
    }
}
