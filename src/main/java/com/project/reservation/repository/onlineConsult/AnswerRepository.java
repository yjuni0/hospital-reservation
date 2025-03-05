package com.project.reservation.repository.onlineConsult;

import com.project.reservation.entity.onlineConsult.Answer;
import com.project.reservation.entity.onlineConsult.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
    Optional<Answer> findByQuestionAndId(Question question, Long id);

    Optional<Answer> findByQuestion(Question question);
}
