package com.example.project.repository;

import com.example.project.model.AppUser;
import com.example.project.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Iterable<Post> getAllByAppUserIs(AppUser user);

    Iterable<Post> getAllByTag(String tag);

    Iterable<Post> getAllByTagIsAndAndAppUserIs(String tag, AppUser user);

    Iterable<Post> getAllByContentContaining(String searchContent);
}
