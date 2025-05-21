package com.example.gptreviewtest.user.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "department")
public class DepartmentEntity {

    @Id
    @Column(name = "dept_code", length = 50)
    private String deptCode;

    @Column(name = "name", nullable = false, length = 100)
    private String name;
}
