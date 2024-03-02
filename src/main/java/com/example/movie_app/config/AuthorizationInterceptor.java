package com.example.movie_app.config;

import com.example.movie_app.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("currentUser");

        if(user == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        String role = user.getRole().getValue();
        if(Objects.equals(user.getRole(),"ROLE_ADIMN")){

        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
