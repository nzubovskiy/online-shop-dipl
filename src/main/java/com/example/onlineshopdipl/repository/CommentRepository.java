package com.example.onlineshopdipl.repository;

import com.example.onlineshopdipl.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Optional<Comment> findByPk(Integer pk);

    List<Comment> findAllById(Integer adPk);

    Comment findByAdsPkAndPk(Integer adPk, Integer pk);
}
