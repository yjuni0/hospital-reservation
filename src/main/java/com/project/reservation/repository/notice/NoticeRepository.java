package com.project.reservation.repository.notice;

import com.project.reservation.entity.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Page<Notice> findByTitleContaining(String title, Pageable pageable);

    Page<Notice> findByContentContaining(String content, Pageable pageable);

    List<Notice> findTop4ByOrderByCreatedDateDesc();

    Page<Notice> findByCreatedDateContaining(LocalDateTime createdDate, Pageable pageable);
}
