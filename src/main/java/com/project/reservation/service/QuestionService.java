package com.project.reservation.service;

import com.project.reservation.dto.request.qeustion.ReqQuestion;
import com.project.reservation.dto.response.question.ResQuestionList;
import com.project.reservation.dto.response.question.ResQuestion;
import com.project.reservation.entity.Member;
import com.project.reservation.entity.Question;
import com.project.reservation.entity.Role;
import com.project.reservation.repository.MemberRepository;
import com.project.reservation.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    // 작성
    public ResQuestion write(Member member, ReqQuestion req) {
        Question saveQuestion = ReqQuestion.toEntity(req);
        if (!memberRepository.existsById(member.getId())) {
            throw new IllegalArgumentException("존재하지 않는 멤버입니다. ");
        }
        saveQuestion.setMappingMember(member);
        questionRepository.save(saveQuestion);
        log.info("온라인 문의 작성: {}", saveQuestion);
        return ResQuestion.fromEntity(saveQuestion);
    }

    // 조회 (페이징)
    public Page<ResQuestionList> getAll(Pageable pageable) {
        Page<Question> questions = questionRepository.findAll(pageable);
        List<ResQuestionList> list = questions.getContent().stream()
                .map(ResQuestionList::fromEntity)
                .toList();
        return new PageImpl<>(list, pageable, questions.getTotalElements());
    }

    // 상세 조회
    public ResQuestion getById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> {
                    log.warn("온라인 문의 조회 실패 - 존재하지 않음: id={}", questionId);
                    return new IllegalArgumentException("해당하는 온라인 문의가 존재하지 않습니다.");
                });
        return ResQuestion.fromEntity(question);
    }

    // 수정
    public ResQuestion update(Member member, Long questionId, ReqQuestion req) {
        Question updateQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> {
                    log.warn("온라인 문의 수정 실패 - 존재하지 않음: id={}", questionId);
                    return new IllegalArgumentException("해당하는 온라인 문의가 없습니다.");
                });
        if (!updateQuestion.getMember().getId().equals(member.getId()) && !isAdmin(member)) {
            throw new IllegalArgumentException("자신이 작성한 게시글만 수정할 수 있습니다.");
        }
        updateQuestion.update(req.getTitle(), req.getContent());
        log.info("온라인 문의 수정 완료: id={}, title={}", questionId, req.getTitle());
        return ResQuestion.fromEntity(updateQuestion);
    }

    // 삭제
    public void delete(Member member,Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 온라인 문의가 없습니다."));

        // 작성자와 로그인한 멤버가 일치하거나 관리자인지 확인
        if (!question.getMember().getId().equals(member.getId()) && !isAdmin(member)) {
            throw new IllegalArgumentException("자신이 작성한 게시글만 삭제할 수 있습니다.");
        }

        questionRepository.deleteById(questionId);
        log.info("온라인 문의 삭제 완료: id={}", questionId);
    }

    private boolean isAdmin(Member member) {
        return member.getRoles().equals(Role.ADMIN); // Role.ADMIN이 관리자 역할로 가정
    }
}
