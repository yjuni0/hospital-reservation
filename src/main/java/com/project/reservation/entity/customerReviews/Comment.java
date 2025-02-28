package com.project.reservation.entity.customerReviews;

import com.project.reservation.common.BaseTimeEntity;
import com.project.reservation.entity.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Comment(Long id, String content, Member member) {
        this.id = id;
        this.content = content;
        this.member = member;
    }
    public void setReview(Review review) {
        this.review = review;
        review.getComments().add(this);
    }
    public void setMember(Member member) {
        this.member = member;
        member.getComments().add(this);
    }

    public void update(String content) {
        this.content = content;
    }
}
