package com.example.project.repository;

import com.example.project.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PostCommentRepository extends JpaRepository<PostComment,Integer> {
}
