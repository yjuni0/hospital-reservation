package com.project.reservation.repository.onlineReserve;

import com.project.reservation.entity.onlineReserve.AvailableDate;
import com.project.reservation.entity.onlineReserve.Department;
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
