package com.project.reservation.admin.controller;

import com.project.reservation.dto.response.member.ResMemberList;
import com.project.reservation.service.member.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/member")
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/list")
    public ResponseEntity<Page<ResMemberList>>listMember(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        Page<ResMemberList> listMember = memberService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(listMember);
    }

}
