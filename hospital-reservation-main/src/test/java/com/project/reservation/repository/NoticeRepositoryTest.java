package com.project.reservation.repository;


import com.project.reservation.entity.notice.Notice;
import com.project.reservation.repository.notice.NoticeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@DataJpaTest // spring 이 자동으로 h2 같은 테스터용 인메모리 DB 설정
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) 직접 테스터용 인메모리 디비 설정 방법 application.properties 에도 추가
public class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository repository;


    //Optional
    @Test
    public void NoticeRepository_FindById_ReturnNotice(){
        // given
        Notice notice1 = Notice.builder()
                .id(1L)
                .title("title1")
                .content("content1")
                .build();
        Notice notice2 = Notice.builder()
                .id(2L)
                .title("title2")
                .content("content2")
                .build();
        repository.saveAll(List.of(notice1, notice2));

        // when
        Optional<Notice> optionalNotice1 = repository.findById(1L);
        Optional<Notice> optionalNotice3= repository.findById(3L);

        // then
        Assertions.assertTrue(optionalNotice1.isPresent());
        Assertions.assertFalse(optionalNotice3.isPresent());

    }

    // Pageable
    @Test
    public void NoticeRepository_FindByAll_Page(){
        // given
        Notice notice1 = Notice.builder()
                .id(1L)
                .title("title1")
                .content("content1")
                .build();
        Notice notice2 = Notice.builder()
                .id(2L)
                .title("title2")
                .content("content2")
                .build();
        repository.saveAll(List.of(notice1, notice2));
        // when
        Page<Notice> pageNotice = repository.findAll(PageRequest.of(0, 10));
        //then
        Assertions.assertNotNull(pageNotice);
        Assertions.assertEquals(1, pageNotice.getTotalPages());
    }

    // title 검색
    @Test
    public void NoticeRepository_FindByContainingTitle() {
        // given (테스트 데이터 준비)
        Notice notice1 = Notice.builder()
                .title("Spring Boot Guide")
                .content("Spring Boot JPA Test")
                .build();
        Notice notice2 = Notice.builder()
                .title("Spring Security Tips")
                .content("Security Best Practices")
                .build();
        Notice notice3 = Notice.builder()
                .title("Java Streams")
                .content("Java 8 Streams API Guide")
                .build();

        repository.saveAll(List.of(notice1, notice2, notice3));

        // when (제목에 'Spring'이 포함된 게시글 검색)
        Page<Notice> pageNotice = repository.findByTitleContaining("Spring", PageRequest.of(0, 10));

        // then (결과 검증)
        Assertions.assertNotNull(pageNotice);
        Assertions.assertEquals(2, pageNotice.getTotalElements()); // 'Spring' 포함된 2개 게시글 확인
        Assertions.assertTrue(pageNotice.getContent().stream()
                .allMatch(notice -> notice.getTitle().contains("Spring"))); // 제목이 'Spring'을 포함하는지 확인
    }

    // content 검색
    @Test
    public void NoticeRepository_FindByContainingContent() {
        // given (테스트 데이터 준비)
        Notice notice1 = Notice.builder()
                .title("Spring Boot Guide")
                .content("Spring Boot JPA Test")
                .build();
        Notice notice2 = Notice.builder()
                .title("Spring Security Tips")
                .content("Spring Security Best Practices")
                .build();
        Notice notice3 = Notice.builder()
                .title("Java Streams")
                .content("Java 8 Streams API Guide")
                .build();

        repository.saveAll(List.of(notice1, notice2, notice3));

        // when (제목에 'Spring'이 포함된 게시글 검색)
        Page<Notice> pageNotice = repository.findByContentContaining("Spring", PageRequest.of(0, 10));

        // then (결과 검증)
        Assertions.assertNotNull(pageNotice);
        Assertions.assertEquals(2, pageNotice.getTotalElements()); // 'Spring' 포함된 2개 게시글 확인
        Assertions.assertTrue(pageNotice.getContent().stream()
                .allMatch(notice -> notice.getContent().contains("Spring"))); // 내용에 'Spring'을 포함하는지 확인
    }
}
