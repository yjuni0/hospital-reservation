package com.project.reservation.dto.request.answer;


import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.onlineConsult.Answer;
import com.project.reservation.entity.onlineConsult.Question;
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

    public static Answer ofEntity(ReqAnswer req, Member member, Question question) {
        return Answer.builder().content(req.content).admin(member).question(question).build();
    }
}
