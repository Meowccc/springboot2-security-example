package com.example.demo.repository;

import com.example.demo.entity.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author meow
 */
@Repository
public interface UserRepo extends JpaRepository<UserDO, String> {

    Optional<UserDO> findByUsername(String username);
}
