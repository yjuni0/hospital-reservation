package com.project.reservation.service.search;

import com.project.reservation.common.SearchDto;
import com.project.reservation.common.exception.MemberException;
import com.project.reservation.dto.response.member.ResMember;
import com.project.reservation.dto.response.member.ResMemberList;
import com.project.reservation.entity.member.Member;
import com.project.reservation.repository.member.MemberRepository;
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
        if (isEmptySearchDto(searchDto, pageable)) {
            throw new MemberException("검색할 정보가 없습니다", HttpStatus.BAD_REQUEST);
        }

        // 유니크 검색 (단일 객체 반환)
        if (searchDto.getEmail() != null && !searchDto.getEmail().isEmpty()) {
            return searchByEmail(searchDto.getEmail(), pageable);
        } else if (searchDto.getNickName()!=null&&!searchDto.getNickName().isEmpty()) {
            return searchByNickName(searchDto.getNickName(), pageable);
        }

        // 리스트 검색 (페이징 처리)
        if (searchDto.getName()!=null&&!searchDto.getName().isEmpty()) {
            return searchByName(searchDto.getName(), pageable);
        } else if (searchDto.getBirth()!=null&&!searchDto.getBirth().isEmpty()) {
            return searchByBirth(searchDto.getBirth(), pageable);
        } else if (searchDto.getPhoneNum()!=null&&!searchDto.getPhoneNum().isEmpty()) {
            return searchByPhoneNum(searchDto.getPhoneNum(), pageable);
        }

        throw new MemberException("검색할 정보가 없습니다", HttpStatus.BAD_REQUEST);
    }

    // 검색 조건이 비어있는지 확인
    private boolean isEmptySearchDto(SearchDto searchDto, Pageable pageable) {
        return (searchDto.getEmail() == null || searchDto.getEmail().isEmpty()) &&
                (searchDto.getNickName() == null || searchDto.getNickName().isEmpty()) &&
                (searchDto.getPhoneNum() == null || searchDto.getPhoneNum().isEmpty()) &&
                (searchDto.getName() == null || searchDto.getName().isEmpty()) &&
                (searchDto.getBirth() == null || searchDto.getBirth().isEmpty());
    }

    // 이메일로 검색 (단일 객체 반환)
    private Page<ResMember> searchByEmail(String email, Pageable pageable) {
        Page<Member> result = memberRepository.findByEmailContaining(email, pageable);
        List<ResMember> listMember = result.getContent().stream().map(ResMember::fromEntity).collect(Collectors.toList());
        log.info("검색한 이메일에 해당하는 멤버 {}", result);
        return new PageImpl<>(listMember, pageable, result.getTotalElements());
    }

    // 닉네임으로 검색 (단일 객체 반환)
    private Page<ResMember>  searchByNickName(String nickName, Pageable pageable) {
        Page<Member>  result = memberRepository.findByNickNameContaining(nickName, pageable);
        List<ResMember> listMember = result.getContent().stream().map(ResMember::fromEntity).collect(Collectors.toList());
        log.info("검색한 닉네임에 해당하는 멤버 {}", result);
        return new PageImpl<>(listMember, pageable, result.getTotalElements());
    }

    // 전화번호로 검색 (단일 객체 반환)
    private Page<ResMember>  searchByPhoneNum(String phoneNum, Pageable pageable) {
        Page<Member>  result = memberRepository.findByPhoneNumContaining(phoneNum, pageable);
        List<ResMember> listMember = result.getContent().stream().map(ResMember::fromEntity).collect(Collectors.toList());
        log.info("검색한 휴대폰 번호에 해당하는 멤버 {}", result);
        return new PageImpl<>(listMember, pageable, result.getTotalElements());
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
