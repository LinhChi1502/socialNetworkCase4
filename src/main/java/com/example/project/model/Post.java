package com.example.project.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="postID")
    private int postID;
    @Column(name = "content", columnDefinition = "longtext")
    private String content;
    @Column(name = "status")
    private boolean status;
    @UpdateTimestamp
    @Column(name = "date")
    private Date date;
    @Column(name = "imageURL")
    private String imageUrl;

    @Transient
    private MultipartFile image;

    //nhieu post co 1 user
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "userID",nullable = false)
    private AppUser appUser;

    //postlike
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<PostLike> post;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "post_tags",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private Set<Hashtag> tags;

    public Post() {
    }

    public Post(int postID, String content, boolean status, Date date, String imageUrl, MultipartFile image, AppUser appUser, Set<PostLike> post, Set<Hashtag> tags) {
        this.postID = postID;
        this.content = content;
        this.status = status;
        this.date = date;
        this.imageUrl = imageUrl;
        this.image = image;
        this.appUser = appUser;
        this.post = post;
        this.tags = tags;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Set<PostLike> getPost() {
        return post;
    }

    public void setPost(Set<PostLike> post) {
        this.post = post;
    }

    public Set<Hashtag> getTags() {
        return tags;
    }

    public void setTags(Set<Hashtag> tags) {
        this.tags = tags;
    }
}
