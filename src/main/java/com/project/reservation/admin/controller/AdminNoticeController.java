package com.project.reservation.admin.controller;

import com.project.reservation.dto.request.notice.ReqNotice;
import com.project.reservation.dto.request.notice.ReqNoticeUpdate;
import com.project.reservation.dto.response.notice.ResNoticeDetail;
import com.project.reservation.entity.member.Member;
import com.project.reservation.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/notice")
public class AdminNoticeController {
    private final NoticeService noticeService;
    //작성
    @PostMapping
    public ResponseEntity<ResNoticeDetail> write(@AuthenticationPrincipal Member admin, @RequestBody ReqNotice noticeReq){
        ResNoticeDetail saveNotice = noticeService.create(admin,noticeReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveNotice); // 201 반환
    }
    // 수정
    @PutMapping("/{noticeId}")
    public ResponseEntity<ResNoticeDetail> update(@PathVariable("noticeId") Long noticeId, @RequestBody ReqNoticeUpdate updateReq){
        ResNoticeDetail updateDetail = noticeService.update(noticeId,updateReq);
        return ResponseEntity.status(HttpStatus.OK).body(updateDetail);
    }
    // 삭제
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<Long> delete(@PathVariable("noticeId") Long noticeId){
        noticeService.delete(noticeId);
        return ResponseEntity.status(HttpStatus.OK).build(); //성공시 200 반환
    }

}
