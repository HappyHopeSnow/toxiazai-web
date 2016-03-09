package com.lianle.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

@Entity
@Table(name="film")
public class Film implements Serializable {


    private Long id;
    private Long uid;
    private String name;
    private String key_word;
    private Long format_id;
    private String format;
    private String captions_type;
    private float score;
    private String size;
    private Long screen_year_id;
    private String screen_year;
    private String down_model;
    private Long cover_id;
    private String cover_name;
    private Long class_id;
    private String class_name;
    private String director;
    private String performer;
    private Long country_id;
    private String country;
    private Long language_id;
    private String language;
    private String screen_date;
    private String length;
    private String other_name;
    private String movie_size;
    private String description;
    private String seed;
    private Date createTime;
    private Date modifyTime;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="uid")
    public Long getUid() {
        return uid;
    }
    public void setUid(Long uid) {
        this.uid = uid;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column(name="key_word")
    public String getKey_word() {
        return key_word;
    }
    public void setKey_word(String key_word) {
        this.key_word = key_word;
    }

    @Column(name="format_id")
    public Long getFormat_id() {
        return format_id;
    }
    public void setFormat_id(Long format_id) {
        this.format_id = format_id;
    }

    @Column(name="format")
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }

    @Column(name="captions_type")
    public String getCaptions_type() {
        return captions_type;
    }
    public void setCaptions_type(String captions_type) {
        this.captions_type = captions_type;
    }

    @Column(name="score")
    public float getScore() {
        return score;
    }
    public void setScore(float score) {
        this.score = score;
    }

    @Column(name="size")
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }

    @Column(name="screen_year_id")
    public Long getScreen_year_id() {
        return screen_year_id;
    }
    public void setScreen_year_id(Long screen_year_id) {
        this.screen_year_id = screen_year_id;
    }

    @Column(name="screen_year")
    public String getScreen_year() {
        return screen_year;
    }
    public void setScreen_year(String screen_year) {
        this.screen_year = screen_year;
    }

    @Column(name="down_model")
    public String getDown_model() {
        return down_model;
    }
    public void setDown_model(String down_model) {
        this.down_model = down_model;
    }

    @Column(name="cover_id")
    public Long getCover_id() {
        return cover_id;
    }
    public void setCover_id(Long cover_id) {
        this.cover_id = cover_id;
    }

    @Column(name="cover_name")
    public String getCover_name() {
        return cover_name;
    }
    public void setCover_name(String cover_name) {
        this.cover_name = cover_name;
    }

    @Column(name="class_id")
    public Long getClass_id() {
        return class_id;
    }
    public void setClass_id(Long class_id) {
        this.class_id = class_id;
    }

    @Column(name="class_name")
    public String getClass_name() {
        return class_name;
    }
    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    @Column(name="director")
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }

    @Column(name="performer")
    public String getPerformer() {
        return performer;
    }
    public void setPerformer(String performer) {
        this.performer = performer;
    }

    @Column(name="country_id")
    public Long getCountry_id() {
        return country_id;
    }
    public void setCountry_id(Long country_id) {
        this.country_id = country_id;
    }

    @Column(name="country")
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name="language_id")
    public Long getLanguage_id() {
        return language_id;
    }
    public void setLanguage_id(Long language_id) {
        this.language_id = language_id;
    }

    @Column(name="language")
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    @Column(name="screen_date")
    public String getScreen_date() {
        return screen_date;
    }
    public void setScreen_date(String screen_date) {
        this.screen_date = screen_date;
    }

    @Column(name="length")
    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }

    @Column(name="other_name")
    public String getOther_name() {
        return other_name;
    }
    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    @Column(name="movie_size")
    public String getMovie_size() {
        return movie_size;
    }
    public void setMovie_size(String movie_size) {
        this.movie_size = movie_size;
    }

    @Column(name="description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name="seed")
    public String getSeed() {
        return seed;
    }
    public void setSeed(String seed) {
        this.seed = seed;
    }

    @Column(name="createtime")
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name="modifytime")
    public Date getModifyTime() {
        return modifyTime;
    }
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

}
