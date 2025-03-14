package com.project.reservation.service.notice;

import com.project.reservation.common.exception.MemberException;
import com.project.reservation.dto.request.notice.ReqNotice;
import com.project.reservation.dto.request.notice.ReqNoticeUpdate;
import com.project.reservation.dto.response.notice.ResNoticeDetail;
import com.project.reservation.common.SearchDto;
import com.project.reservation.dto.response.notice.ResNoticeList;
import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.member.Role;
import com.project.reservation.entity.notice.Notice;
import com.project.reservation.repository.member.MemberRepository;
import com.project.reservation.repository.notice.NoticeRepository;
import com.project.reservation.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    private final MemberRepository memberRepository;
    private final RedisService redisService;

    public ResNoticeDetail create(Member admin, ReqNotice req) {
        // Member 엔티티를 환영속 상태로 변
        Member persistentAdmin = memberRepository.findById(admin.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관리자입니다."));

        Notice notice = ReqNotice.ofEntity(req,persistentAdmin);
        log.info("해당 공지사항 생성 완료: {}", notice);

        Notice savedNotice = noticeRepository.save(notice);
//        redisService.deleteCacheNotices();

        return ResNoticeDetail.fromEntity(savedNotice);
    }

    // 공지사항 전체 조회
    public Page<ResNoticeList> getAll(Pageable pageable) {
        log.info("요청 한 공지사항 전체 리스트"+noticeRepository.findAll(pageable).map(ResNoticeList::fromEntity));
        return noticeRepository.findAll(pageable).map(ResNoticeList::fromEntity);
    }

    // 해당 id 의 공지사항 조회
    public ResNoticeDetail getId(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(()->new IllegalArgumentException("해당 공지사항이 없음."));
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



}
