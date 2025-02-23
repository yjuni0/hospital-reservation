package com.project.reservation.repository.onlineReserve;

import com.project.reservation.entity.onlineReserve.AvailableDate;
import com.project.reservation.entity.onlineReserve.Department;
import com.project.reservation.entity.onlineReserve.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalTime;
import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByAvailableDateAndAvailableDateDepartment(AvailableDate availableDate, Department availableDate_department);

    Slot findBySlotTime(LocalTime slotTime);
}