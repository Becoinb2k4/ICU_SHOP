package org.example.datn_website_best.controller;

import jakarta.validation.Valid;
import org.example.datn_website_best.dto.request.TargetUserRequest;
import org.example.datn_website_best.dto.response.Response;
import org.example.datn_website_best.model.TargetUser;
import org.example.datn_website_best.service.TargetUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/targetUser")
public class TargetUserRestAPI {

    @Autowired
    TargetUserService targetUserService;

    @GetMapping("/list-targetUser")
    public ResponseEntity<?> findAllTargetUser() {
        try{
            return ResponseEntity.ok(targetUserService.findAllTargetUser());
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list-targetUserActive")
    public ResponseEntity<?> findByStatusActive() {
        try{
            return ResponseEntity.ok(targetUserService.findByStatus());
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list-targetUser-search")
    public ResponseEntity<?> findByStatusSearch(@RequestParam("search") String search) {
        try{
            return ResponseEntity.ok(targetUserService.findByStatus().stream()
                    .filter(TargetUserResponse -> TargetUserResponse.getName().toLowerCase().contains(search.trim().toLowerCase()))
                    .collect(Collectors.toList()));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStatus(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "aBoolean", required = false) boolean aBoolean) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().body(
                        Response.builder()
                                .status(HttpStatus.BAD_REQUEST.toString())
                                .mess("Lỗi: ID loại đối tượng không được để trống!")
                                .build()
                );
            }
            TargetUser targetUser = targetUserService.updateStatus(id, aBoolean);
            return ResponseEntity.ok(targetUser);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Response.builder()
                            .status(HttpStatus.CONFLICT.toString())
                            .mess(e.getMessage())
                            .build()
                    );
        }
    }

    @PostMapping("/create-targetUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createTargetUser(@RequestBody @Valid TargetUserRequest targetUserRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errors = result.getAllErrors().stream()
                        .map(error -> error.getDefaultMessage())
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errors);
            }
            System.out.println(targetUserRequest);
            return ResponseEntity.ok(targetUserService.createTargetUser(targetUserRequest));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Response.builder()
                            .status(HttpStatus.CONFLICT.toString())
                            .mess(e.getMessage())
                            .build()
                    );
        }
    }
}
