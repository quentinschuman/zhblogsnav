package com.example.zhblogsnav.service.impl;

import com.example.zhblogsnav.common.BaseResult;
import com.example.zhblogsnav.entity.Blog;
import com.example.zhblogsnav.repository.BlogRepository;
import com.example.zhblogsnav.service.BlogService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * zhblogsnav
 *
 * @author qianshu
 * @date 2022/10/17
 */
@Service
public class BlogServiceImpl implements BlogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlogServiceImpl.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Value("${zhblogs.tags}")
    private String blogTagsAPIUrl;

    @Value("${zhblogs.tags.count}")
    private String blogTagsCountAPIUrl;

    @Value("${zhblogs.blogs}")
    private String blogsAPIUrl;

    @Value("${zhblogs.blogs.random}")
    private String blogsRandomAPIUrl;

    @Value("${zhblogs.arch}")
    private String blogArchAPIUrl;

    @Value("${zhblogs.domain}")
    private String blogDomainAPIUrl;

    private RestTemplate restTemplate;

    private BlogRepository blogRepository;

    public BlogServiceImpl(RestTemplate restTemplate, BlogRepository blogRepository) {
        this.restTemplate = restTemplate;
        this.blogRepository = blogRepository;
    }

    @Override
    public BaseResult findBlogTags() {
        try {
            BaseResult blogTags = restTemplate.getForObject(blogTagsAPIUrl, BaseResult.class);
            return blogTags;
        } catch (Exception e) {
            LOGGER.error("findBlogTags error:", e);
            return BaseResult.fail(e.getMessage());
        }
    }

    @Override
    public BaseResult findBlogTagsCount() {
        try {
            BaseResult blogTagsCount = restTemplate.getForObject(blogTagsCountAPIUrl, BaseResult.class);
            return blogTagsCount;
        } catch (Exception e) {
            LOGGER.error("findBlogTagsIncludeCount error:", e);
            return BaseResult.fail(e.getMessage());
        }
    }

    @Override
    public BaseResult findBlogs(String search, String tags, Integer status, Integer size, Integer offset) {
        try {
            StringBuilder url = new StringBuilder();
            url.append(blogsAPIUrl).append("?");
            if (StringUtils.hasText(search)) {
                url.append("search=").append(search).append("&");
            }
            if (StringUtils.hasText(tags)) {
                url.append("tags=").append(tags).append("&");
            }
            if (status != null) {
                url.append("status=").append(status).append("&");
            }
            if (size != null) {
                url.append("size=").append(size).append("&");
            }
            if (offset != null) {
                url.append("offset=").append(offset);
            }
            BaseResult blogs = restTemplate.getForObject(url.toString(), BaseResult.class);
            return blogs;
        } catch (Exception e) {
            LOGGER.error("findBlogs error:", e);
            return BaseResult.fail(e.getMessage());
        }
    }

    @Override
    public BaseResult findBlogsRandom(String search, String tags, Integer n) {
        try {
            StringBuilder url = new StringBuilder();
            url.append(blogsRandomAPIUrl).append("?");
            if (StringUtils.hasText(search)) {
                url.append("search=").append(search).append("&");
            }
            if (StringUtils.hasText(tags)) {
                url.append("tags=").append(tags).append("&");
            }
            if (n != null) {
                url.append("n=").append(n).append("&");
            }
            BaseResult blogs = restTemplate.getForObject(url.toString(), BaseResult.class);
            List<Blog> blogList = MAPPER.readValue(MAPPER.writeValueAsString(blogs.getData()), new TypeReference<List<Blog>>() {
            });
            // 保存随机获取的blog
            for (Blog blog : blogList) {
                // 根据id判断该条blog是否已经入库过
                Blog blogByIdx = blogRepository.findBlogById(blog.getId());
                // 未入库则入库保存
                if (blogByIdx == null) {
                    blogRepository.save(blog);
                }
            }
            return blogs;
        } catch (Exception e) {
            LOGGER.error("findBlogsRandom error:", e);
            return BaseResult.fail(e.getMessage());
        }
    }

    @Override
    public BaseResult findArch() {
        try {
            BaseResult blogArch = restTemplate.getForObject(blogArchAPIUrl, BaseResult.class);
            return blogArch;
        } catch (Exception e) {
            LOGGER.error("findArch error:", e);
            return BaseResult.fail(e.getMessage());
        }
    }

    @Override
    public BaseResult findDomain() {
        try {
            BaseResult blogDomain = restTemplate.getForObject(blogDomainAPIUrl, BaseResult.class);
            return blogDomain;
        } catch (Exception e) {
            LOGGER.error("findDomain error:", e);
            return BaseResult.fail(e.getMessage());
        }
    }
}
