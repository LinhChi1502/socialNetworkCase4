package com.example.project.repository;

import com.example.project.model.PostComment;
import org.springframework.data.repository.CrudRepository;

public interface PostCommentRepository extends CrudRepository<PostComment,Integer> {
}
