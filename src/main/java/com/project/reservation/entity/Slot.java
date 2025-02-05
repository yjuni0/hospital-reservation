package com.project.reservation.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean state;

    @Column(nullable = false)
    private LocalDateTime startTime;  // 슬롯 시작 시간

    @Column(nullable = false)
    private LocalDateTime endTime;    // 슬롯 종료 시간

    @OneToOne(mappedBy = "slot", cascade = CascadeType.ALL)
    public Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    public Available available;

    @Builder
    public Slot(Long id, Reservation reservation, Available available, Boolean state, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.reservation = reservation;
        this.available = available;
        this.state = state;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
