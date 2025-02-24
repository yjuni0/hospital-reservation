package com.project.reservation.dto.request.member;

public record ReqMemberFindPw (
        String email,
        String code,
        String newPassword,
        String newPasswordCheck){
}
