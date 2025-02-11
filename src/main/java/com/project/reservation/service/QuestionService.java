package com.project.reservation.service;

import com.project.reservation.Dto.request.qeustion.QuestionReq;
import com.project.reservation.Dto.response.question.QuestionListRes;
import com.project.reservation.Dto.response.question.QuestionRes;
import com.project.reservation.entity.Member;
import com.project.reservation.entity.Question;
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

    // 작성
    public QuestionRes write(Member member, QuestionReq req) {
        Question saveQuestion = QuestionReq.toEntity(req);
        saveQuestion.setMappingMember(member);
        questionRepository.save(saveQuestion);
        log.info("온라인 문의 작성: {}", saveQuestion);
        return QuestionRes.fromEntity(saveQuestion);
    }

    // 조회 (페이징)
    public Page<QuestionListRes> getAll(Pageable pageable) {
        Page<Question> questions = questionRepository.findAll(pageable);
        List<QuestionListRes> list = questions.getContent().stream()
                .map(QuestionListRes::fromEntity)
                .toList();
        return new PageImpl<>(list, pageable, questions.getTotalElements());
    }

    // 상세 조회
    public QuestionRes getById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> {
                    log.warn("온라인 문의 조회 실패 - 존재하지 않음: id={}", questionId);
                    return new IllegalArgumentException("해당하는 온라인 문의가 존재하지 않습니다.");
                });
        return QuestionRes.fromEntity(question);
    }

    // 수정
    public QuestionRes update(Long questionId, QuestionReq req) {
        Question updateQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> {
                    log.warn("온라인 문의 수정 실패 - 존재하지 않음: id={}", questionId);
                    return new IllegalArgumentException("해당하는 온라인 문의가 없습니다.");
                });

        updateQuestion.update(req.getTitle(), req.getContent());
        log.info("온라인 문의 수정 완료: id={}, title={}", questionId, req.getTitle());
        return QuestionRes.fromEntity(updateQuestion);
    }

    // 삭제
    public void delete(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            log.warn("온라인 문의 삭제 실패 - 존재하지 않음: id={}", questionId);
            throw new IllegalArgumentException("해당 온라인 문의가 없습니다.");
        }
        questionRepository.deleteById(questionId);
        log.info("온라인 문의 삭제 완료: id={}", questionId);
    }
}
