package com.example.zhblogsnav.controller;

import com.example.zhblogsnav.common.BaseResult;
import com.example.zhblogsnav.service.BlogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * zhblogsnav
 *
 * @author qianshu
 * @date 2022/10/17
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    private BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/getBlogTags")
    public BaseResult getBlogTags() {
        return blogService.findBlogTags();
    }

    @GetMapping("/getBlogTagsCount")
    public BaseResult getBlogTagsCount() {
        return blogService.findBlogTagsCount();
    }

    @GetMapping("/getBlogs")
    public BaseResult getBlogs(String search, String tags, Integer status, Integer size, Integer offset) {
        return blogService.findBlogs(search, tags, status, size, offset);
    }

    @GetMapping("/getBlogsRandom")
    public BaseResult getBlogsRandom(String search, String tags, Integer n) {
        return blogService.findBlogsRandom(search, tags, n);
    }

    @GetMapping("/getArch")
    public BaseResult getArch() {
        return blogService.findArch();
    }

    @GetMapping("/getDomain")
    public BaseResult getDomain() {
        return blogService.findDomain();
    }
}
