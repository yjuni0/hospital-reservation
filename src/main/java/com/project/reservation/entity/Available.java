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
        // 기본 startTime과 endTime을 현재 날짜 기준으로 설정
        LocalDateTime todayStartTime = LocalDateTime.now().toLocalDate().atTime(9, 0);  // 오늘 오전 9시
        LocalDateTime todayEndTime = LocalDateTime.now().toLocalDate().atTime(18, 0);  // 오늘 오후 6시

        this.startTime = startTime != null ? startTime : todayStartTime;
        this.endTime = endTime != null ? endTime : todayEndTime;

    }
    // 슬롯 자동 생성 로직 (예: 30분 단위로 생성)
    public void generateSlotsForMonth() {
        List<Slot> generatedSlots = new ArrayList<>();
        LocalDateTime slotTime = startTime;

        // startTime 부터 endTime 까지 30분 단위로 Slot 생성
        while (!slotTime.isAfter(endTime)) {
            Slot slot = Slot.builder()
                    .available(this)
                    .state(true)  // 기본적으로 활성 상태
                    .startTime(slotTime)
                    .endTime(slotTime.plusMinutes(30)) // 30분 단위
                    .build();

            generatedSlots.add(slot);
            slotTime = slotTime.plusMinutes(30); // 30분씩 증가
        }

        this.slots = generatedSlots;
    }


}
