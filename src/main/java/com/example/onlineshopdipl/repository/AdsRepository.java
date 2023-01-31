package com.example.onlineshopdipl.repository;

import com.example.onlineshopdipl.entity.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {
    Optional<Ads> findByPk(Integer pk);

    List<Ads> findByUserLogin(String userLogin);
}
