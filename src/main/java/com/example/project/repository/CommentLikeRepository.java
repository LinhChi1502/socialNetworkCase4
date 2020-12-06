package com.example.project.repository;

import com.example.project.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Integer> {
}
