package com.project.reservation.Dto.response;

import com.project.reservation.entity.NoticeFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class NoticeFileDownloadRes {
    private String originalFilename;
    private String fileType;
    private byte[] content;

    @Builder
    public NoticeFileDownloadRes(String originalFilename, String fileType, byte[] content) {
        this.originalFilename = originalFilename;
        this.fileType = fileType;
        this.content = content;
    }

    public static NoticeFileDownloadRes fromFileResource(NoticeFile file, String contentType, byte[] content){
        return NoticeFileDownloadRes.builder()
                .originalFilename(file.getOriginFileName())
                .fileType(contentType)
                .content(content)
                .build();
    }
}
