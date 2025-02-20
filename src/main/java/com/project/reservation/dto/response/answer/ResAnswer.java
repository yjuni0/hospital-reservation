package com.project.reservation.dto.response.answer;

import com.project.reservation.entity.onlineConsult.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResAnswer {
    private Long id;
    private Long questionId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static ResAnswer fromEntity(Answer answer) {
        return ResAnswer.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .questionId(answer.getQuestion().getId())
                .createdDate(answer.getCreatedDate())
                .modifiedDate(answer.getModifiedDate())
                .build();
    }
}
