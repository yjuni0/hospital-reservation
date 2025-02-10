package com.project.reservation.service;

import com.project.reservation.Dto.response.NoticeFileDownloadRes;
import com.project.reservation.Dto.response.NoticeFileUploadRes;
import com.project.reservation.entity.Member;
import com.project.reservation.entity.Notice;
import com.project.reservation.entity.NoticeFile;
import com.project.reservation.entity.Role;
import com.project.reservation.repository.NoticeFileRepository;
import com.project.reservation.repository.NoticeRepository;
import com.project.reservation.repository.NoticeRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoticeFileServiceTest {

    @Mock
    private NoticeFileRepository noticeFileRepository;

    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private NoticeFileService noticeFileService;

private final String FOLDER_PATH = "./files/";
    private Notice notice;

    @BeforeEach
    void setUp() {
        // 공지사항 객체 초기화
        notice = new Notice();
        notice.setId(1L);
        notice.setTitle("공지사항1");
        notice.setContent("내용1");
        ReflectionTestUtils.setField(noticeFileService, "FOLDER_PATH", "./files/");

    }
    @Test
    void 파일_업로드_성공() throws IOException {
        // given
        // multipartfile 객체를 mock 객체로 생성
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "hello".getBytes());

        when(noticeRepository.findById(1L)).thenReturn(Optional.of(notice));
        NoticeFile mockNoticeFile = NoticeFile.builder()
                .id(1L)
                .originFileName(file.getOriginalFilename())
                .path("./files/randomId_test.txt")
                .type(file.getContentType())
                .build();
        when(noticeFileRepository.save(any(NoticeFile.class))).thenReturn(mockNoticeFile);

        // when
        List<NoticeFileUploadRes> result = noticeFileService.upload(1L, List.of(file));

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test.txt", result.get(0).getOriginFileName()); // 파일명 확인
        assertEquals("./files/randomId_test.txt", result.get(0).getPath());
        verify(noticeFileRepository, times(1)).save(any(NoticeFile.class)); // save 호출 여부 확인
    }

    @Test
    void 파일_업로드_갯수_초과_예외() throws IOException {
        // given
        MockMultipartFile file1 = new MockMultipartFile("file", "test1.txt", "text/plain", "hello".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file", "test2.txt", "text/plain", "world".getBytes());
        MockMultipartFile file3 = new MockMultipartFile("file", "test3.txt", "text/plain", "test".getBytes());
        MockMultipartFile file4 = new MockMultipartFile("file", "test4.txt", "text/plain", "exceed".getBytes());

        // when, then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            noticeFileService.upload(1L, List.of(file1, file2, file3, file4));  // 4개 파일 업로드 시도
        });

        assertEquals("파일은 최대 3개까지 업로드할 수 있습니다.", exception.getMessage());
    }

    @Test
    void 파일_다운로드_성공() throws IOException {
        NoticeFile noticeFile = NoticeFile.builder()
                .id(1L)
                .originFileName("test.txt")
                .path(FOLDER_PATH+"test.txt")
                .type("text/plain")
                .build();
         //given
        when(noticeFileRepository.findById(1L)).thenReturn(Optional.of(noticeFile));

        //when
        NoticeFileDownloadRes downloadRes = noticeFileService.download(1L);

        //then
        assertNotNull(downloadRes);
        assertEquals("test.txt",downloadRes.getOriginalFilename());
        assertEquals(MediaType.TEXT_PLAIN_VALUE,downloadRes.getFileType());
        assertNotNull(downloadRes.getContent());
        assertTrue(downloadRes.getContent().length>0);

        // 파일 리소스 확인
        verify(noticeFileRepository, times(1)).findById(1L);

    }

    @Test
    void 파일_삭제()throws IOException{
        NoticeFile noticeFile = NoticeFile.builder()
                .id(1L)
                .originFileName("test.txt")
                .path(FOLDER_PATH+"test.txt")
                .type("text/plain")
                .build();
        //given
        when(noticeFileRepository.findById(1L)).thenReturn(Optional.of(noticeFile));
        File mockFile = new File(FOLDER_PATH+"test.txt");
        // when
        noticeFileService.deleteFile(1L);
        //then
        assertFalse(mockFile.exists(),"파일이 삭제 되어야 합니다."); // 파일 삭제 확인
        verify(noticeFileRepository, times(1)).deleteById(1L); //디비삭제 확이
    }
    @Test
    void 파일_삭제_파일_존재하지_않음() throws IOException {
        NoticeFile noticeFile = NoticeFile.builder()
                .id(1L)
                .originFileName("test.txt")
                .path(FOLDER_PATH+"test.txt")
                .type("text/plain")
                .build();
        // given
        when(noticeFileRepository.findById(1L)).thenReturn(Optional.of(noticeFile));
        File mockFile = new File(FOLDER_PATH + "test.txt");

        // when, then
        IOException thrown = assertThrows(IOException.class, () -> {
            noticeFileService.deleteFile(2L);
        });
        assertEquals("파일이 존재하지 않습니다: " + FOLDER_PATH + "test.txt", thrown.getMessage());  // 예외 메시지 확인
    }
}
