//package com.project.reservation.service.search;
//
//import com.project.reservation.common.SearchDto;
//import com.project.reservation.common.exception.MemberException;
//import com.project.reservation.dto.response.member.ResMember;
//import com.project.reservation.dto.response.member.ResMemberList;
//import com.project.reservation.dto.response.notice.ResNoticeList;
//import com.project.reservation.dto.response.question.ResQuestionList;
//import com.project.reservation.dto.response.reservation.ResReservationList;
//import com.project.reservation.dto.response.review.ResReviewList;
//import com.project.reservation.entity.customerReviews.Review;
//import com.project.reservation.entity.member.Member;
//import com.project.reservation.entity.notice.Notice;
//import com.project.reservation.entity.onlineConsult.Question;
//import com.project.reservation.entity.onlineReserve.Reservation;
//import com.project.reservation.repository.customerReviews.ReviewRepository;
//import com.project.reservation.repository.member.MemberRepository;
//import com.project.reservation.repository.member.PetRepository;
//import com.project.reservation.repository.notice.NoticeRepository;
//import com.project.reservation.repository.onlineConsult.QuestionRepository;
//import com.project.reservation.repository.onlineReserve.ReservationRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cglib.core.Local;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.data.domain.Pageable;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class SearchService {
//    private final NoticeRepository noticeRepository;
//    private final MemberRepository memberRepository;
//    private final QuestionRepository questionRepository;
//    private final ReservationRepository reservationRepository;
//    private final ReviewRepository reviewRepository;
//    private final PetRepository petRepository;
//
//    // 공지사항 검색
//    public Page<ResNoticeList> notice(SearchDto searchDto, Pageable pageable) {
//        Page<Notice> result = null;
//        if (!searchDto.getTitle().isEmpty()) {
//            result = noticeRepository.findByTitleContaining(searchDto.getTitle(), pageable);
//            log.info("검색 요청한 타이틀에 포함된 검색어{}", result);
//        } else if (!searchDto.getCreatedDate().isEmpty()) {
//            LocalDateTime searchData = LocalDateTime.parse(searchDto.getCreatedDate());
//            result = noticeRepository.findByCreatedDateContaining(searchData,pageable);
//            log.info("검색 요청한 날짜에 포함된 검색어 {}",result);
//        }
//        List<ResNoticeList> list = result.getContent().stream().map(ResNoticeList::fromEntity).collect(Collectors.toList());
//        log.info("검색어에 해당하는 리스트{}", list);
//        return new PageImpl<>(list, pageable, result.getTotalElements());
//    }
//
//    // 멤버 검색
//    public ResMember uniqueMember(SearchDto searchDto) {
//        Member result = null;
//        if (!searchDto.getEmail().isEmpty()) {
//            result=memberRepository.findByEmailContaining(searchDto.getEmail()).orElseThrow(()->new MemberException("검색한 이메일 없음", HttpStatus.BAD_REQUEST));
//        }else if (!searchDto.getNickName().isEmpty()) {
//            result=memberRepository.findByNickNameContaining(searchDto.getNickName()).orElseThrow(()->new MemberException("검색한 닉네임 없음",HttpStatus.BAD_REQUEST));
//        }else if(!searchDto.getPhoneNum().isEmpty()){
//            result=memberRepository.findByPhoneNumContaining(searchDto.getPhoneNum()).orElseThrow(()->new MemberException("휴대폰 번호 없음",HttpStatus.BAD_REQUEST));
//        } else {
//            throw new MemberException("검색할 정보가 없습니다", HttpStatus.BAD_REQUEST);
//        }
//        log.info("검색어에 해당하는 멤버 {} ",result);
//        return ResMember.fromEntity(result);
//    }
//    // 멤버 리스트 검색
//    public Page<ResMemberList> listMember(SearchDto searchDto, Pageable pageable) {
//        Page<Member> result = null;
//        if(!searchDto.getName().isEmpty()){
//            result=memberRepository.findByNameContaining(searchDto.getName(),pageable);
//        } else if (!searchDto.getBirth().isEmpty()) {
//            result=memberRepository.findByBirthContaining(searchDto.getBirth(),pageable);
//        }else {
//            throw new IllegalArgumentException("검색어에 일치하는 정보가 없습니다.{}");
//        }
//        List<ResMemberList> list = result.getContent().stream().map(ResMemberList::fromEntity).collect(Collectors.toList());
//        return new PageImpl<>(list, pageable, result.getTotalElements());
//    }
//
//    // 문의 검색
//    public Page<ResQuestionList> listQuestion(SearchDto searchDto, Pageable pageable) {
//        Page<Question> result = null;
//        if(!searchDto.getTitle().isEmpty()){
//            result=questionRepository.findByTitleContaining(searchDto.getTitle(),pageable);
//        }else if(!searchDto.getWriter().isEmpty()) {
//            result = questionRepository.findByMember_NickNameContaining(searchDto.getWriter(),pageable);
//        }else if (!searchDto.getCreatedDate().isEmpty()) {
//        LocalDateTime searchData = LocalDateTime.parse(searchDto.getCreatedDate());
//            result=questionRepository.findByCreatedDateContaining(searchData,pageable);
//        }
//        List<ResQuestionList> list = result.getContent().stream().map(ResQuestionList::fromEntity).collect(Collectors.toList());
//        return new PageImpl<>(list, pageable, result.getTotalElements());
//    }
//
//    // 리뷰 검색
//    public Page<ResReviewList> listReview(SearchDto searchDto, Pageable pageable) {
//        Page<Review> result = null;
//        if (!searchDto.getTitle().isEmpty()) {
//            result = reviewRepository.findAllTitleContaining(searchDto.getTitle(),pageable);
//        }else if(!searchDto.getWriter().isEmpty()) {
//            result = reviewRepository.findAllNicknameContaining(searchDto.getWriter(),pageable);
//        }else if(!searchDto.getCreatedDate().isEmpty()) {
//            LocalDateTime searchData = LocalDateTime.parse(searchDto.getCreatedDate());
//            result = reviewRepository.findByCreatedDateContaining(searchData,pageable);
//        }else {
//            throw new IllegalArgumentException("검색어에 일치하는 정보가 없습니다.");
//        }
//        List<ResReviewList> list = result.getContent().stream().map(ResReviewList::fromEntity).collect(Collectors.toList());
//        return new PageImpl<>(list, pageable, result.getTotalElements());
//    }
//
//    // 예약검색
//    public Page<ResReservationList> listReservation(SearchDto searchDto, Pageable pageable) {
//        Page<Reservation> result = null;
//        if (!searchDto.getPetName().isEmpty()) {
//            result = reservationRepository.findByDepartmentName(searchDto.getPetName(),pageable);
//        }else if (!searchDto.getNickName().isEmpty()){
//            result=reservationRepository.findByMember_NickNameContaining(searchDto.getNickName(),pageable);
//        }else if (!searchDto.getCreatedDate().isEmpty()) {
//            LocalDateTime searchData = LocalDateTime.parse(searchDto.getCreatedDate());
//            result = reservationRepository.findByCreatedDateContaining(searchData,pageable);
//        }else {
//            throw new IllegalArgumentException("일치하는 검색어가 없습니다.");
//        }
//        List<ResReservationList> list = result.getContent().stream().map(ResReservationList::fromEntity).collect(Collectors.toList());
//        return new PageImpl<>(list, pageable, result.getTotalElements());
//    }
//
//
//
//
//}