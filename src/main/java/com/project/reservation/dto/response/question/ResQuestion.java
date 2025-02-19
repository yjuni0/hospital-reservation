package com.project.reservation.dto.response.question;

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
public class ResQuestion {
    private String title;
    private String content;
    private String writerName;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static ResQuestion fromEntity(Question question){
        return ResQuestion.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .writerName(question.getMember().getNickName())
                .createdDate(question.getCreatedDate())
                .modifiedDate(question.getModifiedDate())
                .build();
    }
}
