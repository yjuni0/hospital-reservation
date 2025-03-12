package com.project.reservation.service.nonMember;

import com.project.reservation.dto.request.nonMember.ReqNonMember;

import com.project.reservation.dto.response.nonMember.ResNonMember;
import com.project.reservation.entity.member.NonMember;
import com.project.reservation.repository.nonMember.NonMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NonMemberService {
    private final NonMemberRepository nonMemberRepository;

    public NonMember saveNonMember(ReqNonMember reqNonMember) {
        return nonMemberRepository.save(ReqNonMember.ofEntity(reqNonMember));
    }

    public Page<NonMember> getAllNonMembers(Pageable pageable) {
        if(nonMemberRepository.findAll(pageable).getContent().isEmpty()) {
            return null;
        }
        return nonMemberRepository.findAll(pageable);
    }
}