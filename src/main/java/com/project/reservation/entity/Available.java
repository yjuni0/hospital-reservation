package com.project.reservation.entity;

import com.project.reservation.common.BaseTimeEntity;
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
public class Available extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "available", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Slot> slots;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Department department;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Builder
    public Available(Long id, Department department, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.department = department;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

}
