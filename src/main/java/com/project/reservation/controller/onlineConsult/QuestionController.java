package com.project.reservation.controller.onlineConsult;

import com.project.reservation.dto.request.qeustion.ReqQuestion;
import com.project.reservation.dto.response.question.ResQuestionList;
import com.project.reservation.dto.response.question.ResQuestion;
import com.project.reservation.entity.member.Member;
import com.project.reservation.service.onlineConsult.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // 작성
    @PostMapping
    public ResponseEntity<ResQuestion> write(@AuthenticationPrincipal Member member, @RequestBody ReqQuestion req) {
        ResQuestion res = questionService.write(member,req);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    // 리스트
    @GetMapping
    public ResponseEntity<Page<ResQuestionList>> questionList(@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<ResQuestionList> listRes = questionService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listRes);
    }

    // 상세조회
    @GetMapping("/{questionId}")
    public ResponseEntity<ResQuestion> detail(@AuthenticationPrincipal Member member, @PathVariable("questionId") Long questionId){
        ResQuestion questionDetail = questionService.getById(member, questionId);
        return ResponseEntity.status(HttpStatus.OK).body(questionDetail);
    }

    // 수정
    @PutMapping("/{questionId}")
    public ResponseEntity<ResQuestion> update(@AuthenticationPrincipal Member member,@PathVariable("questionId") Long questionId, @RequestBody ReqQuestion req) {
        ResQuestion updateQuestion = questionService.update(member,questionId,req);
        return ResponseEntity.status(HttpStatus.OK).body(updateQuestion);
    }

    // 삭제
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Long> delete(@AuthenticationPrincipal Member member,@PathVariable("questionId") Long questionId) {
        questionService.delete(member,questionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
