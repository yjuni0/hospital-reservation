package com.project.reservation.repository.member;

import com.project.reservation.entity.member.DeletedMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface DeletedMemberRepository extends JpaRepository<DeletedMember, Long> {
    void deleteByDeletedAtBefore(LocalDateTime sixMonthsAgo);
}
