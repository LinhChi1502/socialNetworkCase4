package com.example.project.service.post;

import com.example.project.model.*;
import com.example.project.repository.FriendshipRepository;
import com.example.project.repository.PostRepository;
import com.example.project.service.friendship.FriendshipService;
//import javafx.geometry.Pos;
import com.example.project.service.hashtag.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Lists;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostService implements IPostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private HashtagService hashtagService;

    @Override
    public Iterable<Post> findAll() {
        Iterable<Post> posts = postRepository.findAll();
        return posts;
    }

    @Override
    public Post findById(Integer id) {
        Post post = postRepository.findById(id).get();
        return post;
    }


    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public void remove(Integer id) {
        postRepository.deleteById(id);
    }

    @Override
    public Iterable<Post> getAllByAppUserIs(AppUser user) {
        return postRepository.getAllByAppUserIs(user);
    }

    @Override
    public List<Post> findAllByFriendAndUser(AppUser user) {
        List<Post> allPost = new ArrayList<Post>();
        Iterable<AppUser> friendList = friendshipService.findUserFriendByOtherUser(user);
        for (AppUser friend: friendList
        ) {
            List<Post> friendPosts;
            Iterable<Post> iterablePosts = this.getAllByAppUserIs(friend);
            friendPosts = StreamSupport.stream(iterablePosts.spliterator(), true).collect(Collectors.toList());
            allPost.addAll(friendPosts);
        }
        List<Post> userPosts;
        Iterable<Post> iterable = this.getAllByAppUserIs(user);
        userPosts = StreamSupport.stream(iterable.spliterator(), true).collect(Collectors.toList());
        allPost.addAll(userPosts);

        Collections.sort(allPost, new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });
        return allPost;
    }

    @Override
    public Iterable<Post> getAllPostByContentContaining(String searchContent) {
        return postRepository.getAllByContentContaining(searchContent);
    }

    @Override
    public List<Post> findAllPostByTag(String name) {
        List<Post> allPost = postRepository.findAll();
        List<Post> posts = new ArrayList<>();
        for (Post post:
                allPost) {
            Set<Hashtag> tags = post.getTags();
            for (Hashtag hashtag:
                    tags) {
                if (hashtag.getName().equals(name)) {
                    posts.add(post);
                }
            }
        }
        return posts;
    }

    @Override
    public List<Post> findAllPostByTagIsAndAppUserIs(String name, AppUser appUser) {
        Iterable<Post> allPost = this.getAllByAppUserIs(appUser);
        List<Post> posts = new ArrayList<>();
        for (Post post:
                allPost) {
            Set<Hashtag> tags = post.getTags();
            for (Hashtag hashtag:
                    tags) {
                if (hashtag.getName().equals(name)) {
                    posts.add(post);
                }
            }
        }
        return posts;
    }

}
