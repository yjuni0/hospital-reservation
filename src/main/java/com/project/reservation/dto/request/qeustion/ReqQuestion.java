package com.project.reservation.dto.request.qeustion;

import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.onlineConsult.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqQuestion {
    private String title;
    private String content;


    public static Question toEntity(ReqQuestion reqQuestion){
        return Question.builder()
                .title(reqQuestion.getTitle())
                .content(reqQuestion.getContent())
                .build();
    }
}


