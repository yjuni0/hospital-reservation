package com.project.reservation.repository.onlineReserve;

import com.project.reservation.entity.onlineReserve.AvailableDate;
import com.project.reservation.entity.onlineReserve.Department;
import com.project.reservation.entity.onlineReserve.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByAvailableDateAndAvailableDateDepartment(AvailableDate availableDate, Department availableDate_department);

    Slot findBySlotTime(LocalTime slotTime);

    Slot findBySlotTimeAndAvailableDate(LocalTime slotTime, AvailableDate availableDate);

    Slot findBySlotTimeAndAvailableDate_Date(LocalTime slotTime, LocalDate availableDateDate);

    Slot findByIsAvailableAndSlotTimeAndAvailableDate_Date(boolean b, LocalTime slotTime, LocalDate slotDate);
}