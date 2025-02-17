package com.project.reservation.entity;

import com.project.reservation.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class AvailableDate{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "availableDate", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Slot> slots;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Department department;

    @Column(nullable = false)
    private LocalDate date;



    @Builder
    public AvailableDate(Long id, Department department, LocalDate localDate) {
        this.id = id;
        this.department = department;
        this.date = localDate;
    }

    public void setDepartment(Department department) {
        this.department = department;
        department.getAvailableDate().add(this);
    }

}
