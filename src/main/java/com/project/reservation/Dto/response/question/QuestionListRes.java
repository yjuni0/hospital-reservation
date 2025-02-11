package com.project.reservation.Dto.response.question;

import com.project.reservation.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionListRes {
    private Long id;
    private String title;
    private String content;
    private String writerName;
    private Long answerId;
    private LocalDateTime createdDate;

    public static QuestionListRes fromEntity(Question question){
        return QuestionListRes.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .writerName(question.getMember().getNickName())
                .answerId(question.getAnswer().getId())
                .createdDate(question.getCreatedDate())
                .build();
    }
}
