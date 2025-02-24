package com.project.reservation.repository.nonMember;

import com.project.reservation.entity.member.NonMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NonMemberRepository extends JpaRepository<NonMember, Long> {
    Optional<NonMember> findByName(String name);
}
