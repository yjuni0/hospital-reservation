package com.project.reservation.service.member;

import com.project.reservation.common.exception.MemberException;


import com.project.reservation.dto.request.member.ReqMemberLogin;
import com.project.reservation.dto.request.member.ReqMemberRegister;

import com.project.reservation.dto.response.member.ResMember;


import com.project.reservation.dto.response.member.ResMemberToken;
import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.member.Role;
import com.project.reservation.repository.member.MemberRepository;
import com.project.reservation.security.google.OAuth2UserPrincipal;
import com.project.reservation.security.jwt.CustomUserDetailsService;
import com.project.reservation.security.jwt.JwtTokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    // 인증상태를 관리하기 위한 동시성 맵. 키값은 수신자, 값은 true/false
    private final Map<String, Boolean> verificationStatus = new ConcurrentHashMap<>();

    // 이메일 중복체크 - private 메소드 실행
    public void checkIdDuplicate(String email) {
        isExistUserEmail(email);
    }

    public boolean isEmailExist(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public String findEmail(String name, String phone) {
        Member member = memberRepository.findByNameAndPhoneNum(name, phone)
                .orElseThrow(() -> new MemberException("회원정보가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        return member.getEmail();
    }

    public void setEmailVerified(String receiver) {
        verificationStatus.put(receiver, true);
    }

    // 회원가입
    public ResMember register(ReqMemberRegister reqMemberRegister) {
        isExistUserEmail(reqMemberRegister.getEmail());
        log.info("1번 통과");
        isExistUserNickName(reqMemberRegister.getNickName());
        log.info("2번 통과");

        // 이메일 인증 여부 확인
        if (isEmailVerified(reqMemberRegister.getEmail())) {
            // 비밀번호는 HttpStatus 반환 안함?
            checkPassword(reqMemberRegister.getPassword(), reqMemberRegister.getPasswordCheck());
            log.info("3번 통과");

            // 패스워드 암호화
            String encodedPassword = passwordEncoder.encode(reqMemberRegister.getPassword());
            log.info("4번 통과");

            // reqMemberRegister 에 암호화된 패스워드로 set
            reqMemberRegister.setPassword(encodedPassword);
            log.info("5번 통과");
        }
        // DTO 를 엔티티 객체로 변환, 변환된 Member 엔티티를 데이터베이스에 저장, 변수에 대입
        Member registerMember = memberRepository.save(
                ReqMemberRegister.ofEntity(reqMemberRegister));
        log.info("6번 통과");
        // 클라이언트에게 저장된 registerMember 엔티티를  다시 응답 DTO 로 변환해서 반환
        return ResMember.fromEntity(registerMember);
    }

    // 로그인
    public ResMemberToken login(ReqMemberLogin loginDto, HttpServletResponse response) {
        try {
            // 사용자 엔티티 조회
            Member member = memberRepository.findByEmail(loginDto.getEmail())
                    .orElseThrow(() -> new MemberException( "사용자를 찾을 수 없습니다.",HttpStatus.BAD_REQUEST));

            // 비밀번호 검증
            if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
                throw new MemberException( "비밀번호가 일치하지 않습니다.",HttpStatus.BAD_REQUEST);
            }

            String accessToken = jwtTokenUtil.generateToken(member);

            response.setHeader("Authorization", "Bearer " + accessToken);
            return ResMemberToken.fromEntity(member, accessToken);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    // 마이페이지 - 비밀번호 확인
    public ResMember myPageCheck(Member member, String typedPassword) {
        // 현재 로그인한 멤버 (Member member) 의 정보를 조회, ResMember 로 사용하기 위해 UserDetails 타입을 Member 타입으로 캐스팅
        Member currentMember = (Member) customUserDetailsService.loadUserByUsername(member.getEmail());
        // checkStoredPasswordInDB 메소드로 사용자가 입력하는 비밀번호가 DB 의 사용자의 비밀번호와 일치하는지 확인
        checkStoredPasswordInDB(typedPassword, currentMember.getPassword());
        log.info("memberservice - myPageCheck 사용됨");
        // 성공하면 조회된 사용자 정보를 DTO 로 변환하여 반환
        return ResMember.fromEntity(currentMember);
    }


    // private 메소드들 =========================================================================
    // 이메일 중복체크 - 리파지토리 조회 후 중복시 예외 처리
    private void isExistUserEmail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new MemberException("이미 사용 중인 이메일입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    // 이메일 인증체크
    public boolean isEmailVerified(String receiver) {
        Boolean isVerified = verificationStatus.get(receiver);

        if (isVerified == null || !isVerified) {
            throw new MemberException("이메일 인증이 필요합니다.", HttpStatus.BAD_REQUEST);
        }
        return true;
    }

    // 닉네임 중복체크 - 리파지토리 조회 후 중복시 예외 처리
    public void isExistUserNickName(String nickName) {
        if (memberRepository.findByNickName(nickName).isPresent()) {
            throw new MemberException("이미 사용 중인 닉네임입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    // 비밀번호와 비밀번호 확인 같은지 체크
    public void checkPassword(String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            throw new MemberException("비밀번호 확인 불일치", HttpStatus.BAD_REQUEST);
        }
    }

    // 사용자가 입력한 비밀번호가 DB 에 저장된 암호화된 비밀번호와 같은지 체크
    private void checkStoredPasswordInDB(String typedPassword, String encodedPassword) {
        if (!passwordEncoder.matches(typedPassword, encodedPassword)) {
            throw new MemberException("패스워드 불일치", HttpStatus.BAD_REQUEST);
        }
    }

    // 사용자 인증
    private void authenticate(String email, String password) {
        try {
            // 주입받은 authenticationManager 객체로 authenticate 메소드를 호출해 인증 시도
            // 이메일과 비밀번호를 받은 UsernamePasswordAuthenticationToken 객체를 생성
            // AuthenticationManager 에 전달 ?
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            // isEnabled
        } catch (DisabledException e) {
            throw new MemberException("계정이 비활성화 되었습니다.", HttpStatus.BAD_REQUEST);
            // 비밀번호가 일치하지 않음
        } catch (BadCredentialsException e) {
            throw new MemberException("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    public void saveOrUpdateMember(OAuth2UserPrincipal principal) {
        // 구글 사용자 정보로 Member 엔티티 생성
        Member member = Member.builder()
                .email(principal.getName())
                .password(principal.getPassword())
                .name(principal.getName())
                .nickName(principal.getName())
                .roles(Role.USER)
                .build();

        // 이미 존재하는 사용자라면 업데이트, 아니라면 새로 저장
        Optional<Member> existingMember = memberRepository.findByEmail(member.getEmail());
        if (!existingMember.isPresent()) {
            memberRepository.save(member);

        }

    }
}