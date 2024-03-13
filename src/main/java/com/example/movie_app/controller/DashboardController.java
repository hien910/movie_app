package com.example.movie_app.controller;

import com.example.movie_app.service.BlogService;
import com.example.movie_app.service.MovieService;
import com.example.movie_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/dashboard")
public class DashboardController {
    private final BlogService blogService;
    private final UserService userService;
    private final MovieService movieService;
    @GetMapping()
    public String viewHomePage(Model model) {
        // Lấy ngày hiện tại
        LocalDateTime now = LocalDateTime.now();

        // Đặt ngày bắt đầu là đầu tháng hiện tại
        LocalDateTime start = LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0);

        // Đặt ngày kết thúc là cuối tháng hiện tại
        LocalDateTime end = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 23, 59, 59);

        // Chuyển đổi LocalDateTime sang Date
        Date startDate = java.sql.Timestamp.valueOf(start);
        Date endDate = java.sql.Timestamp.valueOf(end);

        model.addAttribute("movies", movieService.findMovieOfMonth(startDate, endDate));
        model.addAttribute("allMovie", movieService.findAll());

        model.addAttribute("blogs", blogService.findBlogOfMonth(startDate, endDate));
        model.addAttribute("allBlog", blogService.findAll());

        model.addAttribute("users", userService.findUserOfMonth(startDate, endDate));
        model.addAttribute("allUser", userService.findAll() );

        return "admin/dashboard/dashboard";
    }

}
