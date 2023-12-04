package com.yxy.experimentmanagementserver.repository;

import com.yxy.experimentmanagementserver.model.EquipModel;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface EquipRepository extends JpaRepository<EquipModel, Long> {

    // 这里可以定义一些自定义的查询方法，Spring Data JPA 会根据方法名自动生成 SQL 查询语句
    // 例如，根据设备名称查询
    Page<EquipModel> findAll(Pageable pageable);

//    search
    List<EquipModel> findByIdOrNameOrCategoryOrStatus(Long id, String name, String category, String status);

    // 模糊搜索示例
    List<EquipModel> findByIdOrNameOrCategoryOrStatusOrNameContainingOrCategoryContainingOrStatusContaining(
            Long id, String name, String category, String status, String nameLike, String categoryLike, String statusLike
    );
}
