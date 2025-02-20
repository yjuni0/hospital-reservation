package com.project.reservation.dto.response.noticeFile;

import com.project.reservation.entity.notice.NoticeFile;


public record ResNoticeFileDetail(Long id, String originFileName, String type) {
    public static ResNoticeFileDetail fromEntity(NoticeFile noticeFile) {
        return new ResNoticeFileDetail(
                noticeFile.getId(),
                noticeFile.getOriginFileName(),
                noticeFile.getType()
        );
    }

}
