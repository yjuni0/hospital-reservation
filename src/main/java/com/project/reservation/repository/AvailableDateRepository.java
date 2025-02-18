package com.project.reservation.repository;

import com.project.reservation.entity.AvailableDate;
import com.project.reservation.entity.Department;
import com.project.reservation.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailableDateRepository extends JpaRepository<AvailableDate,Long> {


    List<AvailableDate> findByDateBetween(LocalDate startDate, LocalDate endDate);
    boolean existsByDepartmentAndDate(Department department, LocalDate date);

    Optional<AvailableDate> findByDate(LocalDate date);

    Optional<AvailableDate> findByDateAndDepartment(LocalDate date, Department department);
}
