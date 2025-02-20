package com.project.reservation.service.onlineReserve.timeSlot;

import com.project.reservation.entity.onlineReserve.Department;
import com.project.reservation.repository.onlineReserve.DepartmentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService {
    
    private final DepartmentRepository departmentRepository;
    @PostConstruct
    @Transactional
    public void init() {
        log.info("[init 실행 ] : 진료과 데이터 삽입 시작");
        List<String> departmentNames = List.of("내과", "안과", "치과", "외과", "피부과", "정형외과");
        // 기본 데이터 삽입 예시
        for (String name : departmentNames) {
            if (!departmentRepository.existsByName(name)) { // 중복 체크 후 삽입
                departmentRepository.save(Department.builder().name(name).build());
            }
        }
    }
}