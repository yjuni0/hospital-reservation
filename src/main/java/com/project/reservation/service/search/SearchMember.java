package com.project.reservation.service.search;

import com.project.reservation.common.SearchDto;
import com.project.reservation.common.exception.MemberException;
import com.project.reservation.dto.response.member.ResMember;
import com.project.reservation.dto.response.member.ResMemberList;
import com.project.reservation.entity.member.Member;
import com.project.reservation.repository.member.MemberRepository;
import com.project.reservation.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchMember {

    private final MemberRepository memberRepository;

    // 멤버 검색
    public Object searchMember(SearchDto searchDto, Pageable pageable) {
        // 유효한 검색 조건이 없을 경우 예외 처리
        if (isEmptySearchDto(searchDto)) {
            throw new MemberException("검색할 정보가 없습니다", HttpStatus.BAD_REQUEST);
        }

        // 유니크 검색 (단일 객체 반환)
        if (!searchDto.getEmail().isEmpty()) {
            return searchByEmail(searchDto.getEmail());
        } else if (!searchDto.getNickName().isEmpty()) {
            return searchByNickName(searchDto.getNickName());
        } else if (!searchDto.getPhoneNum().isEmpty()) {
            return searchByPhoneNum(searchDto.getPhoneNum());
        }

        // 리스트 검색 (페이징 처리)
        if (!searchDto.getName().isEmpty()) {
            return searchByName(searchDto.getName(), pageable);
        } else if (!searchDto.getBirth().isEmpty()) {
            return searchByBirth(searchDto.getBirth(), pageable);
        }

        throw new MemberException("검색할 정보가 없습니다", HttpStatus.BAD_REQUEST);
    }

    // 검색 조건이 비어있는지 확인
    private boolean isEmptySearchDto(SearchDto searchDto) {
        return searchDto.getEmail().isEmpty() && searchDto.getNickName().isEmpty()
                && searchDto.getPhoneNum().isEmpty() && searchDto.getName().isEmpty()
                && searchDto.getBirth().isEmpty();
    }

    // 이메일로 검색 (단일 객체 반환)
    private ResMember searchByEmail(String email) {
        Member result = memberRepository.findByEmailContaining(email)
                .orElseThrow(() -> new MemberException("검색한 이메일 없음", HttpStatus.BAD_REQUEST));
        log.info("검색한 이메일에 해당하는 멤버 {}", result);
        return ResMember.fromEntity(result);
    }

    // 닉네임으로 검색 (단일 객체 반환)
    private ResMember searchByNickName(String nickName) {
        Member result = memberRepository.findByNickNameContaining(nickName)
                .orElseThrow(() -> new MemberException("검색한 닉네임 없음", HttpStatus.BAD_REQUEST));
        log.info("검색한 닉네임에 해당하는 멤버 {}", result);
        return ResMember.fromEntity(result);
    }

    // 전화번호로 검색 (단일 객체 반환)
    private ResMember searchByPhoneNum(String phoneNum) {
        Member result = memberRepository.findByPhoneNumContaining(phoneNum)
                .orElseThrow(() -> new MemberException("휴대폰 번호 없음", HttpStatus.BAD_REQUEST));
        log.info("검색한 휴대폰 번호에 해당하는 멤버 {}", result);
        return ResMember.fromEntity(result);
    }

    // 이름으로 검색 (리스트 반환, 페이징 처리)
    private Page<ResMemberList> searchByName(String name, Pageable pageable) {
        Page<Member> result = memberRepository.findByNameContaining(name, pageable);
        List<ResMemberList> list = result.getContent().stream()
                .map(ResMemberList::fromEntity)
                .collect(Collectors.toList());
        log.info("검색한 이름에 해당하는 멤버 리스트 {}", list);
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

    // 생일로 검색 (리스트 반환, 페이징 처리)
    private Page<ResMemberList> searchByBirth(String birth, Pageable pageable) {
        Page<Member> result = memberRepository.findByBirthContaining(birth, pageable);
        List<ResMemberList> list = result.getContent().stream()
                .map(ResMemberList::fromEntity)
                .collect(Collectors.toList());
        log.info("검색한 생일에 해당하는 멤버 리스트 {}", list);
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }
}
