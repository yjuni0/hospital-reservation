package com.project.reservation.Dto.response;

import com.project.reservation.entity.NoticeFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeFileUploadRes {
    private Long id;
    private String originFileName;
    private String path;
    private String type;

    @Builder
    public NoticeFileUploadRes(Long id, String originFileName, String path, String type) {
        this.id = id;
        this.originFileName = originFileName;
        this.path = path;
        this.type = type;
    }

    public static NoticeFileUploadRes fromEntity(NoticeFile file){
        return NoticeFileUploadRes.builder()
                .id(file.getId())
                .originFileName(file.getOriginFileName())
                .path(file.getPath())
                .type(file.getType())
                .build();
    }
}
