package com.example.onlineshopdipl.repository;

import com.example.onlineshopdipl.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByPk(Integer adPk);

    Comment findCommentsByAds_Pk(Integer pk);

    Optional<Comment> findByPkAndPk(Integer adPk, Integer pk);
    //Comment findByPkAndPk(Integer adPk, Integer pk);
}
