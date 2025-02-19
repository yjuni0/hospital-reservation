package com.project.reservation.dto.request.answer;


import com.project.reservation.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqAnswer {
    private String content;

    public static Answer ofEntity(ReqAnswer req){
        return Answer.builder().content(req.content).build();
    }
}
