package com.project.reservation.service;

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

    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        generateSlotsBatch();
    }
    public void generateSlotsBatch() {
        List<Slot> slotsToSave = new ArrayList<>();
        List<AvailableDate> availableDates = availableDateRepository.findAll();
        int batchSize = 100;  // 한 번에 처리할 배치 크기
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
                }
            }
        }
        if (!slotsToSave.isEmpty()) {
            slotRepository.saveAll(slotsToSave);
            slotRepository.flush();
        }
    }


}