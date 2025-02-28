package com.project.reservation.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// Serializable 인터페이스 구현. 현재 클래스의 객체를 직렬화한다.
@Component
public class JwtTokenUtil implements Serializable {


    // serialVersionUID 는 개발자가 직접 값을 지정하는 비밀키로, 직렬화된 객체를 역직렬화 할때 일치해야 함.
    private static final long serialVersionUID = -2550185165626007488L;

    // 미리 소스코드에서 분리해놓은 설정파일(프로퍼티나 yml) 에서 " " 에 맞는 데이터를 가져와서 필드에 주입
    @Value("${jwt.tokenExpirationTime}")
    private Integer tokenExpirationTime;

    @Value("${jwt.secret}")
    private String secret ;

    // 1.토큰 생성

    public String generateToken(UserDetails member, String nickName) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",member.getUsername());
        claims.put("roles", member.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
        return doGenerateToken(claims, member.getUsername());
    }

    // doGenerateToken - 실제 토큰 생성 로직 구현. 반환 타입은 String, JWT 토큰 문자열을 반환.
    // userDetails.getUsername()을 호출하여 얻은 값이 String subject 로 전달되며, 이 값이 JWT 의 주체
    // Jwts : JSON Web Tokens (JWT)을 생성하고 파싱하는 데 사용되는 클래스. 이 라이브러리는 JWT 를 쉽게 만들고 검증.
    // new Date() 가 밀리초이기 때문에, 프로퍼티에 초단위로 설정한 tokenExpirationTime 를 밀리초로 변환하기 위해 1000을 곱해서 더함.
    // signWith(SignatureAlgorithm.HS512, secret)는 지정된 알고리즘(HS512)과 비밀 키(secret)로 JWT 에 서명.
    // compact() 는 JWT 를 직렬화하여 최종적으로 하나의 문자열로 반환하는 메서드. JWT 를 URL-safe 문자열로 변환하는 과정.
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //===========================================================================================================
    // 2. getAllClaimsFromToken - 토큰에서 1.헤더, 2.클레임, 3.서명 중에서 -> 2. 클레임만 객체로 추출
    // getAllClaimsFromToken - 토큰에서 모든 클레임 추출. 반환 타입은 Claims. 분석할 JWT 토큰 문자열을 매개변수로.
    // Jwts.parser()를 사용하여 JWT 파서를 생성하고 파서 초기화.
    // setSigningKey(secret) 로 JWT 서명을 검증하기 위해 사용할 비밀 키 설정.
    // parseClaimsJws(token)를 호출하여 주어진 JWT 토큰을 파싱하고 서명을 검증.
    // .getBody() 는 파싱된 JWT 토큰의 클레임 부분을 추출. 반환되는 값은 Claims 객체로, 토큰에 포함된 모든 클레임 정보를 가짐.
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    // getClaimFromToken - 토큰에서 특정 클레임 추출. 반환타입은 제네릭 타입 T.
    // 매개변수는 String token 과 Claims 타입의 입력을 받아, T 타입의 결과를 반환하는 claimsResolver 라는 이름의 함수형 인터페이스
    // 위에서 만든 getAllClaimsFromToken(token) 메서드는 주어진 토큰을 파싱하여 모든 클레임 정보를 추출.
    // 추출된 클레임 정보는 Claims 인터페이스의 claims 이름으로 반환. final 로 불변객체 선언.
    // claimsResolver 는 Claims 객체를 입력으로 받아 T 타입의 결과를 반환하는 함수.
    // claims는 JWT의 페이로드에서 추출된 모든 클레임 정보를 포함하는 객체.
    // apply 메서드는 claimsResolver 함수를 claims 객체에 적용해 특정 클레임 정보를 추출하거나 변환할 수 있다.
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // getUsernameFromToken - String token 을 매개변수로 받아서
    // 위에서 만든 getClaimFromToken 의 (token, Claims::getSubject) 메소드로 subject(사용자 이름) 을 String 으로 반환.
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // getExpirationDateFromToken - String token 을 매개변수로 받아서
    // 위에서 만든 getClaimFromToken 의 (token, Claims::getExpiration) 메소드로 getExpiration(만료시간) 을 Date 로 반환.
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //===========================================================================================================
    // 3. 토큰 유효성 검사

    // isTokenExpired - String token 을 매개변수로 받아서 Boolean 타입을 반환
    // 위에서 만든 getExpirationDateFromToken(token) 메소드를 이용해서 Date 타입의 expiration 이름으로 final 로 불변객체 선언.
    // .before 로 expiration 이 new Date() 현재시간 보다 이전인지 반환. 만료되었으면 true, 아직 만료되지 않았으면 false 반환.
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // validateToken - String token, UserDetails userDetails 를 매개변수로 받아서 Boolean 타입을 반환
    // 위에서 만든 getUsernameFromToken(token) 메소드를 이용해서 String 타입의 username 이름으로 final 불변객체 선언.
    // 1. username.equals(userDetails.getUsername()) - 토큰에서 추출한 사용자 이름이 UserDetails 객체의 사용자 이름과 일치하는지
    // 2. !isTokenExpired(token) - 위에서 만든 isTokenExpired(token) 메소드로 토큰이 만료되지 '않았는지'
    // 확인해서 모두 참일 경우 Boolean true, 아니면 false
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}


//  (봉인) 내가 만들던거 일단 봉인
//    private final String SECRET_KEY = "secret";
//
//    public String generateToken(String email) {
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10시간 유효
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }
//
//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String email = extractEmail(token);
//        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    public String extractEmail(String token) {
//        return extractAllClaims(token).getSubject();
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractAllClaims(token).getExpiration().before(new Date());
//    }
//}