package com.example.movie_app.rest;

import com.example.movie_app.entity.Blog;
import com.example.movie_app.model.request.UpsertBlogRequest;
import com.example.movie_app.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/blogs")
@RequiredArgsConstructor
public class BlogApi {
    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<?> createBlog(@RequestBody UpsertBlogRequest request){
        Blog blog = blogService.createBlog(request);
        return ResponseEntity.ok(blog);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<?> updateBlog(@RequestBody UpsertBlogRequest request,@PathVariable Integer id){
        Blog blog = blogService.updateBlog(request,id);
        return ResponseEntity.ok(blog);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteBlog(@PathVariable Integer id){
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }
}
