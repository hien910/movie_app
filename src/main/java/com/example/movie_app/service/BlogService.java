package com.example.movie_app.service;

import com.example.movie_app.entity.Blog;
import com.example.movie_app.repository.BlogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    public Page<Blog> findBlogByStatusOrderByPublishedAtDesc(Boolean status, Integer page, Integer size){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("publishedAt").descending());
        return blogRepository.findBlogByStatusOrderByPublishedAtDesc(status ,pageRequest);
    }
    public Blog findBlogByIdAndSlug(Integer id, String slug, boolean status){
        return blogRepository.findBlogByIdAndSlugAndStatus(id,slug, status);
    }
    public Page<Blog> getBlogByStatus(Boolean status, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("publishedAt").descending()); // page trong jpa bắt đầu từ 0
        return blogRepository.findBlogByStatusOrderByPublishedAtDesc( status, pageRequest);
    }


}
