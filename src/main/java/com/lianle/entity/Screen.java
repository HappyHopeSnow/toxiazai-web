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
@Table(name = "screen")
public class Screen implements Serializable {


    private Long id;
    private int screen_year;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "screen_year")
    public int getScreen_year() {
        return screen_year;
    }

    public void setScreen_year(int screen_year) {
        this.screen_year = screen_year;
    }

}

