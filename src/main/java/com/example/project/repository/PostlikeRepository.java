package com.example.project.repository;

import com.example.project.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PostlikeRepository extends JpaRepository<PostLike,Integer> {
}
