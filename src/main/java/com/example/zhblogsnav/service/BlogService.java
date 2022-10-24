package com.example.zhblogsnav.service;

import com.example.zhblogsnav.common.BaseResult;

/**
 * zhblogsnav
 *
 * @author qianshu
 * @date 2022/10/17
 */
public interface BlogService {

    /**
     * 获取博客标签
     * @return
     */
    BaseResult findBlogTags();

    /**
     * 获取博客标签（包含个数）
     * @return
     */
    BaseResult findBlogTagsCount();

    /**
     * 获取博客数据
     * @param search 搜索博客时的关键词
     * @param tags 搜索博客时的指定标签
     * @param status 0 全部，1 展示，-1 不展示，2 推荐
     * @param size 返回博客的数目（-1 全量返回）
     * @param offset 偏移量，即指定输出的起始位置，用于分页展示
     * @return
     */
    BaseResult findBlogs(String search, String tags, Integer status, Integer size, Integer offset);

    /**
     * 获取随机博客数据
     * @param search 筛选博客的关键词
     * @param tags 筛选博客的标签
     * @param n 返回随机博客的个数，默认为10.。
     * @return
     */
    BaseResult findBlogsRandom(String search, String tags, Integer n);

    /**
     * 获取构架信息
     * @return
     */
    BaseResult findArch();

    /**
     * 获取域名信息
     * @return
     */
    BaseResult findDomain();
}
