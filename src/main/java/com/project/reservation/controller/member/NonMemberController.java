package com.project.reservation.controller.member;

import com.project.reservation.entity.member.NonMember;
import com.project.reservation.service.nonMember.NonMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nonMember")
@RequiredArgsConstructor
public class NonMemberController {
    private final NonMemberService nonMemberService;

    @PostMapping
    public ResponseEntity<?> createNonMember(@RequestBody NonMember reqNonMember) {

        try {
            nonMemberService.saveNonMember(reqNonMember);
            return ResponseEntity.ok(reqNonMember);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<NonMember> getNonMember(@PathVariable String name) {
        NonMember nonMember = nonMemberService.getFromName(name);
        return ResponseEntity.ok(nonMember);
    }

}