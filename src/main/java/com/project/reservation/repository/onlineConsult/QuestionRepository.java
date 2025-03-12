package com.project.reservation.repository.onlineConsult;

import com.project.reservation.entity.notice.Notice;
import com.project.reservation.entity.onlineConsult.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findByTitleContaining(String title, Pageable pageable);
    Page<Question> findByMember_NickNameContaining(String memberNickName, Pageable pageable);
    Page<Question> findByCreatedDateContaining(LocalDateTime searchData, Pageable pageable);


}
