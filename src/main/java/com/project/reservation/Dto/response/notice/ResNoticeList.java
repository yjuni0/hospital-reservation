package com.project.reservation.Dto.response.notice;

import com.project.reservation.entity.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResNoticeList {
    private Long id;
    private String adminName;
    private String title;
    private String content;

    @Builder
    public ResNoticeList(Long id, String adminName , String title, String content) {
        this.id = id;
        this.adminName = adminName;
        this.title = title;
        this.content = content;
    }

    public static ResNoticeList fromEntity(Notice notice) {
        return ResNoticeList.builder()
                .id(notice.getId())
                .adminName(notice.getAdmin().getName())
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();
    }


}
