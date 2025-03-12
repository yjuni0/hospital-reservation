package com.project.reservation.entity.member;

import com.project.reservation.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class DeletedMember extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column
    private Long originalId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(length = 500)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickName;

    @Column(nullable = false, length = 50)
    private String addr;

    @Column
    private String birth;

    @Column(length = 13)
    private String phoneNum;

    @Column
    private LocalDateTime deletedAt;

    @Builder
    public DeletedMember(Long id, Long originalId, String name, String email, String password,
                         String nickName, String addr, String birth, String phoneNum, LocalDateTime deletedAt) {
        this.id = id;
        this.originalId = originalId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.addr = addr;
        this.birth = birth;
        this.phoneNum = phoneNum;
        this.deletedAt = deletedAt;
    }
}
