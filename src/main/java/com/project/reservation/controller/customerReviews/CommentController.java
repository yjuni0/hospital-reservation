package com.project.reservation.controller.customerReviews;

import com.project.reservation.dto.request.comment.ReqComment;
import com.project.reservation.dto.response.comment.ResComment;
import com.project.reservation.entity.member.Member;
import com.project.reservation.service.customerReviews.CommentService;
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
@RequiredArgsConstructor
@RequestMapping("/api/review/{reviewId}/comment")
public class CommentController {

    private final CommentService commentService;
    // 댓글 목록(페이징)
    @GetMapping
    public ResponseEntity<Page<ResComment>> commentList(
            @PathVariable("reviewId") Long reviewId,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ResComment> commentList = commentService.getComments(pageable, reviewId);
        return ResponseEntity.status(HttpStatus.OK).body(commentList);
    }

    // 댓글 작성
    @PostMapping
    public ResponseEntity<ResComment> write(
            @PathVariable("reviewId") Long reviewId,
            @AuthenticationPrincipal
            Member member,
            @RequestBody ReqComment reqComment) {

        ResComment saveCommentDTO = commentService.createComment(reviewId, member, reqComment);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveCommentDTO);
    }


    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Long> delete(
            @PathVariable("reviewId") Long reviewId,
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal Member member) {

        commentService.deleteComment(commentId, member);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
