package org.example.datn_website_best.service;

import org.example.datn_website_best.Enum.Status;
import org.example.datn_website_best.dto.request.TargetUserRequest;
import org.example.datn_website_best.dto.response.TargetUserResponse;
import org.example.datn_website_best.model.TargetUser;
import org.example.datn_website_best.repository.TargetUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TargetUserService {

    @Autowired
    TargetUserRepository targetUserRepository;

    public List<TargetUserResponse> findAllTargetUser() {
        return targetUserRepository.findAllTargetUser();
    }

    public List<TargetUserResponse> findByStatus() {
        return targetUserRepository.findByStatus(Status.ACTIVE.toString());
    }

    public TargetUser createTargetUser(TargetUserRequest targetUserRequest) {
        Optional<TargetUser> targetUser = targetUserRepository.findByName(targetUserRequest.getName());
        if (targetUser.isPresent()) {
            throw new RuntimeException("Đối tượng " + targetUserRequest.getName() + " đã tồn tại");
        }
        return targetUserRepository.save(convertTargetUserRequestDTO(targetUserRequest));
    }

    public TargetUser updateStatus(Long id, boolean aBoolean) {
        Optional<TargetUser> targetUser = targetUserRepository.findById(id);
        if (!targetUser.isPresent()) {
            throw new RuntimeException("Không tìm thấy tài nguyên đế trong hệ thống!");
        }
        String newStatus = aBoolean ? Status.ACTIVE.toString() : Status.INACTIVE.toString();
        targetUser.get().setStatus(newStatus);

        return targetUserRepository.save(targetUser.get());


    }

    public TargetUser convertTargetUserRequestDTO(TargetUserRequest TargetUserRequest) {
        TargetUser targetUser = TargetUser.builder()
                .name(TargetUserRequest.getName())
                .build();
        targetUser.setStatus(Status.ACTIVE.toString());
        return targetUser;
    }

}
