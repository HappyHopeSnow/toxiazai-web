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
@Table(name = "film_performer_rel")
public class FilmPerformerRel implements Serializable {


    private Long id;
    private Long film_id;
    private Long performer_id;
    private String screen_year;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "film_id")
    public Long getFilm_id() {
        return film_id;
    }

    public void setFilm_id(Long film_id) {
        this.film_id = film_id;
    }

    @Column(name = "performer_id")
    public Long getPerformer_id() {
        return performer_id;
    }

    public void setPerformer_id(Long performer_id) {
        this.performer_id = performer_id;
    }

    @Column(name = "screen_year")
    public String getScreen_year() {
        return screen_year;
    }

    public void setScreen_year(String screen_year) {
        this.screen_year = screen_year;
    }

}
