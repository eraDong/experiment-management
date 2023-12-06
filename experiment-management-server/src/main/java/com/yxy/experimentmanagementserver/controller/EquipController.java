package com.yxy.experimentmanagementserver.controller;

import com.yxy.experimentmanagementserver.model.EquipModel;
import com.yxy.experimentmanagementserver.repository.EquipRepository;
import com.yxy.experimentmanagementserver.specification.EquipSpecification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
    @RequestMapping("/experiment")
public class EquipController {

    @Autowired
    private EquipRepository equipmentRepository;

    // 获取所有实验设备
    @GetMapping("/equipments")
    public ResponseEntity<Page<EquipModel>> getEquipments(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EquipModel> equipmentPage = equipmentRepository.findAll(pageable);
        return ResponseEntity.ok(equipmentPage);
    }

    // 根据 ID 查找物品属性接口
    @GetMapping("/equipment/{id}")
    public ResponseEntity<EquipModel> getEquipmentById(@PathVariable Long id) {
        return equipmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipment not found with id: " + id));
    }

    // 编辑设备接口
    @PutMapping("/edit-equipment/{id}")
    public ResponseEntity<EquipModel> editEquipment(
            @PathVariable Long id,
            @RequestPart("image") MultipartFile imageFile,
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("status") String status,
            @RequestParam("description") String description
    ) {
        // 根据ID查找要编辑的设备
        Optional<EquipModel> existingEquipmentOptional = equipmentRepository.findById(id);

        if (existingEquipmentOptional.isPresent()) {
            EquipModel existingEquipment = existingEquipmentOptional.get();

            // 更新设备的属性
            existingEquipment.setName(name);
            existingEquipment.setCategory(category);
            existingEquipment.setStatus(status);
            existingEquipment.setDescription(description);

            // 处理文件上传
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    // 获取 uploads/equipImage 目录的路径
                    File uploadsDirectory = new File("uploads/equipImage");
                    if (!uploadsDirectory.exists()) {
                        uploadsDirectory.mkdirs();  // 如果目录不存在，创建目录
                    }

                    String uploadDir = uploadsDirectory.getAbsolutePath();

                    // 构建保存文件的完整路径
                    String fileName = uploadDir + File.separator + imageFile.getOriginalFilename();
                    File dest = new File(fileName);

                    // 保存文件到指定路径
                    imageFile.transferTo(dest);

                    // 设置 EquipModel 对象的 image 属性
                    existingEquipment.setImage("equipImage/" + imageFile.getOriginalFilename());

                } catch (IOException e) {
                    e.printStackTrace();
                    // 处理文件保存失败的异常
                }
            }

            // 保存更新后的设备
            EquipModel savedEquipment = equipmentRepository.save(existingEquipment);

            return ResponseEntity.ok(savedEquipment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @PostMapping("/create-equipment")
    public EquipModel createEquipment(
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("status") String status,
            @RequestParam("description") String description,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        EquipModel equipment = new EquipModel();
        equipment.setName(name);
        equipment.setCategory(category);
        equipment.setStatus(status);
        equipment.setDescription(description);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // 获取 uploads/equipImage 目录的路径
                File uploadsDirectory = new File("uploads/equipImage");
                if (!uploadsDirectory.exists()) {
                    uploadsDirectory.mkdirs();  // 如果目录不存在，创建目录
                }

                String uploadDir = uploadsDirectory.getAbsolutePath();

                // 构建保存文件的完整路径
                String fileName = uploadDir + File.separator + imageFile.getOriginalFilename();
                File dest = new File(fileName);

                // 保存文件到指定路径
                imageFile.transferTo(dest);

                // 设置 EquipModel 对象的 image 属性
                equipment.setImage("equipImage/" + imageFile.getOriginalFilename());

            } catch (IOException e) {
                e.printStackTrace();
                // 处理文件保存失败的异常
            }
        }

        // 在这里可以添加一些验证逻辑，例如验证设备信息是否合法

        // 保存设备信息
        return equipmentRepository.save(equipment);
    }




    // 删除设备接口
    @DeleteMapping("/delete-equipment/{id}")
    public ResponseEntity<String> deleteEquipment(@PathVariable Long id) {
        if (equipmentRepository.existsById(id)) {
            equipmentRepository.deleteById(id);
            return ResponseEntity.ok("Equipment deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment not found with id: " + id);
        }
    }




    @GetMapping("/search")
    public ResponseEntity<Page<EquipModel>> searchEquipments(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<EquipModel> spec = (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (id != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("id"), id));
            }
            if (name != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("name"), name));
            }
            if (category != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("category"), category));
            }
            if (status != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), status));
            }


            return predicate;
        };

        Page<EquipModel> equipmentPage = equipmentRepository.findAll(spec, pageable);

        return ResponseEntity.ok(equipmentPage);
    }



}
