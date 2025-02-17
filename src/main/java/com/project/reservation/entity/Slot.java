package com.project.reservation.entity;

import com.project.reservation.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isAvailable;

    @Column(nullable = false)
    private LocalTime slotTime;  // 슬롯 시간

    @ManyToOne(fetch = FetchType.LAZY)
    public AvailableDate availableDate;


    public void setAvailableDate(AvailableDate availableDate) {
        this.availableDate = availableDate;
        availableDate.getSlots().add(this);
    }

}
