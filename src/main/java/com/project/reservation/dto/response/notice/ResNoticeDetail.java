package com.project.reservation.dto.response.notice;

import com.project.reservation.dto.response.noticeFile.ResNoticeFileDetail;
import com.project.reservation.entity.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<ResNoticeFileDetail> noticeFiles;

    @Builder
    public ResNoticeDetail(Long id, String title, String content, String adminName, LocalDateTime createdTime, LocalDateTime updatedTime, List<ResNoticeFileDetail> noticeFiles) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.adminName = adminName;
        this.noticeFiles = noticeFiles;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public static ResNoticeDetail fromEntity(Notice notice) {
        return ResNoticeDetail.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .adminName(notice.getAdmin().getName())
                .noticeFiles(notice.getNoticeFile().stream().map(ResNoticeFileDetail::fromEntity).collect(Collectors.toList()))
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();
    }
}
