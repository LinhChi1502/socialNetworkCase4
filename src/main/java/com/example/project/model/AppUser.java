package com.example.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private int userId;
    @Column(name = "username", unique = true, columnDefinition = "varchar(50)")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "avatarURL", nullable = true)
    private String avatarURL = "5.jpg";
    @Transient
    private int flag;
    @Transient
    private MultipartFile avatar;
    @Column(name = "firstname", nullable = true)
    private String firstName;
    @Column(name = "lastname", nullable = true)
    private String lastName;
    @Column(name = "city", nullable = true)
    private String city;
    @Column(name = "gender", nullable = true)
    private String gender;
    @Column(name = "about", columnDefinition = "longtext", nullable = true)
    private String about;
    @Column(name = "phone", nullable = true)
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
    private String phone;
    @Column(name = "dob", nullable = true)
    private String dateOfBirth;

    //1 user co nhieu post
    @JsonIgnore
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<Post> posts;

    //friendship
    @JsonIgnore
    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL)
    private Set<Friendship> user1;
    @JsonIgnore
    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL)
    private Set<Friendship> user2;
    @JsonIgnore
    @OneToMany(mappedBy = "actionUser", cascade = CascadeType.ALL)
    private Set<Friendship> actionUser;

    //postLike
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<PostLike> postLikes;

    //commentLike
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<CommentLike> likes;

    //commentLike
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Notification> notifications;


    //role
    @ManyToOne
    private AppRole role;

    public AppUser() {
    }

    public AppUser(int userId, String userName, String password, String avatarURL, MultipartFile avatar, String firstName, String lastName, String city, String gender, String about, String phone, String dateOfBirth, Set<Post> posts, Set<Friendship> user1, Set<Friendship> user2, Set<Friendship> actionUser, Set<PostLike> postLikes, Set<CommentLike> likes, Set<Notification> notifications, AppRole role) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.avatarURL = avatarURL;
        this.avatar = avatar;
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
        this.notifications = notifications;
        this.role = role;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
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

    public AppRole getRole() {
        return role;
    }

    public void setRole(AppRole role) {
        this.role = role;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }


}
