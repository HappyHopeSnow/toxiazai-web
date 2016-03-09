package com.lianle.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lianle on 2/13 0013.
 */
@Entity
@Table(name="user")
public class User implements Serializable {

    private Long id;

    private String userName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}