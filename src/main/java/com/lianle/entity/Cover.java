package com.lianle.entity;

/**
 * Created by lianle on 2/23 0023.
 */

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "cover")
public class Cover implements Serializable {


    private Long id;
    private int cover_name;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "cover_name")
    public int getCover_name() {
        return cover_name;
    }

    public void setCover_name(int cover_name) {
        this.cover_name = cover_name;
    }

}

