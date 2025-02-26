package com.project.reservation.entity.notice;

import com.project.reservation.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NoticeFile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originFileName;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private String type;


    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(nullable=false)
    private Notice notice;

    @Builder
    public NoticeFile(Long id, String originFileName, String path, String type) {
        this.id = id;
        this.originFileName = originFileName;
        this.path = path;
        this.type = type;
    }


    // 매핑 메서드
    public void setMappingNotice(Notice notice) {
        this.notice = notice;
        if (notice != null) {
            notice.getNoticeFile().add(this);  // 양방향 설정
        }
    }
}
