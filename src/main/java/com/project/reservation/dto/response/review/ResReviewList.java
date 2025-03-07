package com.project.reservation.dto.response.review;

import com.project.reservation.entity.customerReviews.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ResReviewList implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String content;
    private String nickName;
    private LocalDateTime createdDate;
    private int views;
    private int likes;

    @Builder
    public ResReviewList(Long id, String title,String content, String nickName, LocalDateTime createdDate, int views, int likes) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.nickName = nickName;
        this.createdDate = createdDate;
        this.views = views;
        this.likes = likes;
    }

    // Entity -> DTO
    public static ResReviewList fromEntity(Review review){
        return ResReviewList.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .nickName(review.getMember().getNickName())
                .createdDate(review.getCreatedDate())
                .views(review.getViews())
                .likes(review.getLikes())
                .build();
    }
}
