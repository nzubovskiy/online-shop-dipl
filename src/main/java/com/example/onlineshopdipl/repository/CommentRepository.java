package com.example.onlineshopdipl.repository;

import com.example.onlineshopdipl.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllById(Integer adPk);

    Comment findByAdsPkAndPk(Integer adPk, Integer pk);
}
