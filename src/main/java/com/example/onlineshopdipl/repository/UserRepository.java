package com.example.onlineshopdipl.repository;

import com.example.onlineshopdipl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer id);

    User findByEmail(String userLogin);

    User getUserByPassword(String currentPassword);
}
