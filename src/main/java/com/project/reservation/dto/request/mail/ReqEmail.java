package com.project.reservation.dto.request.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReqEmail {

    private String mail;
    private String verifyCode;
}