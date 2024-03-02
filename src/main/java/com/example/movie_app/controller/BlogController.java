package com.example.movie_app.controller;

import com.example.movie_app.entity.Blog;
import com.example.movie_app.entity.User;
import com.example.movie_app.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/blogs")
public class BlogController {
    private final BlogService blogService;
    @GetMapping()
    public String viewHomePage(Model model){
        model.addAttribute("blogs", blogService.findAll());
        return "admin/blog/index";
    }
    @GetMapping("/own-blog")
    public String viewOwnBlogPage(Model model){

        model.addAttribute("blogList",blogService.findByCurrentUser());
        return "admin/blog/own-blog";
    }
    @GetMapping("/create")
    public String viewCreateBlogPage(){
        return "admin/blog/create";
    }
    @GetMapping("/{id}/detail-blog")
    public String viewDetailBlogPage(@PathVariable Integer id, Model model){
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog", blog);
        return "admin/blog/detail";
    }
}
