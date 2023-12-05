// EquipSpecification.java

package com.yxy.experimentmanagementserver.specification;

import org.springframework.data.jpa.domain.Specification;
import com.yxy.experimentmanagementserver.model.EquipModel;
import org.springframework.web.bind.annotation.RequestParam;

public class EquipSpecification {

    public static Specification<EquipModel> getEquipSpecification(
            Long id, String name, String category, String status) {
        return (root, query, criteriaBuilder) -> {
            // 在这里根据传入的条件构建动态查询
            // 以下是一个简单的示例，你可以根据实际需求进行调整
            return criteriaBuilder.and(
                    // 例如：根据 id 等于传入的 id 进行查询
                    criteriaBuilder.equal(root.get("id"), id),
                    // 根据 name 等于传入的 name 进行查询
                    criteriaBuilder.equal(root.get("name"), name),
//                    String category,
//                    String status,
//                    String nameLike,
//                    String categoryLike,
//                    String statusLike,
                    criteriaBuilder.equal(root.get("status"), status),
                    criteriaBuilder.equal(root.get("category"), category)
            );
        };
    }
}
