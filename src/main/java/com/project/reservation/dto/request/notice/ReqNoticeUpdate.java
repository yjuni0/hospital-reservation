package com.project.reservation.dto.request.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReqNoticeUpdate {
    private String title;
    private String content;

    @Builder
    public ReqNoticeUpdate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
