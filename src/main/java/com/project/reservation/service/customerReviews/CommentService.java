package com.project.reservation.service.customerReviews;

import com.project.reservation.common.exception.MemberException;
import com.project.reservation.common.exception.ResourceNotFoundException;
import com.project.reservation.common.exception.ReviewException;
import com.project.reservation.dto.request.comment.ReqComment;
import com.project.reservation.dto.response.comment.ResComment;

import com.project.reservation.entity.customerReviews.Comment;
import com.project.reservation.entity.customerReviews.Review;
import com.project.reservation.entity.member.Member;
import com.project.reservation.repository.customerReviews.CommentRepository;
import com.project.reservation.repository.customerReviews.ReviewRepository;
import com.project.reservation.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    //댓글 조회 (페이징) -  pageable 페이징 정보, reviewId 댓글을 조회할 리뷰의 ID
    public Page<ResComment> getComments(Pageable pageable, Long reviewId) {
        // 리뷰 ID에 해당하는 댓글들을 페이징 처리하여 조회 (댓글과 작성자, 리뷰를 함께 가져옴)
        Page<Comment> comments = commentRepository.findByReview_Id(pageable, reviewId);
        // 조회된 댓글 엔티티 리스트를 ResComment DTO 리스트로
        List<ResComment> commentList = comments.getContent().stream()
                .map(ResComment::fromEntity) // 각 Comment 엔티티를 ResComment DTO로 변환
                .collect(Collectors.toList()); // 변환된 결과를 List로 수집
        // 변환된 DTO 리스트와 페이징 정보를 포함한 PageImpl 객체 생성 후 반환
        return new PageImpl<>(commentList, pageable, comments.getTotalElements());
    }

    //댓글 작성 - reviewId 댓글을 작성할 리뷰의 ID, member 댓글 작성자인 회원 정보 (로그인된 사용자) reqComment 요청받은 댓글 데이터 (DTO)
    public ResComment createComment(Long reviewId, Member member, ReqComment reqComment) {
        // 리뷰 ID에 해당하는 리뷰 엔티티 조회 (없으면 예외 발생)
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "Review id", String.valueOf(reviewId)));
        // 현재 로그인된 회원 ID로 회원 엔티티 조회 (없으면 예외 발생)
        Member commentWriter = memberRepository.findById(member.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Member id", String.valueOf(member.getId())));
        // 요청받은 ReqComment DTO를 Comment 엔티티로 변환
        Comment comment = ReqComment.ofEntity(reqComment);
        // 변환된 Comment 엔티티에 리뷰 정보 설정 (연관 관계 매핑)
        comment.setReview(review);
        // 변환된 Comment 엔티티에 작성자 정보 설정 (연관 관계 매핑)
        comment.setMember(commentWriter);
        // 데이터베이스에 댓글 저장 후 저장된 엔티티 반환
        Comment saveComment = commentRepository.save(comment);
        // 저장된 Comment 엔티티를 ResComment DTO로 변환하여 반환
        return ResComment.fromEntity(saveComment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment( Long commentId, Member currentMember) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment id", String.valueOf(commentId)));

        Long memberId = comment.getMember().getId();
        if(!currentMember.getId().equals(memberId)) {
            throw new IllegalArgumentException("너꺼아님");
        }
        commentRepository.deleteById(comment.getId());
        commentRepository.flush();
    }
}