package com.project.reservation.controller;

import com.project.reservation.Dto.response.noticeFile.ResNoticeFileDownload;
import com.project.reservation.service.NoticeFileService;
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

//    // 파일 업로드
//    @PostMapping("/upload")
//    public ResponseEntity<List<ResNoticeFileUpload>> uploadFile(@PathVariable Long noticeId, @RequestParam("file") List<MultipartFile> files) {
//        List<ResNoticeFileUpload> saveFile = noticeFileService.upload(noticeId,files);
//        return ResponseEntity.status(HttpStatus.OK).body(saveFile);
//    }
    // 파일 다운로드
    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("fileId") Long fileId) throws IOException {
        ResNoticeFileDownload downloadRes = noticeFileService.download(fileId);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(downloadRes.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=\""+downloadRes.getOriginalFilename()+"\"").body(new ByteArrayResource(downloadRes.getContent()));
    }
//    // 파일 삭제
//    @DeleteMapping("/delete")
//    public ResponseEntity<Long> deleteFile(@RequestParam("fileId") Long fileId) throws IOException {
//        noticeFileService.deleteFile(fileId);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }


}
