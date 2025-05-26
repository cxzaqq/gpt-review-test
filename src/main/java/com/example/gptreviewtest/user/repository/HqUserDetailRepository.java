package com.example.gptreviewtest.user.repository;

import com.example.gptreviewtest.user.aggregate.HqUserDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HqUserDetailRepository extends JpaRepository<HqUserDetailEntity, Integer> {
}
