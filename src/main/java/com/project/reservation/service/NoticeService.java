package com.project.reservation.service;

import com.project.reservation.Dto.request.notice.ReqNotice;
import com.project.reservation.Dto.request.notice.ReqNoticeUpdate;
import com.project.reservation.Dto.response.notice.ResNoticeDetail;
import com.project.reservation.common.SearchDto;
import com.project.reservation.Dto.response.notice.ResNoticeList;
import com.project.reservation.entity.Member;
import com.project.reservation.entity.Notice;
import com.project.reservation.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 공지사항 작성
    public ResNoticeDetail create(Member admin, ReqNotice req) {
        if (admin == null) {
            throw new IllegalArgumentException("관리자 정보가 유효하지 않습니다.");
        }
        //  Notice 엔티티 생성 시, 관리자 정보 포함
        Notice notice = ReqNotice.ofEntity(req);
        notice.setMappingAdmin(admin);
        log.info("해당 공지사항 생성 완료"+notice);
        //  공지사항 저장
        Notice saveNotice = noticeRepository.save(notice);

        // 응답 DTO 변환 후 반환
        return ResNoticeDetail.fromEntity(saveNotice);
    }

    // 공지사항 전체 조회
    public Page<ResNoticeList> getAll(Pageable pageable) {
        log.info("요청 한 공지사항 전체 리스트"+noticeRepository.findAll(pageable).map(ResNoticeList::fromEntity));
        return noticeRepository.findAll(pageable).map(ResNoticeList::fromEntity);
    }

    // 해당 id 의 공지사항 조회
    public ResNoticeDetail getId(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElse(null);
        log.info("요청한 아이디에 일치하는 공지사항 "+notice);
        return ResNoticeDetail.fromEntity(notice);
    }

    // 공지사항 수정 (예외 처리 + save 호출)
    public ResNoticeDetail update(Long noticeId, ReqNoticeUpdate req) {
        Notice updateNotice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("공지사항을 찾을 수 없습니다. ID: " + noticeId));
        log.info("수정 요청한 공지사항{}", updateNotice);
        updateNotice.update(req.getTitle(), req.getContent());
        log.info("수정 완료{}", updateNotice);
        // 명시적으로 save 호출
        Notice savedNotice = noticeRepository.save(updateNotice);
        return ResNoticeDetail.fromEntity(savedNotice);
    }

    // 삭제
    public void delete(Long noticeId) {
        // 해당 아이디의 공지사항이 존재 하는 지 검증
        if (!noticeRepository.existsById(noticeId)) {
            throw new IllegalArgumentException("삭제할 공지사항이 존재하지 않습니다. ID: " + noticeId);
        }
        log.info("삭제요청 처리 완료");
        noticeRepository.deleteById(noticeId);
    }

    // 공지사항 검색
    public Page<ResNoticeList> search(SearchDto searchDto, Pageable pageable) {
        Page<Notice> result = null;
        if (!searchDto.getTitle().isEmpty()) {
            result = noticeRepository.findByTitleContaining(searchDto.getTitle(), pageable);
            log.info("검색 요청한 타이틀에 포함된 검색어{}", result);
        }else if (!searchDto.getContent().isEmpty()) {
            result = noticeRepository.findByContentContaining(searchDto.getContent(), pageable);
            log.info("검색 요청한 컨텐트에 포함된 검색어{}", result);
        }
        List<ResNoticeList> list = result.getContent().stream().map(ResNoticeList::fromEntity).collect(Collectors.toList());
        log.info("검색어에 해당하는 리스트{}", list);
        return new PageImpl<>(list, pageable, result.getTotalElements());
    }

}
