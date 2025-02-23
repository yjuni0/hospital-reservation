package com.project.reservation.repository.onlineConsult;

import com.project.reservation.entity.onlineConsult.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
