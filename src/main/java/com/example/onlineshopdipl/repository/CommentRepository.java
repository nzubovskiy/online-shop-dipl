package com.example.onlineshopdipl.repository;

import com.example.onlineshopdipl.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByPk(Integer adsPk);

    Comment findByPkAndPk(Integer adsPk, Integer pk);
}
