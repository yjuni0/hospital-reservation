package com.project.reservation.controller;

import com.project.reservation.Dto.request.notice.NoticeReq;
import com.project.reservation.Dto.request.notice.NoticeUpdateReq;
import com.project.reservation.common.SearchDto;
import com.project.reservation.Dto.response.notice.NoticeDetailRes;
import com.project.reservation.Dto.response.notice.NoticeListRes;
import com.project.reservation.entity.Member;
import com.project.reservation.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    //작성
    @PostMapping("/write")
    public ResponseEntity<NoticeDetailRes> write(Member member, @RequestBody NoticeReq noticeReq){
        NoticeDetailRes saveNotice = noticeService.create(member,noticeReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveNotice); // 201 반환
    }

    // 전체 페이징
    @GetMapping("/list")
    public ResponseEntity<Page<NoticeListRes>> noticeList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NoticeListRes> listDto = noticeService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDto); //200 반환
    }

    // 페이징 검색
    @GetMapping("/search")
    public ResponseEntity<Page<NoticeListRes>> search(@PageableDefault(size = 10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String title, @RequestParam String content){
        SearchDto searchDto = SearchDto.searchNotice(title,content);
        Page<NoticeListRes> searchList = noticeService.search(searchDto,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(searchList);
    }

    //상세보기
    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeDetailRes> detail(@PathVariable Long noticeId){
        NoticeDetailRes getDetail = noticeService.getId(noticeId);
        return ResponseEntity.status(HttpStatus.OK).body(getDetail);
    }

    // 수정
    @PatchMapping("/{noticeId}/update")
    public ResponseEntity<NoticeDetailRes> update(@PathVariable Long noticeId, @RequestBody NoticeUpdateReq updateReq){
        NoticeDetailRes updateDetail = noticeService.update(noticeId,updateReq);
        return ResponseEntity.status(HttpStatus.OK).body(updateDetail);
    }
    // 삭제
    @DeleteMapping("/{noticeId}/delete")
    public ResponseEntity<Long> delete(@PathVariable Long noticeId){
        noticeService.delete(noticeId);
        return ResponseEntity.status(HttpStatus.OK).build(); //성공시 200 반환
    }


}
