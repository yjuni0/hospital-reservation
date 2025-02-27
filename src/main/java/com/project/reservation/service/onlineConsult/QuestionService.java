package com.project.reservation.service.onlineConsult;

import com.project.reservation.dto.request.qeustion.ReqQuestion;
import com.project.reservation.dto.response.question.ResQuestionList;
import com.project.reservation.dto.response.question.ResQuestion;
import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.onlineConsult.Question;
import com.project.reservation.entity.member.Role;
import com.project.reservation.repository.member.MemberRepository;
import com.project.reservation.repository.onlineConsult.QuestionRepository;
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
        Member wrtierMember = memberRepository.findById(member.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));
        Question saveQuestion = ReqQuestion.toEntity(req);
        saveQuestion.setMember(wrtierMember);
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
    public void delete(Long questionId) {
        questionRepository.deleteById(questionId);
        log.info("온라인 문의 삭제 완료: id={}", questionId);
    }

    private boolean isAdmin(Member member) {
        return member.getRoles().equals(Role.ADMIN); // Role.ADMIN이 관리자 역할로 가정
    }
}
