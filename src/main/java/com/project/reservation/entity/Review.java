package com.project.reservation.entity;

import com.project.reservation.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length=500, nullable = false)
    private String content;

    private Long count;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReviewLike> likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    private List<Comment> comments;

    public Review(Long id, String title, String content, Long count, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.count = count;
        this.member = member;
    }
}
