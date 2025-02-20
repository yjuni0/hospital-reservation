package com.project.reservation.repository.customerReviews;

import com.project.reservation.entity.customerReviews.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT c FROM Comment c JOIN FETCH c.member m JOIN FETCH c.review r WHERE r.id = :reviewId")
    Page<Comment> findAllWithMemberAndReview(Pageable pageable, Long reviewId);

    @Query(value = "SELECT c FROM Comment c JOIN FETCH c.member m JOIN FETCH c.review r WHERE c.id = :commentId")
    Optional<Comment> findByIdWithMemberAndReview(Long commentId);
}
