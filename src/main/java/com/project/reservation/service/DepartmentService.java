package com.project.reservation.service;

import com.project.reservation.entity.Department;
import com.project.reservation.repository.DepartmentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    
    private final DepartmentRepository departmentRepository;
    @PostConstruct
    @Transactional
    public void init() {
        // 기본 데이터 삽입 예시
        if (departmentRepository.count() == 0) {  // 데이터가 없을 때만 삽입
            departmentRepository.save(Department.builder().name("내과").build());
            departmentRepository.save(Department.builder().name("안과").build());
            departmentRepository.save(Department.builder().name("치과").build());
            departmentRepository.save(Department.builder().name("외과").build());
            departmentRepository.save(Department.builder().name("피부과").build());
            departmentRepository.save(Department.builder().name("정형외과").build());
        }
    }
}