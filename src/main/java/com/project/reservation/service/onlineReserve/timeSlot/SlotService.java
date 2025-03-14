package com.project.reservation.service.onlineReserve.timeSlot;

import com.project.reservation.dto.response.reservation.ResSlotList;
import com.project.reservation.entity.onlineReserve.AvailableDate;
import com.project.reservation.entity.onlineReserve.Department;
import com.project.reservation.entity.onlineReserve.Slot;
import com.project.reservation.repository.onlineReserve.AvailableDateRepository;
import com.project.reservation.repository.onlineReserve.DepartmentRepository;
import com.project.reservation.repository.onlineReserve.SlotRepository;
import jakarta.persistence.EntityManager;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
@Transactional
public class SlotService {
    private final SlotRepository slotRepository;
    private final AvailableDateRepository availableDateRepository;
    private final DepartmentRepository departmentRepository;
    private final EntityManager entityManager;

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        log.info("[init] 실행 : 슬롯 생성 시작");
        if (slotRepository.count() == 0) {
            generateSlotsBatch();
        }
    }


    public void generateSlotsBatch() {
        List<Slot> slotsToSave = new ArrayList<>();
        List<AvailableDate> availableDates = availableDateRepository.findAll();
        int batchSize = 500;  // 한 번에 처리할 배치 크기
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
                    slotRepository.flush();
                    entityManager.clear();  // 영속성 컨텍스트 비우기
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
        Department department = departmentRepository.findByName(departmentName).orElseThrow(()->new IllegalArgumentException("해당 진료과가 없음"));
        AvailableDate availableDate = availableDateRepository.findByDateAndDepartment(date,department)
                .orElseThrow(()->new IllegalArgumentException("  진료과에 해당 예약 가능 날짜가 없음  "));

        List<Slot> slots = slotRepository.findByAvailableDateAndAvailableDateDepartment(availableDate,department);
        log.info("선택된 날짜 {} ",availableDate.getDate());
        log.info("슬롯 조회 {} 개", slots.size() );

        return slots.stream().map(ResSlotList::fromEntity).toList();

    }


}