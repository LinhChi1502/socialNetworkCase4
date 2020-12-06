package com.example.project.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="postID")
    private int postID;
    @Column(name = "title")
    private String title;
    @Column(name = "status")
    private boolean status;
    @Column(name = "date")
    private Date date;
    @Column(name = "imageURL")
    private String imageUrl;
    @Transient
    private MultipartFile image;

    //nhieu post co 1 user
    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "userID",nullable = false)
    private Users users;

    //postlike
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<PostLike> post;



    public Posts() {
    }

    public Posts(int postID, String title, boolean status, Date date, String imageUrl, MultipartFile image, Users users) {
        this.postID = postID;
        this.title = title;
        this.status = status;
        this.date = date;
        this.imageUrl = imageUrl;
        this.image = image;
        this.users = users;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Set<PostLike> getPost() {
        return post;
    }

    public void setPost(Set<PostLike> post) {
        this.post = post;
    }
}
