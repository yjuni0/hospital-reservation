package com.project.reservation.common;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDto {
    //member
    private Long id;
    private String name;
    private String nickName;
    private String email;
    private String phoneNum;
    private String birth;

    // review, notice,question
    private String title;
    private String writer;
    private int views;
    private int likes;

    // reservation
    private String departmentName;
    private String petName;

    // 날짜 검색
    private String  createdDate;

}
