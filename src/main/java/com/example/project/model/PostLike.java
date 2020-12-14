package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "postlikes")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postlikeid")
    private int postLikeID;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "userID")
    private AppUser user;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "postID")
    private Post post;


    public PostLike() {
    }

    public PostLike(int postLikeID, AppUser user, Post post) {
        this.postLikeID = postLikeID;
        this.user = user;
        this.post = post;
    }

    public int getPostLikeID() {
        return postLikeID;
    }

    public void setPostLikeID(int postLikeID) {
        this.postLikeID = postLikeID;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
