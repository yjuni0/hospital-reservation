package com.project.reservation.service.nonMember;

import com.project.reservation.common.exception.MemberException;
import com.project.reservation.entity.member.NonMember;
import com.project.reservation.repository.nonMember.NonMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NonMemberService {
    private final NonMemberRepository nonMemberRepository;

    public void saveNonMember(NonMember reqNonMember) {
        nonMemberRepository.save(reqNonMember);
    }

    public Page<NonMember> getAllNonMembers(Pageable pageable) {
        return nonMemberRepository.findAll(pageable);
    }

    public NonMember getFromName(String name) {
        return nonMemberRepository.findByName(name).orElseThrow(()-> new MemberException("없음 ", HttpStatus.BAD_REQUEST));
    }
}