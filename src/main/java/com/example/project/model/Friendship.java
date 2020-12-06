package com.example.project.model;

import javax.persistence.*;

@Entity
@Table(name = "friendship")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendshipID")
    private int friendshipID;
    @Column(name = "friendstatus")
    private int friendStatus;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user1ID")
    private AppUser user1;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user2ID")
    private AppUser user2;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "actionuserID")
    private AppUser actionUser;


    public Friendship() {
    }

    public Friendship(int friendshipID, int friendStatus, AppUser user1, AppUser user2, AppUser actionUser) {
        this.friendshipID = friendshipID;
        this.friendStatus = friendStatus;
        this.user1 = user1;
        this.user2 = user2;
        this.actionUser = actionUser;
    }

    public int getFriendshipID() {
        return friendshipID;
    }

    public void setFriendshipID(int friendshipID) {
        this.friendshipID = friendshipID;
    }

    public int getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(int friendStatus) {
        this.friendStatus = friendStatus;
    }

    public AppUser getUser1() {
        return user1;
    }

    public void setUser1(AppUser user1) {
        this.user1 = user1;
    }

    public AppUser getUser2() {
        return user2;
    }

    public void setUser2(AppUser user2) {
        this.user2 = user2;
    }

    public AppUser getActionUser() {
        return actionUser;
    }

    public void setActionUser(AppUser actionUser) {
        this.actionUser = actionUser;
    }
}
