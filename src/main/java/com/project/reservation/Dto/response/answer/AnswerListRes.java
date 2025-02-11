package com.project.reservation.Dto.response.answer;

import com.project.reservation.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerListRes {
    private Long id;
    private Long questionId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static AnswerListRes fromEntity(Answer answer){
        return AnswerListRes.builder()
                .id(answer.getId())
                .questionId(answer.getQuestion().getId())
                .content(answer.getContent())
                .createdDate(answer.getCreatedDate())
                .modifiedDate(answer.getModifiedDate())
                .build();
    }
}
