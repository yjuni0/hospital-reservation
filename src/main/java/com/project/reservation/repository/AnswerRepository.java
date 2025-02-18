package com.project.reservation.repository;

import com.project.reservation.entity.Answer;
import com.project.reservation.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
    Optional<Answer> findByQuestionAndId(Question question, Long id);
}
