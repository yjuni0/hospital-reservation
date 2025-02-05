package com.project.reservation.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Available {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "available", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    public List<Slot> slots;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    public Department department;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;


    @Builder
    public Available(Long id, List<Slot> slots, Department department, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.slots = slots;
        this.department = department;
        this.startTime = startTime;
        this.endTime = endTime;

    }
}
