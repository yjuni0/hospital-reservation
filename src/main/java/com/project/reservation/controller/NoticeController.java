package com.project.reservation.controller;

import com.project.reservation.Dto.response.notice.ResNoticeDetail;
import com.project.reservation.common.SearchDto;
import com.project.reservation.Dto.response.notice.ResNoticeList;
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
// 주석 부분 관리자용으로 이동 예정
//    //작성
//    @PostMapping("/write")
//    public ResponseEntity<ResNoticeDetail> write(Member member, @RequestBody ReqNotice noticeReq){
//        ResNoticeDetail saveNotice = noticeService.create(member,noticeReq);
//        return ResponseEntity.status(HttpStatus.CREATED).body(saveNotice); // 201 반환
//    }

    // 전체 페이징
    @GetMapping("/list")
    public ResponseEntity<Page<ResNoticeList>> noticeList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ResNoticeList> listDto = noticeService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listDto); //200 반환
    }

    // 페이징 검색
    @GetMapping("/search")
    public ResponseEntity<Page<ResNoticeList>> search(@PageableDefault(size = 10,sort = "id",direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String title, @RequestParam String content){
        SearchDto searchDto = SearchDto.searchNotice(title,content);
        Page<ResNoticeList> searchList = noticeService.search(searchDto,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(searchList);
    }

    //상세보기
    @GetMapping("/{noticeId}")
    public ResponseEntity<ResNoticeDetail> detail(@PathVariable Long noticeId){
        ResNoticeDetail getDetail = noticeService.getId(noticeId);
        return ResponseEntity.status(HttpStatus.OK).body(getDetail);
    }

//    // 수정
//    @PatchMapping("/{noticeId}/update")
//    public ResponseEntity<ResNoticeDetail> update(@PathVariable Long noticeId, @RequestBody ReqNoticeUpdate updateReq){
//        ResNoticeDetail updateDetail = noticeService.update(noticeId,updateReq);
//        return ResponseEntity.status(HttpStatus.OK).body(updateDetail);
//    }
//    // 삭제
//    @DeleteMapping("/{noticeId}/delete")
//    public ResponseEntity<Long> delete(@PathVariable Long noticeId){
//        noticeService.delete(noticeId);
//        return ResponseEntity.status(HttpStatus.OK).build(); //성공시 200 반환
//    }


}
