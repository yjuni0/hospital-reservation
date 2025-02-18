package com.project.reservation.admin.controller;

import com.project.reservation.Dto.request.answer.ReqAnswer;
import com.project.reservation.Dto.response.answer.ResAnswer;
import com.project.reservation.entity.Member;
import com.project.reservation.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/question/{questionId}/answer/write")
    public ResponseEntity<ResAnswer> writeAnswer(Member admin, @PathVariable Long questionId, @RequestBody ReqAnswer reqAnswer) {
        ResAnswer resAnswer = answerService.write(admin,questionId,reqAnswer);
        return ResponseEntity.status(HttpStatus.OK).body(resAnswer);
    }
    @PutMapping("/{answerId}/update")
    public ResponseEntity<ResAnswer> updateAnswer(@PathVariable Long answerId, @RequestBody ReqAnswer reqAnswer) {
        ResAnswer resAnswer = answerService.update(answerId,reqAnswer);
        return ResponseEntity.status(HttpStatus.OK).body(resAnswer);
    }

    @DeleteMapping("/{answerId}/delete")
    public ResponseEntity<ResAnswer> deleteAnswer(@PathVariable Long answerId) {
        answerService.delete(answerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
