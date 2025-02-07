package com.project.reservation.Dto.response;

import com.project.reservation.entity.Notice;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class NoticeListRes {
    private Long id;
//    private String adminName;
    private String title;
    private String content;

    @Builder
    public NoticeListRes(Long id,String adminName ,String title, String content) {
        this.id = id;
//        this.adminName = adminName;
        this.title = title;
        this.content = content;
    }

    public static NoticeListRes fromEntity(Notice notice) {
        return NoticeListRes.builder()
                .id(notice.getId())
//                .adminName(notice.getAdmin().getName())
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();
    }


}
