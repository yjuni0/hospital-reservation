package com.project.reservation.controller;

import com.project.reservation.Dto.response.answer.ResAnswer;
import com.project.reservation.service.AnswerService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ResAnswer> getAnswer(@PathVariable Long answerId) {
        ResAnswer answer = answerService.getById(answerId);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }
// 주석 부분 관리자용으로 이동 예정
//    //리스트
//    @GetMapping("/list")
//    public ResponseEntity<Page<ResAnswerList>> answerList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
//        Page<ResAnswerList> answerListRes = answerService.getAllAnswer(pageable);
//        return ResponseEntity.status(HttpStatus.OK).body(answerListRes);
//    }
//
//    // 작성
//    @PostMapping("/write")
//    public ResponseEntity<ResAnswer> writeAnswer(@PathVariable Long questionId, Member admin, @RequestBody ReqAnswer answerReq) {
//        ResAnswer writeAnswer = answerService.write(questionId,admin,answerReq);
//        return ResponseEntity.status(HttpStatus.CREATED).body(writeAnswer);
//    }
//    // 수정
//    @PutMapping("/{answerId}/update")
//    public ResponseEntity<ResAnswer> updateAnswer(@PathVariable Long answerId, @RequestBody ReqAnswer answerReq) {
//        ResAnswer updateAnswer = answerService.update(answerId,answerReq);
//        return ResponseEntity.status(HttpStatus.OK).body(updateAnswer);
//    }
//
//    // 삭제
//    @DeleteMapping("/{answerId}/delete")
//    public ResponseEntity<ResAnswer> deleteAnswer(@PathVariable Long answerId) {
//        answerService.delete(answerId);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }
}
