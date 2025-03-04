package com.project.reservation.admin.controller;

import com.project.reservation.dto.response.noticeFile.ResNoticeFileUpload;
import com.project.reservation.service.notice.NoticeFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/notice/{noticeId}/file")
public class AdminNoticeFileController {
    private final NoticeFileService noticeFileService;

    @PostMapping
    public ResponseEntity<List<ResNoticeFileUpload>> uploadFile(@PathVariable("noticeId") Long noticeId, @RequestParam("file") List<MultipartFile> files) {
        List<ResNoticeFileUpload> saveFile = noticeFileService.upload(noticeId,files);
        return ResponseEntity.status(HttpStatus.OK).body(saveFile);
    }
        // 파일 삭제
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Long> deleteFile(@RequestParam("fileId") Long fileId) throws IOException {
        noticeFileService.deleteFile(fileId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
