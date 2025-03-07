package com.project.reservation.service.notice;

import com.project.reservation.dto.response.noticeFile.ResNoticeFileDownload;
import com.project.reservation.dto.response.noticeFile.ResNoticeFileUpload;
import com.project.reservation.entity.notice.Notice;
import com.project.reservation.entity.notice.NoticeFile;
import com.project.reservation.repository.notice.NoticeFileRepository;
import com.project.reservation.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class NoticeFileService {

    private final NoticeFileRepository noticeFileRepository;
    private final NoticeRepository noticeRepository;

    @Value("${project.folderPath}")// 파일 경로 properties 에 설정
    private String FOLDER_PATH;

    //파일 업로드
    public List<ResNoticeFileUpload> upload(Long noticeId, List<MultipartFile> multipartFiles){
        // 업로드 파일 개수 제한
        if (multipartFiles.size()>3){
            throw new IllegalArgumentException("파일은 최대 3개까지 업로드할 수 있습니다.");
        }
        // 공지사항 찾기
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 공지사항을 찾을 수 없습니다."));
        // 파일 리스트로 생성
        List<NoticeFile> noticeFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            // 파일 이름
            String originFileName = multipartFile.getOriginalFilename();
            // 중복 파일 방지 랜덤 이름 생성
            String randomId = UUID.randomUUID().toString();
            // 저장 파일 저장 경로 설정 ( OS 맞는 구분자 사용 separator )
            String filePath = FOLDER_PATH + File.separator + randomId + "_" + originFileName;
            log.info(filePath);
            // 파일을 저장할 경로가 존재하는지 확인하고, 없으면 폴더 생성
            File folder = new File(FOLDER_PATH);
            if (!folder.exists()) {
                folder.mkdirs();  // 경로가 없으면 폴더 생성
            }

            // 파일을 지정된 경로에 저장
            try{
                File savePath = new File(filePath);
                multipartFile.transferTo(savePath);
                log.info(savePath.getAbsolutePath()+"파일 저장 완료");
            } catch (IOException e) {
                throw new RuntimeException("파일 저장 중 오류가 발생 했습니다.");
            }

            // NoticeFile 객체 생성
            NoticeFile noticeFile = NoticeFile.builder()
                    .originFileName(originFileName)
                    .path(filePath)
                    .type(multipartFile.getContentType())
                    .build();
            noticeFile.setMappingNotice(notice);

            // 파일 저장 후 리스트에 추가
            noticeFiles.add(noticeFileRepository.save(noticeFile));
            log.info("DataBase 파일 저장 완료");
        }
        // 반환용 객체 전달을 위해 위해 dto 로 변환
        List<ResNoticeFileUpload> dtos = noticeFiles.stream().map(ResNoticeFileUpload::fromEntity).toList();
        log.info("업로드 요청 파일 목록 {}", dtos);
        return dtos;
    }

    //파일 다운로드
    public ResNoticeFileDownload download(Long noticeId, Long noticeFileId) throws IOException{
        // 파일 찾기
        NoticeFile file = noticeFileRepository.findById(noticeFileId).orElseThrow(()->new IllegalArgumentException("해당 아이디와 일치하는 파일이 없습니다."));
        log.info("요청 noticeId와 일치하는 파일 "+ file);
        // 파일 경로 체크 및 설정
        String filePath = file.getPath();
        if (!filePath.startsWith(FOLDER_PATH)) {
            filePath = Paths.get(FOLDER_PATH, filePath).toString();
            log.info("해당 파일 경로 체크 설정"+ filePath);
        }
        // 파일 존재 여부 체크
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            throw new IllegalArgumentException("해당 파일이 존재하지 않습니다.");
        }log.info("존재하는 파일 " + targetFile);

        //파일 타입
        String contentType = determineContentType(file.getType());
        log.info("파일 타입"+contentType);
        // 파일 내용 읽기
        try {
            byte[] content = Files.readAllBytes(targetFile.toPath());
            log.info("다운로드 요청 파일 목록" + content);
            return ResNoticeFileDownload.fromFileResource(file, contentType, content);
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는 도중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // content Type 에 따른 return
    private String determineContentType(String contentType) {
        return switch (contentType) {
            // 프로젝트 자바 17 버전 사용으로 향상된 swich 문 사용
            case "image/png" -> MediaType.IMAGE_PNG_VALUE;
            case "image/jpeg" -> MediaType.IMAGE_JPEG_VALUE;
            case "text/plain" -> MediaType.TEXT_PLAIN_VALUE;
            case "application/pdf" -> "application/pdf"; // PDF 지원
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"; // XLSX 지원
            case "application/vnd.ms-excel" -> "application/vnd.ms-excel"; // XLS 지원
            default -> MediaType.APPLICATION_OCTET_STREAM_VALUE; // 기타 파일 (예: 바이너리 파일)
        };
    }

    // 파일 삭제
    public void deleteFile(Long noticeFileId) throws IOException {
        // 파일이 있는지 확인
        NoticeFile file = noticeFileRepository.findById(noticeFileId).orElseThrow(()->new IllegalArgumentException("해당 파일이 존재하지 않습니다."));
        log.info("삭제 요청 파일"+file);
        // 파일 경로에서 실제 파일 삭제
        File targetFile = new File(file.getPath());
        if (targetFile.exists()) {
            targetFile.delete();
            log.info("폴더에서 요청 파일 삭제 완료");
        }
        // 데이터 베이스에서 파일 정보 삭제
        noticeFileRepository.deleteById(noticeFileId);
        log.info("Database 에서 요청 파일 삭제 완료");
    }


}
