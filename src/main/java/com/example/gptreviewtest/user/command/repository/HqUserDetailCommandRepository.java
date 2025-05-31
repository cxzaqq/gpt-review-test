package com.example.gptreviewtest.user.command.repository;

import com.example.gptreviewtest.user.command.aggregate.HqUserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HqUserDetailCommandRepository extends JpaRepository<HqUserDetailEntity, Integer> {
}
