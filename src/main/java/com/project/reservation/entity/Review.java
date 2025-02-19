package com.project.reservation.entity;

import com.project.reservation.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    private int views;

    private int likes;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    private List<Comment> comments;

    @Builder
    public Review(Long id, String title, String content, int views, int likes, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.member = member;
    }

    public void setMember(Member writerMember) {
        this.member = writerMember;
        writerMember.getReviews().add(this);
    }

    //수정
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //views 증가
    public void upViews() {
        this.views++;
    }

    //likes 증가
    public void upLikes(){
        this.likes++;
    }

    //likes 감소
    public void downLikes(){
        //0 이하로 내려가지 않게
        if (this.likes > 0){
            this.likes--;
        }
    }
}
