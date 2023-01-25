package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.entity.Comment;
import com.example.onlineshopdipl.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment findByPk(Integer pk) {
        return commentRepository.findByPk(pk).get();
    }
}
