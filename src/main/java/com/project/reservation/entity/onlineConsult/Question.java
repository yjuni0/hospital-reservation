package com.project.reservation.entity.onlineConsult;

import com.project.reservation.common.BaseTimeEntity;
import com.project.reservation.entity.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String title;

    @Column(nullable = false,length = 500)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(nullable = false)
    private Member member;

    @OneToOne(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval = true)
    private Answer answer;

    @Builder
    public Question(Long id, String title, String content, Member member) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
    public void setMember(Member member) {
        this.member = member;
    }
}
