package com.project.reservation.controller.member;

import com.project.reservation.dto.request.member.ReqMemberLogin;
import com.project.reservation.dto.request.member.ReqMemberRegister;
import com.project.reservation.dto.response.member.ResMember;
import com.project.reservation.dto.response.member.ResMemberToken;
import com.project.reservation.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<ResMember> register(@RequestBody ReqMemberRegister reqMemberRegister) {
        ResMember resMember = memberService.register(reqMemberRegister);
        return ResponseEntity.ok(resMember);
    }

    @PostMapping("/login")
    public ResponseEntity<ResMemberToken> login(@RequestBody ReqMemberLogin reqMemberLogin){
        ResMemberToken resMember = memberService.login(reqMemberLogin);
        return  ResponseEntity.ok(resMember);
    }
}