package com.project.reservation.repository;

import com.project.reservation.entity.AvailableDate;
import com.project.reservation.entity.Department;
import com.project.reservation.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findByAvailableDateAndAvailableDateDepartment(AvailableDate availableDate, Department availableDate_department);

}