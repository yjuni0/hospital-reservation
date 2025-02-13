package com.project.reservation.Dto.response.notice;

import com.project.reservation.entity.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
public class ResNoticeDetail {
    private Long id;
    private String title;
    private String content;
    private String adminName;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    @Builder
    public ResNoticeDetail(Long id, String title, String content, String adminName, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.adminName = adminName;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public static ResNoticeDetail fromEntity(Notice notice) {
        return ResNoticeDetail.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .adminName(notice.getAdmin().getName())
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();
    }
}
