package com.example.project.service.post;

import com.example.project.model.AppUser;
import com.example.project.model.Post;
import com.example.project.model.PostLike;
import com.example.project.service.IService;

import java.util.List;
import java.util.Set;

public interface IPostService extends IService<Post> {
    Iterable<Post> getAllByAppUserIs(AppUser user);

    List<Post> findAllByFriendAndUser(AppUser user);




    Iterable<Post> getAllPostByContentContaining(String searchContent);

    List<Post> findAllPostByTag(String name);
    List<Post> findAllPostByTagIsAndAppUserIs(String name, AppUser appUser);

    Iterable<Post> getAllPostByStatusIsAndAppUserIs(boolean status, AppUser user);

}
