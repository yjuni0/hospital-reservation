package com.project.reservation.service;

import com.project.reservation.entity.AvailableDate;
import com.project.reservation.entity.Department;
import com.project.reservation.entity.Slot;
import com.project.reservation.repository.AvailableDateRepository;
import com.project.reservation.repository.DepartmentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AvailableDateService {
    private final AvailableDateRepository availableDateRepository;
    private final DepartmentRepository departmentRepository;
    @Scheduled(cron = "0 0 0 1 * ?")
    public void scheduleAvailableDatesGeneration(){
        deletePreviousMonthAvailableDates();
        generateAvailableDatesForDepartments();


    }
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        generateAvailableDatesForDepartments();
    }

    private void generateAvailableDatesForDepartments(){
        List<Department> departments = departmentRepository.findAll();
        LocalDate today = LocalDate.now();
        LocalDate nextMonth = today.plusMonths(1).withDayOfMonth(1);

        // 각 부서마다 가능한 날짜를 생성
        for (Department department : departments) {
            for (LocalDate date = today.withDayOfMonth(1); !date.isAfter(nextMonth); date = date.plusDays(1)) {
                AvailableDate availableDate = AvailableDate.builder()
                        .department(department)
                        .localDate(date)
                        .build();
                availableDateRepository.save(availableDate);
            }
        }
    }
    private void deletePreviousMonthAvailableDates() {
        // 오늘 날짜
        LocalDate today = LocalDate.now();

        // 이전 달의 첫째 날 계산
        LocalDate firstDayOfPreviousMonth = today.minusMonths(1).withDayOfMonth(1);

        // 이전 달의 마지막 날 계산 (2월의 경우 28일 또는 29일)
        LocalDate lastDayOfPreviousMonth = firstDayOfPreviousMonth.withDayOfMonth(firstDayOfPreviousMonth.lengthOfMonth());

        // 이전 달의 AvailableDate를 삭제
        List<AvailableDate> previousMonthAvailableDates = availableDateRepository.findByDateBetween(firstDayOfPreviousMonth, lastDayOfPreviousMonth);

        // 삭제
        availableDateRepository.deleteAll(previousMonthAvailableDates);

        log.info("Deleted AvailableDates for previous month: {} to {}", firstDayOfPreviousMonth, lastDayOfPreviousMonth);
    }

}