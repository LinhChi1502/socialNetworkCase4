package com.example.project.service.post;

import com.example.project.model.Posts;
import com.example.project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {
    @Autowired
    private PostRepository postRepository;
    @Override
    public Iterable<Posts> findAll() {
        Iterable<Posts> posts = postRepository.findAll();
        return posts;
    }

    @Override
    public Posts findById(Integer id) {
        Posts post= postRepository.findById(id).get();
        return post;
    }

    @Override
    public void update(Posts model) {

    }

    @Override
    public void save(Posts model) {

    }

    @Override
    public void remove(Integer id) {

        postRepository.deleteById(id);

    }
}
