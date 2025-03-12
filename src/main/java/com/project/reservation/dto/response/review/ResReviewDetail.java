package com.project.reservation.dto.response.review;

import com.project.reservation.dto.response.comment.ResComment;

import com.project.reservation.entity.customerReviews.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ResReviewDetail {

    private Long id;
    private String title;
    private String nickName;
    private String content;
    private LocalDateTime createdDate;
    private Long memberId;
    private int views;
    private int likes;
    private boolean hasLiked;   // 댓글은 dto 에서 제외

    private List<ResComment> comments;

    @Builder
    public ResReviewDetail(Long id, String title, String nickName,Long memberId, String content, LocalDateTime createdDate, int views, int likes, boolean hasLiked, List<ResComment> comments) {
        this.id = id;
        this.title = title;
        this.nickName = nickName;
        this.memberId = memberId;
        this.content = content;
        this.createdDate = createdDate;
        this.views = views;
        this.likes = likes;
        this.hasLiked = hasLiked;
        this.comments = comments;
    }

    // Entity -> DTO
    public static ResReviewDetail fromEntity(Review review){
        return ResReviewDetail.builder()
                .id(review.getId())
                .title(review.getTitle())
                .nickName(review.getMember().getNickName())
                .memberId(review.getMember().getId())
                .content(review.getContent())
                .createdDate(review.getCreatedDate())
                .views(review.getViews())
                .likes(review.getLikes())
                .comments(review.getComments().stream()
                        .map(ResComment::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
