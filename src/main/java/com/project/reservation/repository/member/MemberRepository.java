package com.project.reservation.repository.member;

import com.project.reservation.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNickName(String nickName);

    Optional<Member> findByEmail(String email);

    Page<Member> findByNameContaining(String name, Pageable pageable);

    Page<Member> findByBirthContaining(String birth,Pageable pageable);

    Optional<Member> findByEmailContaining(String email);

    Optional<Member> findByNickNameContaining(String nickName);

    Optional<Member> findByPhoneNumContaining(String phoneNum);

    Optional<Member> findByNameAndPhoneNum(String name, String phone);
}
