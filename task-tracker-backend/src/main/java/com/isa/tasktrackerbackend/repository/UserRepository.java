package com.isa.tasktrackerbackend.repository;

import com.isa.tasktrackerbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
