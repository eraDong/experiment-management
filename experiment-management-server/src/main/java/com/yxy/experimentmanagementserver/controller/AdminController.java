package com.yxy.experimentmanagementserver.controller;

import com.yxy.experimentmanagementserver.model.AdminModel;
import com.yxy.experimentmanagementserver.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminRepository adminRepository;


    @Autowired
    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;

    }

    @PostMapping("/login")
    public ResponseEntity<String> adminLogin(@RequestBody Map<String, String> requestBody) {
        String password = requestBody.get("password");
        AdminModel existingAdmin = adminRepository.findByPassword(password);

        if (existingAdmin != null) {
            // Administrator authentication successful
            return ResponseEntity.ok("{\"status\": \"success\", \"message\": \"Login successful!\"}");
        } else {
            // Authentication failed, return appropriate response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"status\": \"error\", \"message\": \"Login failed. Check your credentials.\"}");
        }
    }

}