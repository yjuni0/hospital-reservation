package com.project.reservation.dto.response.comment;

import com.project.reservation.entity.customerReviews.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ResComment {

    private Long id;
    private String content;
    private String nickName;
    private LocalDateTime createdDate;

    @Builder
    public ResComment(Long id, String content, String nickName, LocalDateTime createdDate) {
        this.id = id;
        this.content = content;
        this.nickName = nickName;
        this.createdDate = createdDate;
    }

    // Entity -> DTO
    public static ResComment fromEntity(Comment comment) {
        return ResComment.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .nickName(comment.getMember().getNickName())
                .createdDate(comment.getCreatedDate())
                .build();
    }
}




