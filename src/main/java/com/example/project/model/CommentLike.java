package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "commentlikes")
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentlikeid")
    private int commentLikeID;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "postcommentID")
    private PostComment postComment;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "userID")
    private AppUser user;


    public CommentLike() {
    }

    public CommentLike(int commentLikeID, PostComment postComment, AppUser user) {
        this.commentLikeID = commentLikeID;
        this.postComment = postComment;
        this.user = user;
    }

    public int getCommentLikeID() {
        return commentLikeID;
    }

    public void setCommentLikeID(int commentLikeID) {
        this.commentLikeID = commentLikeID;
    }

    public PostComment getPostComment() {
        return postComment;
    }

    public void setPostComment(PostComment postComment) {
        this.postComment = postComment;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

}
