package com.project.reservation.repository.customerReviews;

import com.project.reservation.entity.customerReviews.Review;
import com.project.reservation.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {


    // 첫 페이징 화면("/")
    @Query(value = "SELECT r FROM Review r JOIN FETCH r.member")
    Page<Review> findAllWithMemberAndComments(Pageable pageable);

    // 제목 검색
    @Query(value = "SELECT r FROM Review r JOIN FETCH r.member WHERE r.title LIKE %:title%")
    Page<Review> findAllTitleContaining(@Param("title") String title, Pageable pageable);

    // 내용 검색
    @Query(value = "SELECT r FROM Review r JOIN FETCH r.member WHERE r.content LIKE %:content%")
    Page<Review> findAllContentContaining(@Param("content")String content, Pageable pageable);

    // 작성자 검색
    @Query(value = "SELECT r FROM Review r JOIN FETCH r.member WHERE r.member.nickName LIKE %:nickName%")
    Page<Review> findAllNicknameContaining(@Param("nickName")String nickName, Pageable pageable);

    // 리뷰의 총 좋아요 수 조회
    @Query(value = "SELECT COUNT(l) FROM ReviewLike l WHERE l.review.id = :reviewId")
    long countLikesByReview(@Param("reviewId")Long reviewId);

    @Query(value = "SELECT * FROM Review r ORDER BY r.views DESC LIMIT 4", nativeQuery = true)
    List<Review> findTop4ByOrderByViewsDesc();


    Page<Review> findByCreatedDateContaining(LocalDateTime createdDate, Pageable pageable);

    @Query(value = "SELECT * FROM Review r ORDER BY r.likes DESC, r.views DESC  LIMIT 4", nativeQuery = true)
    List<Review> findTop4ByOrderByLikesDescViewsDesc();
}
