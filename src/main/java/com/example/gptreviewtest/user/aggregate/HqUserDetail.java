package com.example.gptreviewtest.user.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "hq_user_detail")
public class HqUserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private PositionEntity position;

    @ManyToOne
    @JoinColumn(name = "duty_id")
    private DutyEntity duty;
}
