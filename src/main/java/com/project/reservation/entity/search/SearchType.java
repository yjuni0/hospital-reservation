package com.project.reservation.entity.search;

import lombok.Getter;

import java.util.List;



@Getter
public enum SearchType {
    NOTICE(List.of("title", "content"),FieldType.STRING),
    MEMBER(List.of("name", "nickName", "email", "birth"),FieldType.STRING),
    MEMBER_CREATE(List.of("createdDate"), FieldType.LOCAL_DATE_TIME),
    RESERVATION(List.of("nickName", "petName","departmentName"),FieldType.STRING),
    RESERVATION_DATE(List.of("date"), FieldType.LOCAL_DATE_TIME),
    REVIEW(List.of("title", "nickName", "content"),FieldType.STRING),
    QUESTION(List.of("title", "nickName", "content"),FieldType.STRING),
    PET(List.of("name", "age"),FieldType.STRING, FieldType.INTEGER);

    private final List<String> fields;
    private final List<FieldType> fieldTypes;

    SearchType(List<String> fields, FieldType... fieldTypes) {
        this.fields = fields;
        this.fieldTypes = List.of(fieldTypes);
    }



    public enum FieldType {
        STRING,
        INTEGER,
        LOCAL_DATE_TIME
    }
}
