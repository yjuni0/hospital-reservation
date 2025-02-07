package com.project.reservation.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "review_likes",
uniqueConstraints = { @UniqueConstraint(columnNames = {"review_id", "member_id"})})
@Getter
public class ReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;



}
