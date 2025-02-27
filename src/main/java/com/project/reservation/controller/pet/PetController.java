package com.project.reservation.controller.pet;

import com.project.reservation.dto.request.pet.ReqPet;
import com.project.reservation.dto.response.pet.ResPet;
import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.member.Pet;
import com.project.reservation.service.pet.PetService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pet")

public class PetController {

    private final PetService petService;

    @GetMapping("/list")
    public ResponseEntity<List<ResPet>> getPets(@AuthenticationPrincipal Member member) {
        List<ResPet> pets = petService.getPets(member);
        return ResponseEntity.ok(pets);
    }

    @PatchMapping("/update")
    public ResponseEntity<List<ResPet>> updatePetProfiles(@AuthenticationPrincipal Member member, @RequestBody List<ReqPet> reqPets) {
        List<ResPet> updatePets = petService.updatePetProfiles(member, reqPets);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatePets);
    }

}
