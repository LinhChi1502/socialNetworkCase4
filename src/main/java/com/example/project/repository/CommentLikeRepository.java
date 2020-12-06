package com.example.project.repository;

import com.example.project.model.CommentLike;
import org.springframework.data.repository.CrudRepository;

public interface CommentLikeRepository extends CrudRepository<CommentLike, Integer> {
}
