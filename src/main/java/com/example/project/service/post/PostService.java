package com.example.project.service.post;

import com.example.project.model.Post;
import com.example.project.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {
    @Autowired
    private PostRepository postRepository;
    @Override
    public Iterable<Post> findAll() {
        Iterable<Post> posts = postRepository.findAll();
        return posts;
    }

    @Override
    public Post findById(Integer id) {
        Post post= postRepository.findById(id).get();
        return post;
    }



    @Override
    public void save(Post model) {

    }

    @Override
    public void remove(Integer id) {

        postRepository.deleteById(id);

    }
}
