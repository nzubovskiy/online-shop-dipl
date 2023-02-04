package com.example.onlineshopdipl.repository;

import com.example.onlineshopdipl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @NonNull
    Optional<User> findById(Integer id);

    User findByEmail(String userLogin);

    User getUserByPassword(String currentPassword);
}
