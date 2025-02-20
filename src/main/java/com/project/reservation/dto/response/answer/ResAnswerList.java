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
public class ResAnswerList {
    private Long id;
    private Long questionId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static ResAnswerList fromEntity(Answer answer){
        return ResAnswerList.builder()
                .id(answer.getId())
                .questionId(answer.getQuestion().getId())
                .content(answer.getContent())
                .createdDate(answer.getCreatedDate())
                .modifiedDate(answer.getModifiedDate())
                .build();
    }
}
