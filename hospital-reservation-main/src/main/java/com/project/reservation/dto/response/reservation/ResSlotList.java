package com.project.reservation.dto.response.reservation;

import com.project.reservation.entity.onlineReserve.Slot;

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
