package com.example.zhblogsnav.repository;

import com.example.zhblogsnav.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * zhblogsnav
 *
 * @author qianshu
 * @date 2022/10/18
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {

    Blog findBlogById(String id);
}
