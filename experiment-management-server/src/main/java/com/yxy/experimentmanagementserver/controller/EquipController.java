package com.yxy.experimentmanagementserver.controller;

import com.yxy.experimentmanagementserver.model.EquipModel;
import com.yxy.experimentmanagementserver.repository.EquipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;

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

    // 创建设备接口
    @PostMapping("/create-equipment")
    public EquipModel createEquipment(
            @RequestParam("name") String name,
            @RequestParam("category") String category,
            @RequestParam("status") String status,
            @RequestParam("description") String description
    ) {
        // 创建 EquipModel 对象
        EquipModel equipment = new EquipModel();
        equipment.setName(name);
        equipment.setCategory(category);
        equipment.setStatus(status);
        equipment.setDescription(description);

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


}
