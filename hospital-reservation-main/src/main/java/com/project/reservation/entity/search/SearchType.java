//package com.project.reservation.entity.search;
//
//import lombok.Getter;
//
//import java.util.List;
//
//@Getter
//public enum SearchType {
//    NOTICE(List.of("title", "content"),List.of(FieldType.STRING, FieldType.STRING)),
//    MEMBER(List.of("name", "nickName", "email", "birth","createdDate"),List.of(FieldType.STRING, FieldType.STRING, FieldType.STRING, FieldType.STRING,FieldType.LOCAL_DATE)),
//    RESERVATION(List.of("petName","departmentName","date"),List.of(FieldType.STRING, FieldType.STRING,FieldType.LOCAL_DATE)),
//    REVIEW(List.of("title", "nickName", "content"),List.of(FieldType.STRING, FieldType.STRING, FieldType.STRING)),
//    QUESTION(List.of("title", "nickName", "content","createdDate"),List.of(FieldType.STRING, FieldType.STRING, FieldType.STRING,FieldType.LOCAL_DATE)),
//    PET(List.of("name", "age"),List.of(FieldType.STRING, FieldType.INTEGER));
//
//    private final List<String> fields;
//    private final List<FieldType> fieldTypes;
//
//    SearchType(List<String> fields, List<FieldType> fieldTypes) {
//        this.fields = fields;
//        this.fieldTypes = fieldTypes;
//    }
//
//    public enum FieldType {
//        STRING,
//        INTEGER,
//        LOCAL_DATE
//    }
//}
