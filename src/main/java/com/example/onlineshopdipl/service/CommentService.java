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


    public CommentService(CommentRepository commentRepository, AdsRepository adsRepository, CommentMapper commentMapper, UserService userService) {
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
        this.commentMapper = commentMapper;
        this.userService = userService;
    }

    /**
     * Creating of new comment.
     *
     * @param pk identification number of an ad
     * @param commentDto {@link CommentDto} from a client
     * @param authentication {@link Authentication} instance from controller
     * @return {@link CommentDto} instance of created {@link Comment}
     */

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

    /**
     * Receive all comments for Ads by Ads id.
     *
     * @param adPk identification number of an ad
     * @return {@link ResponseWrapperComment} instance with number of founded comments and List of {@link CommentDto}
     */
    public ResponseWrapperComment getAllCommentsByAd(Integer adPk) {
        Ads ads = adsRepository.findByPk(adPk);
        List<Comment> allComments = commentRepository.findCommentsByAds_Pk(adPk);
        ResponseWrapperComment wrapperComment = new ResponseWrapperComment();

            wrapperComment.setCount(allComments.size());
            wrapperComment.setResults(commentMapper.toListDto(allComments));

        return wrapperComment;
    }

    /**
     * Delete comment from DB by id.
     * The repository method {@link CommentRepository#delete(Object)} is used.
     *
     * @param adPk identification number of an ad
     * @param pk identification number of a comment
     * @param authentication {@link Authentication} instance from controller
     */
    public void deleteComment(Authentication authentication, Integer adPk, Integer pk) {
        Optional<Comment> commentOptional = commentRepository.findByPkAndPk(adPk, pk);
         commentOptional.ifPresent(comment -> {
           userService.checkUserHaveRights(authentication, comment.getUser().getUsername());
       });
        commentOptional.ifPresent((commentRepository::delete));

    }

    /**
     * Receive comment by comment id with checking by ads id in DB, then update comment.
     *
     * @param adPk identification number of an ad
     * @param pk identification number of a comment
     * @param commentDto {@link CommentDto} from a client
     * @param authentication {@link Authentication} instance from controller
     * @return comment update
     */
    public CommentDto updateComments(CommentDto commentDto, Integer adPk, Integer pk, Authentication authentication) {
        Comment comment = commentRepository.findByPk(pk);
            userService.checkUserHaveRights(authentication, comment.getUser().getUsername());
            comment.setText(commentDto.getText());
            commentRepository.save(comment);
        return commentMapper.toDTO(comment);

    }

    /**
     * Get comment by Ads id and comment id.
     *
     * @param adPk identification number of an ad
     * @param pk identification number of a comment
     * @return {@link CommentDto} instance
     */

    public CommentDto getComments_1(Integer adPk, Integer pk) {
        Optional<Comment> commentOptional = commentRepository.findByPkAndPk(adPk, pk);
        return commentOptional
                .map(commentMapper::toDTO)
                .orElse(null);
    }
}
