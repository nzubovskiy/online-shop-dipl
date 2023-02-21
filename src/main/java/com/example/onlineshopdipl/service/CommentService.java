package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.dto.CommentDto;
import com.example.onlineshopdipl.dto.ResponseWrapperComment;
import com.example.onlineshopdipl.entity.Ads;
import com.example.onlineshopdipl.entity.Comment;
import com.example.onlineshopdipl.mapper.CommentMapper;
import com.example.onlineshopdipl.repository.AdsRepository;
import com.example.onlineshopdipl.repository.CommentRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;


    public CommentService(CommentRepository commentRepository, AdsRepository adsRepository, CommentMapper commentMapper, UserService userService) {
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
        this.commentMapper = commentMapper;
        this.userService = userService;
    }

    public CommentDto addComments(CommentDto commentDto, Integer pk, Authentication authentication) {
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
        List<Comment> allComments = commentRepository.findAllByPk(adPk);
        ResponseWrapperComment wrapperComment = new ResponseWrapperComment();
        if (allComments.isEmpty()) {
            wrapperComment.setResult(Collections.emptyList());
        } else {
            wrapperComment.setCount(allComments.size());
            wrapperComment.setResult(commentMapper.toListDto(allComments));
        }
        return wrapperComment;
    }

    public void deleteComment(Authentication authentication, Integer adPk, Integer pk) {
        Optional<Comment> commentOptional = commentRepository.findByPkAndPk(adPk, pk);
        commentOptional.ifPresent(comment -> {
            userService.checkUserHaveRights(authentication, comment.getUser().getUsername());
        });
        commentOptional.ifPresent((commentRepository::delete));

    }



    public CommentDto updateComments(CommentDto commentUpdateDto, Integer adPk, Integer pk, Authentication authentication) {
        Ads ads = adsRepository.findByPk(adPk);
        Optional<Comment> commentOptional = commentRepository.findByPkAndPk(adPk, pk);
        commentOptional.ifPresent(comment -> {
            userService.checkUserHaveRights(authentication, comment.getUser().getUsername());
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
