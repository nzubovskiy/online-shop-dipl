package com.example.onlineshopdipl.repository;

import com.example.onlineshopdipl.entity.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {
    Ads findByPk(Integer pk);

    List<Ads> findByUserUsername(String username);//поменяла login на username

    boolean findByTitleAndUserId(String title, Integer id);

    Optional<Ads> findByPkAndUserId(Integer pk, Integer id);

    @Query("select a from Ads a where a.title like %:title%")
    List<Ads> searchByTitle(@Param("title") String title);
}
