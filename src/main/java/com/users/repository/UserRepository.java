package com.users.repository;

import com.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findAllByEmail(String email);
}
