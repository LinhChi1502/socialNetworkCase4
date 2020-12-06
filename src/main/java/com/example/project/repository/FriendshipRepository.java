package com.example.project.repository;

import com.example.project.model.Friendship;
import org.springframework.data.repository.CrudRepository;

public interface FriendshipRepository extends CrudRepository<Friendship,Integer> {
}
