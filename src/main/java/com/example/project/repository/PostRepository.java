package com.example.project.repository;

import com.example.project.model.AppUser;
import com.example.project.model.Hashtag;
import com.example.project.model.Post;
import com.example.project.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Iterable<Post> getAllByAppUserIs(AppUser user);

    Iterable<Post> getAllByContentContaining(String searchContent);
}
