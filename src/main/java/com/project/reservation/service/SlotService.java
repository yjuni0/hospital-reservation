package com.project.reservation.service;

import com.project.reservation.Dto.response.ResSlotList;
import com.project.reservation.entity.AvailableDate;
import com.project.reservation.entity.Department;
import com.project.reservation.entity.Slot;
import com.project.reservation.repository.AvailableDateRepository;
import com.project.reservation.repository.DepartmentRepository;
import com.project.reservation.repository.SlotRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SlotService {
    private final SlotRepository slotRepository;
    private final AvailableDateRepository availableDateRepository;
    private final DepartmentRepository departmentRepository;


    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        log.info("[init] 실행 : 슬롯 생성 시작");
        generateSlotsBatch();
    }


    public void generateSlotsBatch() {
        List<Slot> slotsToSave = new ArrayList<>();
        List<AvailableDate> availableDates = availableDateRepository.findAll();
        int batchSize = 1000;  // 한 번에 처리할 배치 크기
        int count = 0;

        for (AvailableDate availableDate : availableDates) {
            for (LocalTime slotTime = LocalTime.of(10, 0); slotTime.isBefore(LocalTime.of(18, 0)); slotTime = slotTime.plusMinutes(20)) {
                Slot slot = Slot.builder()
                        .isAvailable(true)
                        .availableDate(availableDate)
                        .slotTime(slotTime)
                        .build();
                slotsToSave.add(slot);

                count++;
                if (count % batchSize == 0) {
                    // 배치 크기마다 flush를 해서 성능을 최적화합니다.
                    slotRepository.flush();

                    log.info("현재 슬롯 생성된 수: {}", slotsToSave.size());
                    log.info("현재 배치 크기: {}", batchSize);
                }
            }
        }
        if (!slotsToSave.isEmpty()) {
            slotRepository.saveAll(slotsToSave);
            slotRepository.flush();
        }
    }

    // 슬롯 조회
    public List<ResSlotList> getSlotListOfDateAndDepartment(String departmentName, LocalDate date) {
        AvailableDate availableDate = availableDateRepository.findByDate(date)
                .orElseThrow(()->new IllegalArgumentException("해당 날짜에 대한 예약 가능 일이 없음 "));
        Department department = departmentRepository.findByName(departmentName)
                .orElseThrow(()->new IllegalArgumentException("해당 진료과가 없음"));

        List<Slot> slots = slotRepository.findByAvailableDateDateAndAvailableDateDepartment(date,department);

        return slots.stream().map(ResSlotList::fromEntity).toList();

    }


}