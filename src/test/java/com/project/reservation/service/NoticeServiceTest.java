package com.project.reservation.service;



import com.project.reservation.dto.request.notice.ReqNotice;
import com.project.reservation.dto.request.notice.ReqNoticeUpdate;
import com.project.reservation.dto.response.notice.ResNoticeDetail;
import com.project.reservation.common.SearchDto;
import com.project.reservation.dto.response.notice.ResNoticeList;
import com.project.reservation.entity.Member;
import com.project.reservation.entity.Notice;
import com.project.reservation.entity.Role;
import com.project.reservation.repository.NoticeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoticeServiceTest {


    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private NoticeService noticeService;

    @Test
    void 공지사항_작성() {
        // 1. 테스트용 Member 객체 생성
        Member member = Member.builder()
                .id(1L)
                .name("admin")
                .password("admin1")
                .phoneNum("01011112222")
                .addr("test")
                .email("admin1@gmail.com")
                .roles(Role.ADMIN)
                .birth("960601")
                .nickName("admin")
                .build();

        // 3. 테스트용 ReqNotice 객체 생성
        ReqNotice reqNotice = ReqNotice.builder()
                .title("title1")
                .content("content1")
                .build();

        // 4. Mock 설정 (noticeRepository.save() 호출 시 저장된 Notice 반환)
        Notice savedNotice = Notice.builder()
                .id(1L)
                .title(reqNotice.getTitle())
                .content(reqNotice.getContent())
                .admin(member)
                .build();

        when(noticeRepository.save(Mockito.any(Notice.class))).thenReturn(savedNotice);

        // 5. 실제 서비스 메서드 호출
        ResNoticeDetail saveNotice = noticeService.create(member, reqNotice);

        // 6. 검증
        Assertions.assertNotNull(saveNotice);
        Assertions.assertEquals("title1", saveNotice.getTitle());
        Assertions.assertEquals("content1", saveNotice.getContent());
    }

    @Test
    void 공지사항_전체조회_페이징(){
        Member member = Member.builder()
                .id(1L)
                .name("admin")
                .password("admin1")
                .phoneNum("01011112222")
                .addr("test")
                .email("admin1@gmail.com")
                .roles(Role.ADMIN)
                .birth("960601")
                .nickName("admin")
                .build();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Notice notice1 = Notice.builder().id(1L).admin(member).title("공지사항1").content("내용1").build();
        Notice notice2 = Notice.builder().id(2L).admin(member).title("공지사항2").content("내용2").build();
        List<Notice> noticeList = List.of(notice1, notice2);
        Page<Notice> noticePage = new PageImpl<Notice>(noticeList,pageable,noticeList.size());

        when(noticeRepository.findAll(pageable)).thenReturn(noticePage);

        //when
        Page<ResNoticeList> result = noticeService.getAll(pageable);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getTotalElements());
        Assertions.assertEquals("공지사항1", result.getContent().get(0).getTitle());
        verify(noticeRepository, times(1)).findAll(pageable);
    }

    @Test
    void 공지사항_id_조회(){
        Member member = Member.builder()
                .id(1L)
                .name("admin")
                .password("admin1")
                .phoneNum("01011112222")
                .addr("test")
                .email("admin1@gmail.com")
                .roles(Role.ADMIN)
                .birth("960601")
                .nickName("admin")
                .build();
        Long noticeId = 1L;
        Notice notice = Notice.builder()
                .id(noticeId)
                .admin(member)
                .title("title1")
                .content("content1")
                .build();

        when(noticeRepository.findById(noticeId)).thenReturn(Optional.of(notice));
        // When
        ResNoticeDetail result = noticeService.getId(noticeId);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("title1", result.getTitle());
        verify(noticeRepository, times(1)).findById(noticeId);
    }

    @Test
    void 공지사항_수정(){
        Member member = Member.builder()
                .id(1L)
                .name("admin")
                .password("admin1")
                .phoneNum("01011112222")
                .addr("test")
                .email("admin1@gmail.com")
                .roles(Role.ADMIN)
                .birth("960601")
                .nickName("admin")
                .build();
        // given
        Long noticeId = 1L;
        Notice ExistNotice = Notice.builder().id(noticeId).admin(member).title("기존 title1").content("기존 content1").build();
        ReqNoticeUpdate updateRequest = ReqNoticeUpdate.builder().title("새 제목1").content("새 내용1").build();
        when(noticeRepository.findById(noticeId)).thenReturn(Optional.of(ExistNotice));
        when(noticeRepository.save(Mockito.any(Notice.class))).thenReturn(ExistNotice);
        //when
        ResNoticeDetail result = noticeService.update(noticeId,updateRequest);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("새 제목1", result.getTitle());
        Assertions.assertEquals("새 내용1", result.getContent());
        verify(noticeRepository, times(1)).findById(noticeId);
        verify(noticeRepository, times(1)).save(Mockito.any(Notice.class));

    }

    @Test
    void 공지사항_삭제(){
        Member member = Member.builder()
                .id(1L)
                .name("admin")
                .password("admin1")
                .phoneNum("01011112222")
                .addr("test")
                .email("admin1@gmail.com")
                .roles(Role.ADMIN)
                .birth("960601")
                .nickName("admin")
                .build();
        //given
        Long noticeId = 1L;
        when(noticeRepository.existsById(noticeId)).thenReturn(true);

        //when
        noticeService.delete(noticeId);

        //then
        verify(noticeRepository, times(1)).existsById(noticeId);
        verify(noticeRepository, times(1)).deleteById(noticeId);
    }

    @Test
    void 공지사항_제목_검색(){
        Member member = Member.builder()
                .id(1L)
                .name("admin")
                .password("admin1")
                .phoneNum("01011112222")
                .addr("test")
                .email("admin1@gmail.com")
                .roles(Role.ADMIN)
                .birth("960601")
                .nickName("admin")
                .build();

        Notice notice1 = Notice.builder().id(1L).admin(member).title("공지사항1").content("내용1").build();
        Notice notice2 = Notice.builder().id(2L).admin(member).title("공지사항2").content("내용2").build();
        List<Notice> noticeList = List.of(notice1, notice2);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        // SearchDto 설정 (제목으로 검색)
        SearchDto searchDto = new SearchDto();
        searchDto.setTitle("공지사항1");  // 제목으로 검색

        //mock 제목으로 검색한 결과 반환
        Page<Notice> noticePage = new PageImpl<>(noticeList, pageable, noticeList.size());
        when(noticeRepository.findByTitleContaining(searchDto.getTitle(),pageable)).thenReturn(noticePage);

        // when 공지사항 검색 호출
        Page<ResNoticeList> result = noticeService.search(searchDto,pageable);
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("공지사항1", result.getContent().get(0).getTitle());
        Assertions.assertEquals("공지사항2", result.getContent().get(1).getTitle());
        verify(noticeRepository, times(1)).findByTitleContaining(searchDto.getTitle(),pageable);
    }

    @Test
    void 공지사항_내용_검색(){
        Member member = Member.builder()
                .id(1L)
                .name("admin")
                .password("admin1")
                .phoneNum("01011112222")
                .addr("test")
                .email("admin1@gmail.com")
                .roles(Role.ADMIN)
                .birth("960601")
                .nickName("admin")
                .build();

        Notice notice1 = Notice.builder().id(1L).admin(member).title("공지사항1").content("내용1").build();
        Notice notice2 = Notice.builder().id(2L).admin(member).title("공지사항2").content("내용2").build();
        List<Notice> noticeList = List.of(notice1, notice2);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        // SearchDto 설정 (제목으로 검색)
        SearchDto searchDto = new SearchDto();
        searchDto.setTitle("");
        searchDto.setContent("내용1");// 내용으로 검색

        // Mock: 내용으로 검색한 결과 반환
        Page<Notice> noticePage = new PageImpl<>(noticeList, pageable, noticeList.size());

        when(noticeRepository.findByContentContaining(searchDto.getContent(),pageable)).thenReturn(noticePage);

        // when
        Page<ResNoticeList> result = noticeService.search(searchDto,pageable);
        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals("내용1", result.getContent().get(0).getContent());
        Assertions.assertEquals("내용2",result.getContent().get(1).getContent());
    }
}
