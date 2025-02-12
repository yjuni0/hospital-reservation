package com.project.reservation.service;

import com.project.reservation.Dto.request.answer.ReqAnswer;
import com.project.reservation.Dto.response.answer.ResAnswer;
import com.project.reservation.Dto.response.answer.ResAnswerList;
import com.project.reservation.entity.Answer;
import com.project.reservation.entity.Member;
import com.project.reservation.entity.Question;
import com.project.reservation.repository.AnswerRepository;
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
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    // 작성
    public ResAnswer write(Long questionId, Member admin, ReqAnswer reqAnswer){
        Question question = questionRepository.findById(questionId).orElseThrow(()->new IllegalArgumentException("해당 문의가 존재하지 않습니다."));

        Answer answer = ReqAnswer.ofEntity(reqAnswer);
        answer.setMappingAdmin(admin);
        answer.setMappingQuestion(question);
        answerRepository.save(answer);

        return ResAnswer.fromEntity(answer);
    }

    // 조회
    public Page<ResAnswerList> getAllAnswer(Pageable pageable){
        Page<Answer> answers = answerRepository.findAll(pageable);
        List<ResAnswerList> answerList = answers.stream().map(ResAnswerList::fromEntity).toList();
        return new PageImpl<>(answerList, pageable, answers.getTotalElements());
    }

    // 상세 조회
    public ResAnswer getById(Long answerId){
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변이 존재하지 않습니다."));
        return ResAnswer.fromEntity(answer);
    }

    // 수정
    public ResAnswer update(Long answerId, ReqAnswer reqAnswer){
        Answer updateAnswer = answerRepository.findById(answerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변이 존재하지 않습니다."));
        updateAnswer.update(reqAnswer.getContent());
        return ResAnswer.fromEntity(updateAnswer);
    }
    // 삭제
    public void delete(Long answerId){
        if (!answerRepository.existsById(answerId)) {
            throw new IllegalArgumentException("해당 답변이 존재하지 않습니다.");
        }
        answerRepository.deleteById(answerId);
    }
}
