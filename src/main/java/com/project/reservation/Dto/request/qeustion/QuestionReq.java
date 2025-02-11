package com.project.reservation.Dto.request.qeustion;

import com.project.reservation.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionReq {
    private String title;
    private String content;

    public static Question toEntity(QuestionReq questionReq){
        return Question.builder()
                .title(questionReq.getTitle())
                .content(questionReq.getContent())
                .build();
    }
}


