package com.project.reservation.controller.pet;

import com.project.reservation.dto.request.pet.ReqPet;
import com.project.reservation.dto.response.pet.ResPet;
import com.project.reservation.entity.member.Pet;
import com.project.reservation.service.pet.PetService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member/{memberId}/pet")
public class PetController {
    private final PetService petService;

    @GetMapping
    public ResponseEntity<List<ResPet>> getPetList(@PathVariable("memberId") Long memberId){
        List<ResPet> list = petService.findAllPets(memberId).stream().map(ResPet::fromEntity).toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Pet> addPet(@PathVariable("memberId") Long memberId, @RequestBody Pet pet){
        Pet addPet = petService.petRegister(memberId,pet);
        return ResponseEntity.ok(addPet);
    }

    @PutMapping("/{petId}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long memberId,@PathVariable("petId") Long petId, @RequestBody Pet reqPet){
        Pet updatePet = petService.updatePet(petId,reqPet);
        return ResponseEntity.ok(updatePet);
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable Long memberId, @PathVariable("petId") Long petId){
        petService.deletePet(petId);
        return ResponseEntity.ok().build();
    }


}
