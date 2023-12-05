package com.yxy.experimentmanagementserver.repository;

import com.yxy.experimentmanagementserver.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminModel, Long> {
    AdminModel findByPassword(String password);

    // 您可以在这里定义其他查询方法，Spring Data JPA将自动生成相应的SQL查询
    // 例如，通过用户名查询管理员的方法：
//    AdminModel findByUsername(String username);
}
