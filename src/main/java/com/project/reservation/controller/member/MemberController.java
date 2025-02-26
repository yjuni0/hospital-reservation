package com.project.reservation.controller.member;

import com.project.reservation.common.exception.MemberException;
import com.project.reservation.dto.request.member.ReqMemberFindId;
import com.project.reservation.dto.request.member.ReqMemberLogin;
import com.project.reservation.dto.request.member.ReqMemberRegister;
import com.project.reservation.dto.request.member.ReqMemberFindPw;
import com.project.reservation.dto.response.member.ResMember;
import com.project.reservation.dto.response.member.ResMemberToken;
import com.project.reservation.service.member.MailService;
import com.project.reservation.service.member.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final MailService mailService;

    // 이메일 중복확인
    @GetMapping("/checkEmail")
    public ResponseEntity<?> checkIdDuplicate(@RequestParam(name = "email") String email) {
        memberService.checkIdDuplicate(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 닉네임 중복확인
    @GetMapping("/checkNickName")
    public ResponseEntity<?> checkNickNameDuplicate(@RequestParam(name = "nickName") String nickName) {
        memberService.isExistUserNickName(nickName);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 이메일 인증 확인
    @GetMapping("/checkEmailVerified")
    public ResponseEntity<?> checkEmailVerified(@RequestParam(name = "emailVerified") String emailVerified) {
        memberService.isEmailVerified(emailVerified);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<ResMember> register(@RequestBody ReqMemberRegister reqMemberRegister) {
        ResMember registeredMember = memberService.register(reqMemberRegister);
        return ResponseEntity.ok(registeredMember);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ResMemberToken> login(@RequestBody ReqMemberLogin reqMemberLogin, HttpServletResponse response){
        // 서비스레이어에서 요청 DTO 로 로그인 메소드를 한 결과를 ResMemberToken 로 받음. 성공시 생성된 토큰 정보
        ResMemberToken resMemberToken = memberService.login(reqMemberLogin,response);
        return  ResponseEntity.status(HttpStatus.OK).header(resMemberToken.getToken()).body(resMemberToken);
//        return ResponseEntity.ok()
//                .header("Authorization", "Bearer " + resMemberToken.getToken())
//                .body(resMemberToken);
    }

    //====================================================================================================
    // 이메일 찾기
    @PostMapping("/findId")
    public ResponseEntity<String> findId(
            @RequestBody ReqMemberFindId reqMemberFindId){
        String memberEmail = memberService.findEmail(reqMemberFindId.email(), reqMemberFindId.phoneNum());

        return ResponseEntity.ok("귀하의 이메일 입니다. : " + memberEmail);
    }

    @PostMapping("/findPw")
    public ResponseEntity<?> findPassword(@RequestBody ReqMemberFindPw reqMemberFindPw) {

        if (memberService.isEmailExist(reqMemberFindPw.email())) {

            mailService.sendMail(reqMemberFindPw.email());
            return ResponseEntity.ok("비밀번호 재설정 인증 메일이 전송되었습니다.");
        } else {

            return ResponseEntity.badRequest().body("등록되지 않은 이메일입니다.");
        }
    }

    @PostMapping("/findPw/verify")
    public ResponseEntity<?> verifyCode(@RequestBody ReqMemberFindPw reqMemberFindPw) {

        if (mailService.verifyCode(reqMemberFindPw.email(), reqMemberFindPw.code())) {

            return ResponseEntity.ok("인증 코드가 확인되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("잘못된 인증 코드입니다.");
        }
    }

    @PostMapping("/findPw/resetPw")
    public ResponseEntity<?> resetPassword(@RequestBody ReqMemberFindPw reqMemberFindPw) {
        if (mailService.verifyCode(reqMemberFindPw.email(), reqMemberFindPw.code())) {
            try {
                memberService.checkPassword(reqMemberFindPw.newPassword(), reqMemberFindPw.newPasswordCheck());
                return ResponseEntity.ok("비밀번호가 성공적으로 재설정되었습니다.");
            } catch (MemberException e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.badRequest().body("인증 실패 또는 만료된 코드입니다.");
        }
    }


}