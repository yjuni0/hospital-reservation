package com.project.reservation.admin.controller;

import com.project.reservation.entity.member.NonMember;
import com.project.reservation.service.nonMember.NonMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/member")
public class AdminMemberController {
    private NonMemberService nonMemberService;

    @GetMapping("/nonMember")
    public ResponseEntity<Page<NonMember>> getAllNonMembers(@PageableDefault Pageable pageable) {
        Page<NonMember> nonMemberPage = nonMemberService.getAllNonMembers(pageable);
        return ResponseEntity.ok(nonMemberPage);
    }
}
