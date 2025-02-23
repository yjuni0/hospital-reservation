package com.project.reservation.dto.response.noticeFile;

import com.project.reservation.entity.notice.NoticeFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class ResNoticeFileDownload {
    private String originalFilename;
    private String fileType;
    private byte[] content;

    @Builder
    public ResNoticeFileDownload(String originalFilename, String fileType, byte[] content) {
        this.originalFilename = originalFilename;
        this.fileType = fileType;
        this.content = content;
    }

    public static ResNoticeFileDownload fromFileResource(NoticeFile file, String contentType, byte[] content){
        return ResNoticeFileDownload.builder()
                .originalFilename(file.getOriginFileName())
                .fileType(contentType)
                .content(content)
                .build();
    }
}
