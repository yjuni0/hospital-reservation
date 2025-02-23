package com.project.reservation.dto.response.noticeFile;

import com.project.reservation.entity.notice.NoticeFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResNoticeFileUpload {
    private Long id;
    private String originFileName;
    private String path;
    private String type;

    @Builder
    public ResNoticeFileUpload(Long id, String originFileName, String path, String type) {
        this.id = id;
        this.originFileName = originFileName;
        this.path = path;
        this.type = type;
    }

    public static ResNoticeFileUpload fromEntity(NoticeFile file){
        return ResNoticeFileUpload.builder()
                .id(file.getId())
                .originFileName(file.getOriginFileName())
                .path(file.getPath())
                .type(file.getType())
                .build();
    }
}
