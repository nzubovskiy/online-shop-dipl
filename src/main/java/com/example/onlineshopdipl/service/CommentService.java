package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.dto.CommentDto;
import com.example.onlineshopdipl.dto.ResponseWrapperComment;
import com.example.onlineshopdipl.entity.Ads;
import com.example.onlineshopdipl.entity.Comment;
import com.example.onlineshopdipl.mapper.CommentMapper;
import com.example.onlineshopdipl.repository.AdsRepository;
import com.example.onlineshopdipl.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, AdsRepository adsRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
        this.commentMapper = commentMapper;
    }

    public CommentDto addComments(CommentDto commentDto, Integer pk) {
        Ads ads = adsRepository.findByPk(pk);
        Comment commentEntity = commentMapper.toEntity(commentDto);
        Comment comment = new Comment();
        if (ads == null) {
            return null;
        } else {
            comment.setPk(commentEntity.getPk());
            comment.setCreatedAt(commentEntity.getCreatedAt());
            comment.setText(commentEntity.getText());
            commentRepository.save(comment);
        }
        return commentMapper.toDTO(comment);
    }

    public ResponseWrapperComment getAllCommentsByAd(Integer adPk) {
        List<Comment> allComents = commentRepository.findAllByPk(adPk);
        ResponseWrapperComment wrapperComment = new ResponseWrapperComment();
        if (allComents.isEmpty()) {
            wrapperComment.setResult(Collections.emptyList());
        } else {
            wrapperComment.setCount(allComents.size());
            wrapperComment.setResult(commentMapper.toListDto(allComents));
        }
        return wrapperComment;
    }

    public boolean deleteComment(Integer adPk, Integer pk) {
        Optional<Comment> commentOptional = commentRepository.findByPkAndPk(adPk, pk);
        commentOptional.ifPresent((commentRepository::delete));
        return commentOptional.isPresent();
    }



    public CommentDto updateComments(CommentDto commentUpdateDto, Integer adPk, Integer pk) {
        Ads ads = adsRepository.findByPk(adPk);
        Optional<Comment> commentOptional = commentRepository.findByPkAndPk(adPk, pk);
        commentOptional.ifPresent(comment -> {
            comment.setPk(commentUpdateDto.getPk());
            comment.setCreatedAt(commentUpdateDto.getCreatedAt());
            comment.setText(commentUpdateDto.getText());

            commentRepository.save(comment);
        });
        return commentOptional
                .map(commentMapper::toDTO)
                .orElse(null);
    }

    public CommentDto getComments_1(Integer adPk, Integer pk) {
        Optional<Comment> commentOptional = commentRepository.findByPkAndPk(adPk, pk);
        return commentOptional
                .map(commentMapper::toDTO)
                .orElse(null);
    }
}
