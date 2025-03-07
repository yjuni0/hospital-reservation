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

    Page<Member> findByEmailContaining(String email,Pageable pageable);

    Page<Member> findByNickNameContaining(String nickName,Pageable pageable);

    Page<Member> findByPhoneNumContaining(String phoneNum,Pageable pageable);

    Optional<Member> findByNameAndPhoneNum(String name, String phone);
}
