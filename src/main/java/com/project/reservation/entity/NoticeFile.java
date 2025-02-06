package com.project.reservation.entity;

import com.project.reservation.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class NoticeFile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String path;

    @ManyToOne
    @JoinColumn(name="notice_id", nullable=false)
    private Notice notice;

    @Builder
    public NoticeFile(Long id, String name, String type, String path, Notice notice) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.path = path;
        this.notice = notice;
    }
}
