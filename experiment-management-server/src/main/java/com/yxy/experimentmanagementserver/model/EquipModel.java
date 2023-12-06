package com.yxy.experimentmanagementserver.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
public class EquipModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @JsonProperty("id")
    private Long id;

    @JsonProperty(value = "name", required = true)
    private String name;
    @JsonProperty(value = "category", required = true)
    private String category;
    @JsonProperty(value = "status", required = true)
    private String status;
    @JsonProperty("description")
    private String description;

    @JsonProperty("image")
    private String image;  // 新增的属性

//    @JsonProperty("amount")
//    private long amount;

    @Column(name = "createtime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @PrePersist
    public void prePersist() {
        createTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "EquipModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    // 省略其他构造函数和方法...

    // Getter 和 Setter 方法...

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
