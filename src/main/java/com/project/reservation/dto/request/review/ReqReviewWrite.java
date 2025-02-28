package com.project.reservation.dto.request.review;

import com.project.reservation.entity.customerReviews.Review;
import com.project.reservation.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ReqReviewWrite {

    private String title;
    private String content;

    @Builder
    public ReqReviewWrite(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Review ofEntity(ReqReviewWrite reqReviewWrite){
        return Review.builder()
                .title(reqReviewWrite.getTitle())
                .content(reqReviewWrite.getContent())
                .build();
    }
}