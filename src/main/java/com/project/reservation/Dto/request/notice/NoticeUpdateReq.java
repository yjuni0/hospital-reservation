package com.project.reservation.Dto.request.notice;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticeUpdateReq {
    private String title;
    private String content;

    @Builder
    public NoticeUpdateReq(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
