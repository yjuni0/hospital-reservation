package com.project.reservation.Dto.response;

import com.project.reservation.entity.Slot;

import java.time.LocalTime;

public record ResSlotList(Long id, LocalTime slotTime, Boolean isAvailable) {
    public static ResSlotList fromEntity(Slot slot){
        return new ResSlotList(
                slot.getId(),
                slot.getSlotTime(),
                slot.getIsAvailable()
        );
    }
}
