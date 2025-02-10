package com.project.reservation.controller;

import com.project.reservation.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
}
