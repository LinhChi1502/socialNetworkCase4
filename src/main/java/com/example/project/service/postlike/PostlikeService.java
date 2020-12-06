package com.example.project.service.postlike;

import com.example.project.model.PostLike;
import com.example.project.repository.PostlikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostlikeService implements IPostlikeService {
    @Autowired
    private PostlikeRepository postlikeRepository;


    @Override
    public Iterable<PostLike> findAll() {
        return postlikeRepository.findAll();

    }

    @Override
    public PostLike findById(Integer id) {
        return postlikeRepository.findById(id).get();
    }

    @Override
    public void update(PostLike model) {
        postlikeRepository.save(model);
    }

    @Override
    public void save(PostLike model) {
        postlikeRepository.save(model);
    }

    @Override
    public void remove(Integer id) {
        postlikeRepository.deleteById(id);
    }
}
