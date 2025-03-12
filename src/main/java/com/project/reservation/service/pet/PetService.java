package com.project.reservation.service.pet;
import com.project.reservation.dto.request.pet.ReqPet;
import com.project.reservation.dto.response.pet.ResPet;

import com.project.reservation.entity.member.Member;
import com.project.reservation.entity.member.Pet;
import com.project.reservation.repository.member.MemberRepository;
import com.project.reservation.repository.member.PetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;

    public List<ResPet> getPets(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다"));

        return member.getPets().stream()
                .map(ResPet::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ResPet> updatePetProfiles(Long memberId, List<ReqPet> reqPets) {

        // 1. 현재 멤버의 모든 펫을 existingPets 로 대입
        List<Pet> existingPets = petRepository.findByMember_Id(memberId);
        log.info("펫 수정1");
        // 2. 요청된 펫 ID 목록 생성
        Set<Long> requestedPetIds = reqPets.stream()
                .map(ReqPet::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        log.info("펫 수정2");

        // 3. 요청에 포함되지 않은 펫 삭제
        existingPets.stream()
                .filter(pet -> !requestedPetIds.contains(pet.getId()))
                .forEach(pet -> petRepository.delete(pet));
        log.info("펫 수정3");

        // 4. 사용자 엔티티 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원 없음"));
        log.info("펫 수정4");

        // 5. 펫 정보 업데이트 또는 새로운 펫 추가
        List<Pet> updatedPets = reqPets.stream()
                .map(reqPet -> {
                    if (reqPet.getId() != null) {
                        // 기존 펫 업데이트
                        return petRepository.findById(reqPet.getId())
                                .map(pet -> {
                                    pet.updatePet(reqPet.getName(), reqPet.getBreed(), reqPet.getAge());
                                    petRepository.save(pet);
                                    return pet;
                                })
                                .orElseThrow(() -> new RuntimeException("해당 id의 펫이 없습니다. " + reqPet.getId()));
                    } else {
                        // 새로운 펫 추가
                        Pet newPet = Pet.builder()
                                .name(reqPet.getName())
                                .breed(reqPet.getBreed())
                                .age(reqPet.getAge())
                                .member(member)
                                .build();
                        return petRepository.save(newPet);
                    }
                })
                .collect(Collectors.toList());
        log.info("펫 수정5");

        return updatedPets.stream()
                .map(ResPet::fromEntity)
                .collect(Collectors.toList());
    }
}