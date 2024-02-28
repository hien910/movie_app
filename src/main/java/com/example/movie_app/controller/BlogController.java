package com.example.movie_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/blogs")
public class BlogController {
    @GetMapping("/")
    public String viewHomePage(){
        return "admin/blog/index";
    }
    @GetMapping("/own-blog")
    public String viewOwnBlogPage(){
        return "admin/blog/own-blog";
    }
    @GetMapping("/create")
    public String viewCreateBlogPage(){
        return "admin/blog/create";
    }
    @GetMapping("/{id}/detail-blog")
    public String viewDetailBlogPage(@PathVariable Integer id){
        return "admin/blog/detail";
    }
}
