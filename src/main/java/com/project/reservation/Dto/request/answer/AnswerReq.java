package com.project.reservation.Dto.request.answer;


import com.project.reservation.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerReq {
    private String content;

    public static Answer ofEntity(AnswerReq req){
        return Answer.builder().content(req.content).build();
    }
}
