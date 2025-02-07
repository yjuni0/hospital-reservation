package com.project.reservation.service;

import com.project.reservation.Dto.request.NoticeFileReq;
import com.project.reservation.Dto.request.NoticeReq;
import com.project.reservation.Dto.response.NoticeFileRes;
import com.project.reservation.Dto.response.NoticeListRes;
import com.project.reservation.entity.Notice;
import com.project.reservation.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Page<NoticeListRes> getAll(Pageable pageable) {
        Page<Notice> notices = noticeRepository.findAll(pageable);
        List<NoticeListRes> list = notices.getContent().stream().map(NoticeListRes::fromEntity).toList();
        return new PageImpl<>(list, notices.getPageable(), notices.getTotalElements());
    }

//    //  공지사항 전체 조회
//    public Page<NoticeListRes> getAll(Pageable pageable){
//        Page<Notice> notices = noticeRepository.findAll(pageable);
//        List<NoticeListRes> list = notices.getContent().stream().map(NoticeListRes::fromEntity).toList();
//
//        return new PageImpl<>(list, pageable, notices.getTotalElements());
//    }
//    // 해당 id 의 공지사항 조회
//    public NoticeListRes getId(Long noticeId) {
//        Notice notice = noticeRepository.findById(noticeId).orElse(null);
//        return NoticeListRes.fromEntity(notice);
//    }
//    // 공지사항 수정
//    public NoticeListRes update(Long noticeId, NoticeReq req) {
//        Notice updateNotice = noticeRepository.findById(noticeId).orElse(null);
//
//        updateNotice.update(req.getTitle(),req.getContent());
//        return NoticeListRes.fromEntity(updateNotice);
//
//
//    }

}
