package com.project.reservation.service.onlineReserve.timeSlot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledTaskService {
    private final AvailableDateService availableDateService;
    private final SlotService slotService;


    @Scheduled(cron = "0 0 0 1 * ?")
    public void ScheduledTask() {
        log.info("[ScheduledTaskService ] 실행 : AvailableDate 및 슬롯 생성");

        try {
            availableDateService.deletePreviousMonthAvailableDates();
            availableDateService.generateAvailableDatesForNextMonth();
            slotService.generateSlotsBatch();

            log.info("[ScheduledTaskService] 작업 완료 ");
        }catch (Exception e){
            log.error("[ScheduledTaskService] 작업 중 오류 발생 ", e);
        }
    }

}
