package com.project.reservation.dto.response.question;

import com.project.reservation.entity.onlineConsult.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResQuestionList {
    private Long id;
    private String title;
    private String content;
    private String writerName;
    private LocalDateTime createdDate;

    public static ResQuestionList fromEntity(Question question){
        return ResQuestionList.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .writerName(question.getMember().getNickName())
                .createdDate(question.getCreatedDate())
                .build();
    }
}
