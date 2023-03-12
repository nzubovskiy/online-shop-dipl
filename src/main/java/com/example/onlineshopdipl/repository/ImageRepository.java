package com.example.onlineshopdipl.repository;

import com.example.onlineshopdipl.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    Optional<Image> findByUserId(Integer userId);
}
