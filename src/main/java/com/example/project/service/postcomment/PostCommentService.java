package com.example.project.service.postcomment;

import com.example.project.model.PostComment;
import com.example.project.repository.PostCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostCommentService implements IPostCommnentService {
    @Autowired
    private PostCommentRepository postCommentRepository;

    @Override
    public Iterable<PostComment> findAll() {
        return postCommentRepository.findAll();
    }

    @Override
    public PostComment findById(Integer id) {
        return postCommentRepository.findById(id).get();
    }



    @Override
    public void save(PostComment model) {
        postCommentRepository.save(model);
    }

    @Override
    public void remove(Integer id) {
        postCommentRepository.deleteById(id);

    }
}
