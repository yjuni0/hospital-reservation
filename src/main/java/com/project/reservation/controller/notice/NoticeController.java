package com.project.reservation.controller.notice;

import com.project.reservation.dto.response.notice.ResNoticeDetail;
import com.project.reservation.common.SearchDto;
import com.project.reservation.dto.response.notice.ResNoticeList;
import com.project.reservation.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;


    // 전체 페이징
    @GetMapping
    public ResponseEntity<Page<ResNoticeList>> noticeList(@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ResNoticeList> listDto = noticeService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDto); //200 반환
    }



    //상세보기
    @GetMapping("/{noticeId}")
    public ResponseEntity<ResNoticeDetail> detail(@PathVariable("noticeId") Long noticeId){
        ResNoticeDetail getDetail = noticeService.getId(noticeId);
        return ResponseEntity.status(HttpStatus.OK).body(getDetail);
    }

}
