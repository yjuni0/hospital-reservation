package com.project.reservation.repository.customerReviews;

import com.project.reservation.entity.customerReviews.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByReview_Id(Pageable pageable, Long reviewId);

}
