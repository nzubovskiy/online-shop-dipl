package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.dto.CommentDto;
import com.example.onlineshopdipl.dto.ResponseWrapperComment;
import com.example.onlineshopdipl.entity.Ads;
import com.example.onlineshopdipl.entity.Comment;
import com.example.onlineshopdipl.entity.User;
import com.example.onlineshopdipl.mapper.CommentMapper;
import com.example.onlineshopdipl.repository.AdsRepository;
import com.example.onlineshopdipl.repository.CommentRepository;
import com.example.onlineshopdipl.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;
    private final UserRepository userRepository;



    public CommentService(CommentRepository commentRepository, AdsRepository adsRepository, CommentMapper commentMapper, UserService userService, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
        this.commentMapper = commentMapper;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public CommentDto addComments(CommentDto commentDto, Integer pk, Authentication authentication) {
        Ads ads = adsRepository.findByPk(pk);
        User user = userService.getUser(authentication.getName());
        Comment comment = commentMapper.toEntity(commentDto);

            comment.setAds(ads);
            comment.setUser(user);
            comment.setCreatedAt(LocalDateTime.now());

            Comment newComment = commentRepository.save(comment);

        return commentMapper.toDTO(newComment);
    }

    public ResponseWrapperComment getAllCommentsByAd(Integer adPk) {
        Ads ads = adsRepository.findByPk(adPk);
        List<Comment> allComments = commentRepository.findCommentsByAds_Pk(adPk);
        ResponseWrapperComment wrapperComment = new ResponseWrapperComment();

            wrapperComment.setCount(allComments.size());
            wrapperComment.setResults(commentMapper.toListDto(allComments));

        return wrapperComment;
    }

    public void deleteComment(Authentication authentication, Integer adPk, Integer pk) {
        Optional<Comment> commentOptional = commentRepository.findByPkAndPk(adPk, pk);
         commentOptional.ifPresent(comment -> {
           userService.checkUserHaveRights(authentication, comment.getUser().getUsername());
       });
        commentOptional.ifPresent((commentRepository::delete));

    }

    public CommentDto updateComments(CommentDto commentDto, Integer adPk, Integer pk, Authentication authentication) {
        Comment comment = commentRepository.findByPk(pk);
            userService.checkUserHaveRights(authentication, comment.getUser().getUsername());
            comment.setText(commentDto.getText());
            commentRepository.save(comment);
        return commentMapper.toDTO(comment);

    }

    public CommentDto getComments_1(Integer adPk, Integer pk) {
        Optional<Comment> commentOptional = commentRepository.findByPkAndPk(adPk, pk);
        return commentOptional
                .map(commentMapper::toDTO)
                .orElse(null);
    }
}
