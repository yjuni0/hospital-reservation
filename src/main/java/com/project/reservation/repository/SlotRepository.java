package com.project.reservation.repository;

import com.project.reservation.entity.AvailableDate;
import com.project.reservation.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {
    Optional<Slot> findByAvailableDateAndSlotTime(AvailableDate availableDate, LocalTime slotTime);

}
