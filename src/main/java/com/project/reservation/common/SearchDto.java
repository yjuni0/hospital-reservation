package com.project.reservation.common;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDto {
    private Long id;
    private String nickName;
    private String email;
    private String title;
    private String writer;
    private LocalDateTime createdDate;
    private int views;
    private int likes;
}
