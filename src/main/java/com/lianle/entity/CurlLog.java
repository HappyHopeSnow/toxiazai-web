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
@Table(name="curl_log")
public class CurlLog implements Serializable {


    private Long id;
    private Long start_id;
    private Long end_id;
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

    @Column(name="start_id")
    public Long getStart_id() {
        return start_id;
    }
    public void setStart_id(Long start_id) {
        this.start_id = start_id;
    }

    @Column(name="end_id")
    public Long getEnd_id() {
        return end_id;
    }
    public void setEnd_id(Long end_id) {
        this.end_id = end_id;
    }

    @Column(name="createtime")
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
