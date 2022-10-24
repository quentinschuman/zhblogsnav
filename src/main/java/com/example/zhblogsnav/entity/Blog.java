package com.example.zhblogsnav.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * zhblogsnav
 *
 * @author qianshu
 * @date 2022/10/18
 */
@Entity
@Table(name = "t_blog")
public class Blog {

    @Id
    private String id;

    @Column
    private Integer idx;

    @Column
    private String name;

    @Column
    private String url;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> tags;

    @Column
    private String sign;

    @Column
    private String feed;

    @Column
    private String status;

    @Column(name = "`repeat`")
    private Boolean repeat;

    @Column
    private Boolean enabled;

    @Column(name = "site_map")
    private String sitemap;

    @Column
    private String arch;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date join_time;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date update_time;

    @Column
    private String saveweb_id;

    @Column
    private Boolean recommend;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getRepeat() {
        return repeat;
    }

    public void setRepeat(Boolean repeat) {
        this.repeat = repeat;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getSitemap() {
        return sitemap;
    }

    public void setSitemap(String sitemap) {
        this.sitemap = sitemap;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public Date getJoin_time() {
        return join_time;
    }

    public void setJoin_time(Date join_time) {
        this.join_time = join_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getSaveweb_id() {
        return saveweb_id;
    }

    public void setSaveweb_id(String saveweb_id) {
        this.saveweb_id = saveweb_id;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }
}
