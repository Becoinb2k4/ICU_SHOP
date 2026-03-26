package org.example.datn_website_best.repository;

import org.example.datn_website_best.dto.response.TargetUserResponse;
import org.example.datn_website_best.model.TargetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TargetUserRepository extends JpaRepository<TargetUser,Long> {
    @Query(value = "SELECT new org.example.datn_website_best.dto.response.TargetUserResponse(s.id, s.name, s.status)FROM TargetUser s")
    List<TargetUserResponse> findAllTargetUser();
    @Query(value = "SELECT new org.example.datn_website_best.dto.response.TargetUserResponse(s.id, s.name, s.status)FROM TargetUser s WHERE s.status=:status")
    List<TargetUserResponse> findByStatus(@Param("status") String status);

    Optional<TargetUser> findByName(String name);
    Optional<TargetUser> findByIdAndStatus(Long id, String status);
}
