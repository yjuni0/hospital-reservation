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
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500,nullable = false)
    private String content;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id", nullable = false)
    private Member admin;

    @Builder
    public Answer(Long id, String content, Question question, Member admin) {
        this.id = id;
        this.content = content;
        this.question = question;
        this.admin = admin;
    }


    public void setMappingAdmin(Member admin){
        this.admin = admin;
        admin.getQuestions().add(question);
    }
    public void setMappingQuestion(Question question){
        this.question = question;
        question.setAnswer(this);
    }
    public void update(String content){
        this.content = content;
    }
}
