package com.project.reservation.admin.controller;

import com.project.reservation.dto.response.member.ResMember;
import com.project.reservation.dto.response.member.ResMemberList;
import com.project.reservation.entity.member.DeletedMember;
import com.project.reservation.entity.member.NonMember;
import com.project.reservation.service.member.MemberService;
import com.project.reservation.service.nonMember.NonMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminMemberController {
    private final MemberService memberService;
    private final NonMemberService nonMemberService;

    @GetMapping("/nonMember")
    public ResponseEntity<Page<NonMember>> getAllNonMembers(@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NonMember> nonMemberPage = nonMemberService.getAllNonMembers(pageable);
        return ResponseEntity.ok(nonMemberPage);
    }

    @GetMapping("/member")
    public ResponseEntity<Page<ResMemberList>> getAllMembers(@PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ResMemberList> listMember = memberService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listMember);
    }
    @GetMapping("/member/{memberId}")
    public ResponseEntity<ResMember> getMember(@PathVariable("memberId") Long memberId) {
        ResMember resMember = memberService.getMember(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(resMember);
    }
    // !! 관리자용 !!
    @GetMapping("/deletedMember")
    public ResponseEntity<Page<DeletedMember>> deletedMemberList(
            @PageableDefault(size = 8, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<DeletedMember> deletedMembers = memberService.getAllDeletedMember(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(deletedMembers);
    }

    @DeleteMapping("/member/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable("memberId") Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
