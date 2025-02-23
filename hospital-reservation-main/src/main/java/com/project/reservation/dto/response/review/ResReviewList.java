package com.project.reservation.dto.response.review;

import com.project.reservation.entity.customerReviews.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

public class ResReviewList {

    private Long reviewId;
    private String title;
    private String nickName;
    private LocalDateTime createdDate;
    private int views;
    private int likes;

    @Builder
    public ResReviewList(Long reviewId, String title, String nickName, LocalDateTime createdDate, int views, int likes) {
        this.reviewId = reviewId;
        this.title = title;
        this.nickName = nickName;
        this.createdDate = createdDate;
        this.views = views;
        this.likes = likes;
    }

    // Entity -> DTO
    public static ResReviewList fromEntity(Review review){
        return ResReviewList.builder()
                .reviewId(review.getId())
                .title(review.getTitle())
                .nickName(review.getMember().getNickName())
                .createdDate(review.getCreatedDate())
                .views(review.getViews())
                .likes(review.getLikes())
                .build();
    }
}
