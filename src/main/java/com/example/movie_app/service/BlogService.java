package com.example.movie_app.service;

import com.example.movie_app.entity.Blog;
import com.example.movie_app.entity.User;
import com.example.movie_app.exception.ResourceNotFoundException;
import com.example.movie_app.model.request.UpsertBlogRequest;
import com.example.movie_app.repository.BlogRepository;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final HttpSession session;

    Slugify slugify = Slugify.builder().build();


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
    public List<Blog> findAll() {
        return blogRepository.findAll(Sort.by("createdAt").descending());
    }
    public List<Blog> findByCurrentUser(){
        User user = (User) session.getAttribute("currentUser");
        return blogRepository.findByUser_idOrderByCreatedAtDesc(user.getId());
    }

    public Blog createBlog(UpsertBlogRequest request) {

            User user = (User) session.getAttribute("currentUser");

            // Create blog

        Faker faker = new Faker();
        Boolean staus = request.getStatus();
        Date publishAt = null;
        if(staus){
            publishAt = new Date();
        }
            Blog blog = Blog.builder()
                    .title(request.getTitle())
                    .slug(slugify.slugify(request.getTitle()))
                    .content(request.getContent())
                    .description(request.getDescription())
                    .status(request.getStatus())
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .publishedAt(publishAt)
                    .user(user)
                    .thumbnail(faker.company().logo())
                    .build();

            return blogRepository.save(blog);
        }



    // Cập nhật bài viết
    public Blog updateBlog( UpsertBlogRequest request,Integer id) {
        // find blog by id
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với id = " + id));
        Date publishAt = null;
        if(request.getStatus()){
            publishAt = new Date();
        }
        // update blog
        blog.setTitle(request.getTitle());
        blog.setSlug(slugify.slugify(request.getTitle()));
        blog.setDescription(request.getDescription());
        blog.setContent(request.getContent());
        blog.setStatus(request.getStatus());
        blog.setUpdatedAt(new Date());
        blog.setPublishedAt(publishAt);
        blog.setThumbnail(request.getThumbnail());

        return blogRepository.save(blog);
    }

    // Xóa bài viết
    public void deleteBlog(Integer id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với id = " + id));
        blogRepository.delete(blog);
    }

    public Blog getBlogById(Integer id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bài viết với id = " + id));
    }
}



