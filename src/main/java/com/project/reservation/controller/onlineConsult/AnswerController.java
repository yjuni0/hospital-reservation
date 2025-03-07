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
    @GetMapping
    public ResponseEntity<ResAnswer> getAnswer(@PathVariable("questionId") Long questionId ) {
        ResAnswer answer = answerService.getById(questionId);
        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }
}
