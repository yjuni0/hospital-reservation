package com.project.reservation.repository.member;

import com.project.reservation.entity.member.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    Optional<Pet> findByName(String name);

    List<Pet> findByMember_Id(Long memberId);
}
