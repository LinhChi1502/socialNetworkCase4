package com.example.project.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private int userId;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "avatarURL")
    private String avatarURL;
    @Column(name = "status", nullable = true)
    private boolean status;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "city")
    private String city;
    @Column(name = "gender")
    private String gender;
    @Column(name = "about", columnDefinition = "longtext")
    private String about;
    @Column(name = "phone")
    private int phone;
    @Column(name = "dob")
    private Date dateOfBirth;

    //1 user co nhieu post
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<Post> posts;

    //friendship
    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL)
    private Set<Friendship> user1;
    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL)
    private Set<Friendship> user2;
    @OneToMany(mappedBy = "actionUser", cascade = CascadeType.ALL)
    private Set<Friendship> actionUser;

    //postLike
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<PostLike> postLikes;

    //commentLike
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<CommentLike> likes;

    //role
    @ManyToOne
    private AppRole roll;

    public AppUser() {
    }

    public AppUser(int userId, String userName, String password, String avatarURL, boolean status, String firstName,
                   String lastName, String city, String gender, String about, int phone, Date dateOfBirth,
                   Set<Post> posts, Set<Friendship> user1, Set<Friendship> user2, Set<Friendship> actionUser,
                   Set<PostLike> postLikes, Set<CommentLike> likes, AppRole roll) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.avatarURL = avatarURL;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.gender = gender;
        this.about = about;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.posts = posts;
        this.user1 = user1;
        this.user2 = user2;
        this.actionUser = actionUser;
        this.postLikes = postLikes;
        this.likes = likes;
        this.roll = roll;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public Set<Friendship> getUser1() {
        return user1;
    }

    public void setUser1(Set<Friendship> user1) {
        this.user1 = user1;
    }

    public Set<Friendship> getUser2() {
        return user2;
    }

    public void setUser2(Set<Friendship> user2) {
        this.user2 = user2;
    }

    public Set<Friendship> getActionUser() {
        return actionUser;
    }

    public void setActionUser(Set<Friendship> actionUser) {
        this.actionUser = actionUser;
    }

    public Set<PostLike> getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(Set<PostLike> postLikes) {
        this.postLikes = postLikes;
    }

    public Set<CommentLike> getLikes() {
        return likes;
    }

    public void setLikes(Set<CommentLike> likes) {
        this.likes = likes;
    }

    public AppRole getRoll() {
        return roll;
    }

    public void setRoll(AppRole roll) {
        this.roll = roll;
    }


    public AppUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
