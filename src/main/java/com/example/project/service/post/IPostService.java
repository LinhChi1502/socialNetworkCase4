package com.example.project.service.post;

import com.example.project.model.AppUser;
import com.example.project.model.Post;
import com.example.project.service.IService;

import java.util.List;

public interface IPostService extends IService<Post> {
    Iterable<Post> getAllByAppUserIs(AppUser user);

    List<Post> findAllByFriendAndUser(AppUser user);

    Iterable<Post> getAllByTag(String tag);

    Iterable<Post> getAllPostByTagIsAndAndAppUserIs(String tag, AppUser user);

    Iterable<Post> getAllPostByContentContaining(String searchContent);
}
