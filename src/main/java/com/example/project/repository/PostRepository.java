package com.example.project.repository;

import com.example.project.model.Posts;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Posts,Integer> {
}
