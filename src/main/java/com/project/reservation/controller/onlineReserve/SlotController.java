package com.project.reservation.controller.onlineReserve;

import com.project.reservation.dto.response.reservation.ResSlotList;
import com.project.reservation.service.onlineReserve.timeSlot.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/slot")
public class SlotController {

    private final SlotService slotService;

    @GetMapping
    public ResponseEntity<List<ResSlotList>> getSlotList(@RequestParam(name = "departmentName") String departmentName, @RequestParam(name = "date")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        List<ResSlotList> slotList = slotService.getSlotListOfDateAndDepartment(departmentName, date);
        return ResponseEntity.status(HttpStatus.CREATED).body(slotList);
    }
}
