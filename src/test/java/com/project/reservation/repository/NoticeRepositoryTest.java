package com.project.reservation.repository;

import com.project.reservation.entity.Member;
import com.project.reservation.entity.Notice;
import com.project.reservation.entity.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository repository;

    // one object save
    @Test
    public void NoticeRepository_SaveAll_ReturnSavedNotice(){
        
        //given
        Notice notice = Notice.builder()
                .id(1L)
                .title("title1")
                .content("content1")
                .build();
        
        //when
        Notice savedNotice = repository.save(notice);
        
        // then
        Assertions.assertNotNull(savedNotice);
        Assertions.assertEquals(1L, savedNotice.getId());
        Assertions.assertEquals("title1", savedNotice.getTitle());
        Assertions.assertEquals("content1", savedNotice.getContent());
        
    }
    // get List
    @Test
    public void NoticeRepository_FindAll_ReturnAllNotices(){
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
        List<Notice> notices = repository.findAll();
        // then
        Assertions.assertNotNull(notices);
        Assertions.assertEquals(2, notices.size());
    }

    //Optional
    @Test
    public void NoticeRepository_FindById_ReturnNotices(){
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
}
