package com.example.project.model;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "postcomment")
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postcommentID")
    private int postCommentID;

    @Column(name = "coment",columnDefinition = "longtext")
    private String comment;

 @CreationTimestamp
    @Column(name = "datecomment")
    private Date date;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "userID")
    private AppUser user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "postID")
    private Post post;

    //    //commentLike
    @OneToMany(mappedBy = "postComment",cascade = CascadeType.ALL)
    private Set<CommentLike> likes;


    public int getPostCommentID() {
        return postCommentID;
    }

    public void setPostCommentID(int postCommentID) {
        this.postCommentID = postCommentID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AppUser getUser() {
        return user;
    }

    @Transactional
    public void setUser(AppUser user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    @Transactional
    public void setPost(Post post) {
        this.post = post;
    }

    public Set<CommentLike> getLikes() {
        return likes;
    }

    public void setLikes(Set<CommentLike> likes) {
        this.likes = likes;
    }
}
