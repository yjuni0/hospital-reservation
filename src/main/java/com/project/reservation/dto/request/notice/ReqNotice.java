package com.project.reservation.dto.request.notice;

import com.project.reservation.entity.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReqNotice {
    private String title;
    private String content;

    @Builder
    public ReqNotice(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Notice ofEntity(ReqNotice req) {
        return Notice.builder().title(req.getTitle()).content(req.getContent()).build();
    }
}
