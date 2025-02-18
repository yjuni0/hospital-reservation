package com.project.reservation.entity;

import com.project.reservation.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 500, nullable = false)
    private String content;

    @OneToMany(mappedBy = "notice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<NoticeFile> noticeFile;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id")//,nullable = false)
    private Member admin;

    @Builder
    public Notice(Long id, String title, String content, Member admin) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.admin = admin;
    }

    // 테스트용
    @Builder
    public Notice(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // 공지사항 수정 메서드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setMappingAdmin(Member admin) {
        this.admin = admin;
        admin.getNotices().add(this);
    }
}
