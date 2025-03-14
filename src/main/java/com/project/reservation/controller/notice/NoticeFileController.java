package com.project.reservation.controller.notice;

import com.project.reservation.dto.response.noticeFile.ResNoticeFileDownload;
import com.project.reservation.service.notice.NoticeFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/member/notice/{noticeId}/file")
@RequiredArgsConstructor
public class NoticeFileController {
    private final NoticeFileService noticeFileService;


    // 파일 다운로드
    @GetMapping("/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("noticeId")Long noticeId, @PathVariable("fileId") Long fileId) throws IOException {
        ResNoticeFileDownload downloadRes = noticeFileService.download(noticeId,fileId);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(downloadRes.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=\""+downloadRes.getOriginalFilename()+"\"").body(new ByteArrayResource(downloadRes.getContent()));
    }

}
