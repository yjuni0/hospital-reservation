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


