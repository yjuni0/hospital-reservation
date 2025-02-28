package com.project.reservation.dto.response.notice;

import com.project.reservation.entity.notice.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResNoticeList implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String adminName;
    private String title;
    private String content;
    private LocalDateTime createdDate;

    @Builder
    public ResNoticeList(Long id, String adminName, String title, String content, LocalDateTime createdDate) {
        this.id = id;
        this.adminName = adminName;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }

    public static ResNoticeList fromEntity(Notice notice) {
        return ResNoticeList.builder()
                .id(notice.getId())
                .adminName(notice.getAdmin().getNickName())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdDate(notice.getCreatedDate())
                .build();
    }
}
