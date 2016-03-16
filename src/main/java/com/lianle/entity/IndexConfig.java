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
@Table(name="index_config")
public class IndexConfig implements Serializable {


    private Long id;
    private String recommend_ids;
    private String array_ids;
    private String new_ids;
    private String hot_ids;
    private String love_ids;
    private Date createTime;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="recommend_ids")
    public String getRecommend_ids() {
        return recommend_ids;
    }
    public void setRecommend_ids(String recommend_ids) {
        this.recommend_ids = recommend_ids;
    }

    @Column(name="array_ids")
    public String getArray_ids() {
        return array_ids;
    }
    public void setArray_ids(String array_ids) {
        this.array_ids = array_ids;
    }

    @Column(name="new_ids")
    public String getNew_ids() {
        return new_ids;
    }
    public void setNew_ids(String new_ids) {
        this.new_ids = new_ids;
    }

    @Column(name="hot_ids")
    public String getHot_ids() {
        return hot_ids;
    }
    public void setHot_ids(String hot_ids) {
        this.hot_ids = hot_ids;
    }

    @Column(name="love_ids")
    public String getLove_ids() {
        return love_ids;
    }
    public void setLove_ids(String love_ids) {
        this.love_ids = love_ids;
    }

    @Column(name="createtime")
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
