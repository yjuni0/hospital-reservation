package com.project.reservation.Dto.request;

import com.project.reservation.entity.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class NoticeReq {
    private String title;
    private String content;

    @Builder
    public NoticeReq(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Notice ofEntity(NoticeReq req) {
        return Notice.builder().title(req.getTitle()).content(req.getContent()).build();
    }
}
