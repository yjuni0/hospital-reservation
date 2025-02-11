package com.project.reservation.controller;

import com.project.reservation.Dto.request.qeustion.QuestionReq;
import com.project.reservation.Dto.response.question.QuestionListRes;
import com.project.reservation.Dto.response.question.QuestionRes;
import com.project.reservation.entity.Member;
import com.project.reservation.entity.Question;
import com.project.reservation.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // 작성
    @PostMapping("/write")
    public ResponseEntity<QuestionRes> write(Member member, @RequestBody QuestionReq req) {
        QuestionRes res = questionService.write(member,req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    // 리스트
    @GetMapping("/list")
    public ResponseEntity<Page<QuestionListRes>> questionList(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<QuestionListRes> listRes = questionService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listRes);
    }

    // 상세조회
    @GetMapping("/qusetionId")
    public ResponseEntity<QuestionRes> detail(@RequestParam("questionId")Long questionId){
        QuestionRes questionDetail = questionService.getById(questionId);
        return ResponseEntity.status(HttpStatus.OK).body(questionDetail);
    }

    // 수정
    @PutMapping("/{questionId}/update")
    public ResponseEntity<QuestionRes> update(@PathVariable Long questionId, @RequestBody QuestionReq req) {
        QuestionRes updateQuestion = questionService.update(questionId,req);
        return ResponseEntity.status(HttpStatus.OK).body(updateQuestion);
    }

    // 삭제
    @DeleteMapping("/{questionId}/delete")
    public ResponseEntity<Long> delete(@PathVariable Long questionId) {
        questionService.delete(questionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
