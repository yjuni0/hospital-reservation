package com.project.reservation.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)  // 404 반환
public class ReservationException extends RuntimeException {
    public ReservationException(String message) {
        super(message);
    }
}
