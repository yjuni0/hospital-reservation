package com.project.reservation.repository;

import com.project.reservation.entity.NoticeFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeFileRepository extends JpaRepository<NoticeFile, Long> {
}
