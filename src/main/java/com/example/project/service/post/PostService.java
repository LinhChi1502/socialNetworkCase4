package com.example.project.service.post;

import com.example.project.model.AppUser;
import com.example.project.model.Friendship;
import com.example.project.model.Post;
import com.example.project.repository.FriendshipRepository;
import com.example.project.repository.PostRepository;
import com.example.project.service.friendship.FriendshipService;
import javafx.geometry.Pos;
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
    public Iterable<Post> getAllByTag(String tag) {
        return postRepository.getAllByTag(tag);
    }

    @Override
    public Iterable<Post> getAllPostByTagIsAndAndAppUserIs(String tag, AppUser user) {
        return postRepository.getAllByTagIsAndAndAppUserIs(tag,user);
    }

    @Override
    public Iterable<Post> getAllPostByContentContaining(String searchContent) {
        return postRepository.getAllByContentContaining(searchContent);
    }
}
