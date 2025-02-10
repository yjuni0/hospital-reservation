package com.project.reservation.service;

import com.project.reservation.Dto.request.NoticeReq;
import com.project.reservation.Dto.request.NoticeUpdateReq;
import com.project.reservation.Dto.request.SearchDto;
import com.project.reservation.Dto.response.NoticeDetailRes;
import com.project.reservation.Dto.response.NoticeListRes;
import com.project.reservation.entity.Member;
import com.project.reservation.entity.Notice;
import com.project.reservation.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 공지사항 작성
    public NoticeDetailRes create(Member admin, NoticeReq req) {
        if (admin == null) {
            throw new IllegalArgumentException("관리자 정보가 유효하지 않습니다.");
        }
        //  Notice 엔티티 생성 시, 관리자 정보 포함
        Notice notice = Notice.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .admin(admin) // 관리자 정보 설정
                .build();

        //  공지사항 저장
        Notice saveNotice = noticeRepository.save(notice);

        // 응답 DTO 변환 후 반환
        return NoticeDetailRes.fromEntity(saveNotice);
    }

    // 공지사항 전체 조회
    public Page<NoticeListRes> getAll(Pageable pageable) {
        return noticeRepository.findAll(pageable).map(NoticeListRes::fromEntity);
    }

    // 해당 id 의 공지사항 조회
    public NoticeDetailRes getId(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElse(null);
        return NoticeDetailRes.fromEntity(notice);
    }

    // 공지사항 수정 (예외 처리 + save 호출)
    public NoticeDetailRes update(Long noticeId, NoticeUpdateReq req) {
        Notice updateNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("공지사항을 찾을 수 없습니다. ID: " + noticeId));

        updateNotice.update(req.getTitle(), req.getContent());

        // 명시적으로 save 호출
        Notice savedNotice = noticeRepository.save(updateNotice);
        return NoticeDetailRes.fromEntity(savedNotice);
    }

    // 삭제
    public void delete(Long noticeId) {
        // 해당 아이디의 공지사항이 존재 하는 지 검증
        if (!noticeRepository.existsById(noticeId)) {
            throw new IllegalArgumentException("삭제할 공지사항이 존재하지 않습니다. ID: " + noticeId);
        }
        noticeRepository.deleteById(noticeId);
    }

    // 공지사항 검색
    public Page<NoticeListRes> search(SearchDto searchDto, Pageable pageable) {
        Page<Notice> result = null;
        if (!searchDto.getTitle().isEmpty()) {
            result = noticeRepository.findByTitleContaining(searchDto.getTitle(), pageable);
        }else if (!searchDto.getContent().isEmpty()) {
            result = noticeRepository.findByContentContaining(searchDto.getContent(), pageable);
        }
        List<NoticeListRes> list = result.getContent().stream().map(NoticeListRes::fromEntity).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

}
