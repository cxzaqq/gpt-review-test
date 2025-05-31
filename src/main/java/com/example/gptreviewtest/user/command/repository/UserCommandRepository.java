package com.example.gptreviewtest.user.command.repository;

import com.example.gptreviewtest.user.command.aggregate.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCommandRepository extends JpaRepository<UserEntity, String> {
}
