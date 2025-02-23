package com.project.reservation.controller.onlineConsult;

import com.project.reservation.dto.response.answer.ResAnswer;
import com.project.reservation.service.onlineConsult.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/member/question/{questionId}/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    // 조회
    @GetMapping("/{answerId}")
    public ResponseEntity<ResAnswer> getAnswer(@PathVariable("questionId") Long questionId , @PathVariable("answerId") Long answerId) {
        ResAnswer answer = answerService.getById(questionId,answerId);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }
}
