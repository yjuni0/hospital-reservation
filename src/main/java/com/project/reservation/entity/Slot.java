package com.project.reservation.entity;

import com.project.reservation.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

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

    @Setter
    private Boolean isAvailable = true;

    @Column(nullable = false)
    private LocalTime slotTime;  // 슬롯 시간

    @ManyToOne
    @JoinColumn(nullable = false)
    public AvailableDate availableDate;


    public void setAvailableDate(AvailableDate availableDate) {
        this.availableDate = availableDate;
        availableDate.getSlots().add(this);
    }

}
