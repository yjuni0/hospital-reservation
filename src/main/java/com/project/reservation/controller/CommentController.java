package com.project.reservation.controller;

import com.project.reservation.dto.request.comment.ReqComment;
import com.project.reservation.dto.response.comment.ResComment;
import com.project.reservation.entity.Member;
import com.project.reservation.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 목록(페이징)
    @GetMapping("/list")
    public ResponseEntity<Page<ResComment>> commentList(
            @PathVariable Long reviewId,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ResComment> commentList = commentService.getComments(pageable, reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(commentList);
    }

    // 댓글 작성
    @PostMapping("/write")
    public ResponseEntity<ResComment> write(
            @PathVariable Long reviewId,
//            @AuthenticationPrincipal
            Member member,
            @RequestBody ReqComment reqComment) {

        ResComment saveCommentDTO = commentService.createComment(reviewId, member, reqComment);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveCommentDTO);
    }

    // 댓글 수정
    @PutMapping("/{commentId}/update")
    public ResponseEntity<ResComment> update(
            @PathVariable Long commentId,
            @RequestBody ReqComment reqComment) {

        ResComment updateCommentDTO = commentService.updateComment(commentId, reqComment);
        return ResponseEntity.status(HttpStatus.OK).body(updateCommentDTO);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<Long> delete(@PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
