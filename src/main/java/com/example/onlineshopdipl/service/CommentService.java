package com.example.onlineshopdipl.service;

import com.example.onlineshopdipl.dto.CommentDto;
import com.example.onlineshopdipl.entity.Ads;
import com.example.onlineshopdipl.entity.Comment;
import com.example.onlineshopdipl.mapper.CommentMapper;
import com.example.onlineshopdipl.repository.AdsRepository;
import com.example.onlineshopdipl.repository.CommentRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public CommentDto createComment(CommentDto commentDto, Integer pk) {
        Comment comment = new Comment();
        Optional<Ads> ads = adsRepository.findByPk(pk);
        comment = Mappers.getMapper(CommentMapper.class).toEntity(commentDto);

        comment.setPk(ads.get().getPk());
        commentRepository.save(comment);

        return Mappers.getMapper(CommentMapper.class).toDTO(comment);
    }

    public List<CommentDto> getAllCommentsByAd(Integer adPk) {
        List<CommentDto> commentsDto = new ArrayList<>();
        List<Comment> allComents = commentRepository.findAllById(adPk);
        for (Comment comment : allComents) {
            commentsDto.add(Mappers.getMapper(CommentMapper.class).toDTO(comment));
        }
        return commentsDto;
    }

    public void deleteComment(Integer adPk, Integer pk) {
        Comment comment = commentRepository.findByAdsPkAndPk(adPk, pk);
        commentRepository.delete(comment);
    }

    public CommentDto findByAdsPkAndPk(Integer adPk, Integer pk) {
        Comment comment = commentRepository.findByAdsPkAndPk(adPk, pk);
        CommentDto commentDto = Mappers.getMapper(CommentMapper.class).toDTO(comment);
        return commentDto;
    }

    ///
    public CommentDto updateComments(CommentDto commentUpdateDto, Integer adPk, Integer pk) {
        Comment comment = commentRepository.findByAdsPkAndPk(adPk, pk);
        Comment commentUpdate = Mappers.getMapper(CommentMapper.class).toEntity(commentUpdateDto);
        comment.setCreatedAt(commentUpdate.getCreatedAt());
        comment.setPk(pk);
        comment.setText(commentUpdate.getText());
        commentRepository.save(comment);

        return Mappers.getMapper(CommentMapper.class).toDTO(comment);
    }
}
