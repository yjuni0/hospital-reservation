package com.project.reservation.service.pet;


import com.project.reservation.dto.request.pet.ReqPet;
import com.project.reservation.dto.response.pet.ResPet;
import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.member.Pet;
import com.project.reservation.repository.member.MemberRepository;
import com.project.reservation.repository.member.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@RequiredArgsConstructor
@Transactional
@Service
public class PetService {

   private final PetRepository petRepository;
    private final MemberRepository memberRepository;

   // 모든 펫 조회
   public List<Pet> findAllPets(Long memberId) {
      return petRepository.findByMember_Id(memberId);
   }

   // ID로 펫 조회
   public Pet findPetById(Long petId) {
      return petRepository.findById(petId).orElseThrow(()-> new IllegalArgumentException("펫이 없음"));
   }

   public Pet petRegister(Long memberId, Pet reqPet) {
      Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
      reqPet.addMember(member);
      petRepository.save(reqPet);
      return reqPet;
   }

   // 펫 정보 수정
   public Pet updatePet(Long petId, Pet pet) {
      Pet upPet = petRepository.findById(petId).orElseThrow(() -> new IllegalArgumentException("Pet not found"));
      upPet.updatePet(pet.getName(),pet.getBreed(),pet.getAge());
      petRepository.save(pet);
      return upPet;
   }
   public void deletePet(Long petId) {
      petRepository.deleteById(petId);
   }
}