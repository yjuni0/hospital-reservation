package com.project.reservation.test;


import org.junit.jupiter.api.*;

public class TestLifeCycle {

    @BeforeAll // 가장 처음 실행
    public static void beforeAll() {
        System.out.println("beforeAll");
        System.out.println();
    }
    @AfterAll // 가장 마지막에 실행
    public static void afterAll() {
        System.out.println("afterAll 호출 ");
        System.out.println();
    }
    @BeforeEach // 각 메서드 이전 실행
    public void beforeEach() {
        System.out.println("beforeEach 호출");
        System.out.println();
    }
    @AfterEach // 각 메서드 이후 실행
    public void afterEach() {
        System.out.println("afterEach 호출 ");
        System.out.println();
    }
    @Test
    void test1() {
        System.out.println("test1 호출");
        System.out.println();
    }
    @Test
    @DisplayName("Test Case 2!! ")
    void test2() {
        System.out.println("test2 호출");
        System.out.println();
    }
    @Test
    @Disabled // Disabled Annotation : 테스트를 실행하지 않게 설정
    void test3() {
        System.out.println("test3 호출");
        System.out.println();
    }


}

