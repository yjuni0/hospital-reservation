# 웹 프로젝트

## ✔ 병원 웹 사이트 ( 예약 )

  

React + Spring 활용 웹 사이트 제작

  

  

* * *

# frontend

  

### 웹 사이트

  

메인 페이지와 메뉴 구성 및 전반적인 정적, 동적 페이지 구현

  

### Language

[![](https://camo.githubusercontent.com/1376cb018bc52e2dcf0ebeda06b1185db8fc231a0e9588713a048e87f7c11c37/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f48544d4c352d4533344632363f7374796c653d666c61742d737175617265266c6f676f3d48544d4c35266c6f676f436f6c6f723d7768697465)](https://camo.githubusercontent.com/1376cb018bc52e2dcf0ebeda06b1185db8fc231a0e9588713a048e87f7c11c37/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f48544d4c352d4533344632363f7374796c653d666c61742d737175617265266c6f676f3d48544d4c35266c6f676f436f6c6f723d7768697465) [![](https://camo.githubusercontent.com/62e058ea5cad05af7cd86d0ab3fccf81be8c734e8df7e7a4249c8fc49b45063b/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f435353332d3135373242363f7374796c653d666c61742d737175617265266c6f676f3d43535333266c6f676f436f6c6f723d7768697465)](https://camo.githubusercontent.com/62e058ea5cad05af7cd86d0ab3fccf81be8c734e8df7e7a4249c8fc49b45063b/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f435353332d3135373242363f7374796c653d666c61742d737175617265266c6f676f3d43535333266c6f676f436f6c6f723d7768697465) 

### Framework / Library

  

[![](https://camo.githubusercontent.com/f5aaeddebfb89b74e44e33753e684f64b764729d8455bbae398911442f946ab9/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f52656163742d3631444146423f7374796c653d666c61742d737175617265266c6f676f3d5265616374266c6f676f436f6c6f723d7768697465)](https://camo.githubusercontent.com/f5aaeddebfb89b74e44e33753e684f64b764729d8455bbae398911442f946ab9/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f52656163742d3631444146423f7374796c653d666c61742d737175617265266c6f676f3d5265616374266c6f676f436f6c6f723d7768697465) [![](https://camo.githubusercontent.com/71e193a2ebb68a66e34ca4eced4275caf19cc3ec2a5ab15ef2d318289f62c8c9/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f52656163745f726f757465722d4341343234353f7374796c653d666c61742d737175617265266c6f676f3d72656163742d726f75746572266c6f676f436f6c6f723d7768697465)](https://camo.githubusercontent.com/71e193a2ebb68a66e34ca4eced4275caf19cc3ec2a5ab15ef2d318289f62c8c9/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f52656163745f726f757465722d4341343234353f7374796c653d666c61742d737175617265266c6f676f3d72656163742d726f75746572266c6f676f436f6c6f723d7768697465)

## Contents

▶ 홈페이지

*   각 병원 사이트 참조 “/” 접속 시 메인 페이지 접속을 위한 홈페이지
*   메인 화면 ( “/main” ) 링크 삽입

  

▶메인 페이지

*   병원 로고 “/main” 링크 삽입
*   디자인 각 병원 사이트 참조 “/” –> “/main” 접속
*   Nav와 signin 및 signup 가능
*   Signin 과 signup 은 /signin< (정보 입력) /로그인하기 / 회원 가입/ > 로그인 페이지가 우선 적용
*   로그인 완료 후 >/main 로 리다이렉트 처리
*   회원 가입 시도는 /signup 페이지로  

  

▶메뉴 구성

*   메뉴 병원 소개, 진료 안내, 고객센터, 건강 정보 ( 추후 관리자가 정보 업데이트 )

  

○ 병원 소개

*   병원 소개 페이지 구성 / 개요 및 병원 위치 / 찾아오는 방법 ( 지도 api 삽입 )

  

○ 진료 안내

*   진료 안내 페이지 접속 시 DB 에서 리스트 받아서 데이터 조회 후 제공 리스트 형태로 구현 hover 시 파란색 이미지처럼 표시 후 선택 가능하게
*   위 리스트 형태 구현 후 선택하면 해당 진료 과목 예약 페이지로 이동
*   진료 예약 가능한 시간 테이블만 선택 가능
*   예약 시 중복 예약 불가능 처리 / 예약된 시간은 선택 불가능 처리
*   예약하기

  

○ 고객센터

*   공지사항 및  1:1 문의 메뉴
*   비회원 1:1 문의 접근 시 로그인 페이지로 / redirect
*   1:1 문의는 비공개 처리

  

○건강 정보

*   최근 이슈 건강 정보 내용 업데이트  
*   영상, 파일 및 게시 글
*   관리자는 작성 수정 삭제 가능

  

▶ 관리자 페이지

*   관리자는 바로 로그인 가능 ( 회원 가입 x )
*   전체 회원 정보 조회 가능
*   회원 1명에 대한 정보 전체 조회 가능
*   회원 정보로 검색 조회 가능
*   공지사항 작성 수정 삭제 가능
*   비회원 예약 확인 가능
*   비회원 정보 조회 검색 조회 가능
*   모든 문의 내용 조회 가능
*   1:1문의 답변 가능
*   1:1 문의는 비밀 글로 작성되며 댓글도 해당 1:1문의 작성자만 확인 가능
*   댓글 전체 조회 및 삭제 가능
*   전체 예약 현황 관리 가능
*   병원 휴무일 설정 가능 ( 휴무 일은 예약 불가 )
*   건강 정보 내용 등록 수정 삭제 가능
*   모든 페이지에 필요한 버튼 활성화해 주기

  

▶ 회원

*   회원 정보 수정 가능 (상세 페이지)  
*   회원은 병원 전반 시스템 이용 가능
*   회원 상세에서 본인 예약 현황 관리 가능
*   예약 취소 가능

# backend

### 웹 사이트

  

메인 페이지와 메뉴 구성 및 전반적인 정적, 동적 페이지 구현

### Language

  

[![](https://camo.githubusercontent.com/7caa67f6d2fb3d8e74ab2f8790bd7990505cbc6635332c716cbf8abd193ea154/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4a6176612d3030373339363f7374796c653d666c61742d737175617265266c6f676f436f6c6f723d7768697465)](https://camo.githubusercontent.com/7caa67f6d2fb3d8e74ab2f8790bd7990505cbc6635332c716cbf8abd193ea154/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4a6176612d3030373339363f7374796c653d666c61742d737175617265266c6f676f436f6c6f723d7768697465)

  

### DataBase

  

[![](https://camo.githubusercontent.com/a4bec6acac0346481d8de167d0cbecba0d9a8e1b0a9f823a084770edced1796d/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4d7953514c2d3434373941313f7374796c653d666c61742d737175617265266c6f676f3d4d7953514c266c6f676f436f6c6f723d7768697465)](https://camo.githubusercontent.com/a4bec6acac0346481d8de167d0cbecba0d9a8e1b0a9f823a084770edced1796d/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4d7953514c2d3434373941313f7374796c653d666c61742d737175617265266c6f676f3d4d7953514c266c6f676f436f6c6f723d7768697465)

  

### Framework / Library

## [![](https://camo.githubusercontent.com/eae780e0e821dca1158b8373ffdfaa3afeed9df8e3c06824ade69bad78336ce2/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e672d3644423333463f7374796c653d666c61742d737175617265266c6f676f3d537072696e67266c6f676f436f6c6f723d7768697465)](https://camo.githubusercontent.com/eae780e0e821dca1158b8373ffdfaa3afeed9df8e3c06824ade69bad78336ce2/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e672d3644423333463f7374796c653d666c61742d737175617265266c6f676f3d537072696e67266c6f676f436f6c6f723d7768697465) [![](https://camo.githubusercontent.com/095e1d30fe0b137feed0fdddcc5385279df27a6945fac4f8a8b61c6e29c7545d/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e675f426f6f742d3644423333463f7374796c653d666c61742d737175617265266c6f676f3d737072696e672d626f6f74266c6f676f436f6c6f723d7768697465)](https://camo.githubusercontent.com/095e1d30fe0b137feed0fdddcc5385279df27a6945fac4f8a8b61c6e29c7545d/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e675f426f6f742d3644423333463f7374796c653d666c61742d737175617265266c6f676f3d737072696e672d626f6f74266c6f676f436f6c6f723d7768697465) [![](https://camo.githubusercontent.com/77c90006c9b50f03713951454fcfe563635174c69cb9e89221868dbea99a4106/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e675f53656375726974792d3644423333463f7374796c653d666c61742d737175617265266c6f676f3d537072696e672d5365637572697479266c6f676f436f6c6f723d7768697465)](https://camo.githubusercontent.com/77c90006c9b50f03713951454fcfe563635174c69cb9e89221868dbea99a4106/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f537072696e675f53656375726974792d3644423333463f7374796c653d666c61742d737175617265266c6f676f3d537072696e672d5365637572697479266c6f676f436f6c6f723d7768697465)

  

## Contents

  

★Spring Security 활용

*   스프링 시큐리티 설정
*   SecurityFilterChain 정의
*   /홈 /메인 /로그인 및 /회원가입 /공지사항 /진료안내 /건강정보 전체 접근 허용
*    관리자는 유저 정보 접근 허용
*   BCryptPasswordEncoder 활용 패스워드 암호화
*   AuthenticationManager 활용
*   메인 페이지

  

▶ 회원 가입

*   회원 가입
*   아이디 중복 확인 로직
*   중복 시 에러 로직
*   회원 가입 입력 정보를 바탕으로 회원 가입 요청 처리

  

▶ 로그인 시 발급해 줄 JWT 관련 로직 작성

*   Import io.jsonwebtoken활용  
*   Token validate 확인 로직, 에러
*   Token Expired 확인 로직, 에러
*   JWT 발급, access 토큰 및 refresh토큰 발급, 에러
*   Response header에 토큰 전달

  

▶ 로그인

*   아이디 비밀번호 확인 로직, 에러
*   로그인 처리시 JWT 발급 로직, 에러
*   로그인 완료 리턴

  

▶ 고객센터

*   공지사항 전체 리스트 출력
*   공지사항 검색 리스트 출력 “ 키워드 ”  
*   공지사항CRUD 관리자용 ( ROLE\_ADMIN 확인 )

  

▶ 1:1 문의

*   회원 토큰 인증 로직, 에러
*   토큰으로 회원 아이디 및 1:1 문의 내용 출력
*   문의내용CRUD

  

▶ 진료 안내

*   회원 / 비회원 은 예약 테이블에 회원 , 비회원 여부 삽입
*   예약 가능 테이블 조회
*   예약 로직, 에러
*   성공 시 예약 완료 상태 업데이트 로직

  

▶ 건강 정보

*   관리자 CRUD  파일 및 동영상, 게시글 / 로직
*   회원 댓글 CRUD

  

▶ 회원

*   회원 정보 수정 로직 (상세 페이지)  
*   회원 상세에서 본인 예약 현황 관리 로직  

  

  

▶ 관리자 페이지

  

*   관리자는 바로 로그인가능 ( 회원가입 x )
*   전체 회원 정보 조회 로직
*   회원 1명에 대한 정보 전체 조회 로직
*   회원 정보로 검색 조회 로직
*   공지사항 작성 수정 삭제 로직
*   비회원 예약 확인 로직
*   비회원 정보 조회 검색 조회 로직
*   모든 1:1문의 내용 조회 로직
*   1:1문의 답변 로직
*   댓글 전체 조회 및 삭제 로직
*   전체 예약 현황 관리 로직
*   병원 휴무일 설정 로직 ( 휴무일은 예약 불가 )
*   건강 정보 내용 등록 수정 삭제 로직

# EndPoint

## 웹 사이트 URL

## USER

  

![](https://t9018789117.p.clickup-attachments.com/t9018789117/362c2964-322c-41ac-b45f-efe500fedd1e/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202025-01-06%20173953.png)

  

## ADMIN /admin

![](https://t9018789117.p.clickup-attachments.com/t9018789117/2affe73f-43e5-4ae6-bc0f-de70b2166cb5/%ED%99%94%EB%A9%B4%20%EC%BA%A1%EC%B2%98%202025-01-06%20174010.png)

# WorkFlow

## User![](https://t9018789117.p.clickup-attachments.com/t9018789117/2ef6928c-9009-4351-97cb-789b4e73fc87/userWorkFlow.drawio.png)

  

## ADMIN
![adminWorkFlow drawio](https://github.com/user-attachments/assets/c3ed3697-f8d5-4ea5-ad94-d664e43c3ae8)



# ER 다이어그램

![ERD관계 drawio](https://github.com/user-attachments/assets/2b41b644-01ee-4b08-8a73-23e3624f3239)
