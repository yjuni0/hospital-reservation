package com.project.reservation.controller;

import com.project.reservation.Dto.request.answer.AnswerReq;
import com.project.reservation.Dto.response.answer.AnswerListRes;
import com.project.reservation.Dto.response.answer.AnswerRes;
import com.project.reservation.entity.Answer;
import com.project.reservation.entity.Member;
import com.project.reservation.service.AnswerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/member/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    // 조회
    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerRes> getAnswer(@PathVariable Long answerId) {
        AnswerRes answer = answerService.getById(answerId);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }
// 주석 부분 관리자용으로 이동 예정
//    //리스트
//    @GetMapping("/list")
//    public ResponseEntity<Page<AnswerListRes>> answerList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
//        Page<AnswerListRes> answerListRes = answerService.getAllAnswer(pageable);
//        return ResponseEntity.status(HttpStatus.OK).body(answerListRes);
//    }
//
//    // 작성
//    @PostMapping("/write")
//    public ResponseEntity<AnswerRes> writeAnswer(@PathVariable Long questionId, Member admin, @RequestBody AnswerReq answerReq) {
//        AnswerRes writeAnswer = answerService.write(questionId,admin,answerReq);
//        return ResponseEntity.status(HttpStatus.CREATED).body(writeAnswer);
//    }
//    // 수정
//    @PutMapping("/{answerId}/update")
//    public ResponseEntity<AnswerRes> updateAnswer(@PathVariable Long answerId, @RequestBody AnswerReq answerReq) {
//        AnswerRes updateAnswer = answerService.update(answerId,answerReq);
//        return ResponseEntity.status(HttpStatus.OK).body(updateAnswer);
//    }
//
//    // 삭제
//    @DeleteMapping("/{answerId}/delete")
//    public ResponseEntity<AnswerRes> deleteAnswer(@PathVariable Long answerId) {
//        answerService.delete(answerId);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
}
