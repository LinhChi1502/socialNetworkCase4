package com.example.project.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notiId")
    private int notiId;

    @Column(name = "notiContent")
    private String content;

    @Column(name = "notiDate")
    @CreationTimestamp
    private Date date;

    @Column(name = "notiChecked")
    private boolean notiChecked;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "userID")
    private AppUser user;

    public Notification() {
    }

    public Notification(int notiId, String content, Date date, boolean notiChecked, AppUser user) {
        this.notiId = notiId;
        this.content = content;
        this.date = date;
        this.notiChecked = notiChecked;
        this.user = user;
    }

    public int getNotiId() {
        return notiId;
    }

    public void setNotiId(int notiId) {
        this.notiId = notiId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isNotiChecked() {
        return notiChecked;
    }

    public void setNotiChecked(boolean notiChecked) {
        this.notiChecked = notiChecked;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
