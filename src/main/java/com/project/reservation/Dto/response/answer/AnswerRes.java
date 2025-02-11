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
public class AnswerRes {
    private Long id;
    private Long questionId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static AnswerRes fromEntity(Answer answer) {
        return AnswerRes.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .questionId(answer.getQuestion().getId())
                .createdDate(answer.getCreatedDate())
                .modifiedDate(answer.getModifiedDate())
                .build();
    }
}
