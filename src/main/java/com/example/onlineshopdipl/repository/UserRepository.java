package com.example.onlineshopdipl.repository;

import com.example.onlineshopdipl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM users", nativeQuery = true)
    Collection<User> getAll();
    @NonNull
    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    Optional<User> findById(Integer id);

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    User findByEmail(String username);

    @Query(value = "SELECT * FROM users WHERE password =:password", nativeQuery = true)
    User getUserByPassword(String password);
}
