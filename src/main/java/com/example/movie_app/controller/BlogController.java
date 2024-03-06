package com.example.movie_app.controller;

import com.example.movie_app.entity.Blog;
import com.example.movie_app.entity.Image;
import com.example.movie_app.entity.User;
import com.example.movie_app.service.BlogService;
import com.example.movie_app.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/blogs")
public class BlogController {
    private final BlogService blogService;
    private final ImageService imageService;
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
//    @GetMapping("/{id}/detail-blog")
//    public String viewDetailBlogPage(@PathVariable Integer id, Model model){
//        Blog blog = blogService.getBlogById(id);
//        model.addAttribute("blog", blog);
//        return "admin/blog/detail";
//    }
    @GetMapping("/{id}/detail-blog")

        public String viewDetailPage(@PathVariable Integer id, Model model,
                @RequestParam(required = false, defaultValue = "1") Integer page,
                @RequestParam(required = false, defaultValue = "12") Integer size) {
            Blog blog = blogService.getBlogById(id);

            Page<Image> pageData = imageService.getAllImagesByCurrentUser(page, size);

            model.addAttribute("blog", blog);
            model.addAttribute("pageData", pageData);
            model.addAttribute("currentPage", page);
            model.addAttribute("images", pageData.getContent());
        return "admin/blog/detail";
    }
}
