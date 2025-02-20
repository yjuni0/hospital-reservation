package com.project.reservation.repository.onlineReserve;

import com.project.reservation.entity.onlineReserve.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByName(String name);

   Optional<Department> findByName(String departmentName);
}
