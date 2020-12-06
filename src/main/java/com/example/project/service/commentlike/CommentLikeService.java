package com.example.project.service.commentlike;

import com.example.project.model.CommentLike;
import com.example.project.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService implements ICommentLikeService {

    @Autowired
    private CommentLikeRepository commentLikeRepository;


    @Override
    public Iterable<CommentLike> findAll() {
        return commentLikeRepository.findAll();
    }

    @Override
    public CommentLike findById(Integer id) {
        return commentLikeRepository.findById(id).get();
    }

    @Override
    public void update(CommentLike model) {
        commentLikeRepository.save(model);
    }

    @Override
    public void save(CommentLike model) {
        commentLikeRepository.save(model);
    }

    @Override
    public void remove(Integer id) {
        commentLikeRepository.deleteById(id);
    }
}
