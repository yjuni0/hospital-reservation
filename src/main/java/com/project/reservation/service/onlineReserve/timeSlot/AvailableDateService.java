package com.project.reservation.service.onlineReserve.timeSlot;

import com.project.reservation.entity.onlineReserve.AvailableDate;
import com.project.reservation.entity.onlineReserve.Department;
import com.project.reservation.repository.onlineReserve.AvailableDateRepository;
import com.project.reservation.repository.onlineReserve.DepartmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AvailableDateService {
    private final AvailableDateRepository availableDateRepository;
    private final DepartmentRepository departmentRepository;


    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("[init] 서버 실행 : 오늘부터 다음 달 마지막 날까지 예약 가능 일자 생성 시작");
        generateAvailableDatesForFromTodayToNextMonthEnd();
    }
    // 서버 실행 시 현재 날 부터 다음 달 마지막 날까지 예약 가능 날짜 자동 생성
    public void generateAvailableDatesForFromTodayToNextMonthEnd(){
        List<Department> departments = departmentRepository.findAll();
        LocalDate today = LocalDate.now();
        LocalDate nextMonthEnd = today.plusMonths(1).withDayOfMonth(today.plusMonths(1).lengthOfMonth());

        log.info("{}~{} 예약 가능 일자 생성", today, nextMonthEnd);
        // 각 부서마다 가능한 날짜를 생성
        for (Department department : departments) {
            for (LocalDate date = today.withDayOfMonth(1); !date.isAfter(nextMonthEnd); date = date.plusDays(1)) {
                if (!availableDateRepository.existsByDepartmentAndDate(department, date)) {
                    availableDateRepository.save(AvailableDate.builder()
                            .department(department)
                            .localDate(date)
                            .build());
                    log.debug("[init ] : {}부서 - {}날짜 생성 완료", department, date);
                }
            }
        }
    }
    // 다음 달 예약 가능 날짜 자동 생성 메서드
    public void generateAvailableDatesForNextMonth() {
        List<Department> departments = departmentRepository.findAll();
        LocalDate nextMonthStart = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        LocalDate nextMonthEnd = nextMonthStart.withDayOfMonth(nextMonthStart.lengthOfMonth());
        log.info("[다음 달 예약 가능 일 생성 실행] {} ~ {} 날짜 생성 시작 ", nextMonthStart, nextMonthEnd);
        for (Department department : departments) {
            for (LocalDate date = nextMonthStart; !date.isAfter(nextMonthEnd); date = date.plusDays(1)) {
                if (!availableDateRepository.existsByDepartmentAndDate(department, date)) {
                    availableDateRepository.save(AvailableDate.builder()
                            .department(department)
                            .localDate(date)
                            .build());
                    log.debug("[스케줄]:{} 부서 - {} 날짜 생성 완료 ", department, date);
                }
            }
        }
    }
    // 이전 달의 예약 가능 날짜 삭제 메서드 ( 불필요 데이터 삭제 )
    public void deletePreviousMonthAvailableDates() {
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

        log.info("이전 달의 예약 가능 날짜 삭제: {} to {}", firstDayOfPreviousMonth, lastDayOfPreviousMonth);
    }

}