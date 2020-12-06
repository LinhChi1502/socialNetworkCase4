package com.example.project.model;

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
    private Users user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "postID")
    private Posts post;


    public PostLike() {
    }

    public PostLike(int postLikeID, Users user, Posts post) {
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

}
