package com.project.reservation.admin.controller;

import com.project.reservation.Dto.request.notice.ReqNotice;
import com.project.reservation.Dto.request.notice.ReqNoticeUpdate;
import com.project.reservation.Dto.response.notice.ResNoticeDetail;
import com.project.reservation.entity.Member;
import com.project.reservation.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/notice")
public class AdminNoticeController {
    private final NoticeService noticeService;
    //작성
    @PostMapping("/write")
    public ResponseEntity<ResNoticeDetail> write(Member member, @RequestBody ReqNotice noticeReq){
        ResNoticeDetail saveNotice = noticeService.create(member,noticeReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveNotice); // 201 반환
    }
        // 수정
    @PatchMapping("/{noticeId}/update")
    public ResponseEntity<ResNoticeDetail> update(@PathVariable("noticeId") Long noticeId, @RequestBody ReqNoticeUpdate updateReq){
        ResNoticeDetail updateDetail = noticeService.update(noticeId,updateReq);
        return ResponseEntity.status(HttpStatus.OK).body(updateDetail);
    }
    // 삭제
    @DeleteMapping("/{noticeId}/delete")
    public ResponseEntity<Long> delete(@PathVariable("noticeId") Long noticeId){
        noticeService.delete(noticeId);
        return ResponseEntity.status(HttpStatus.OK).build(); //성공시 200 반환
    }

}
