package com.example.movie_app.service;

import com.example.movie_app.entity.User;
import com.example.movie_app.exception.BadRequestException;
import com.example.movie_app.model.enums.UserRole;
import com.example.movie_app.model.request.LoginRequest;
import com.example.movie_app.model.request.SignUpRequest;
import com.example.movie_app.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final HttpSession session;

    public void login(LoginRequest request) {
        log.info("Login: {}", request);

        // Tìm user theo email
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadRequestException("Tài khoản hoặc mật khẩu không đúng"));

        // Kiểm tra password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BadRequestException("Tài khoản hoặc mật khẩu không đúng");
        }


        // Lưu thông tin user vào trong session
        session.setAttribute("currentUser", user);
    }

    public void signup(SignUpRequest request) {
        log.info("Signup: {}", request);

        // Tìm user theo email
        User existedUser = userRepository.findByEmail(request.getEmail()).orElse(null);



        if (existedUser != null) {
            throw new BadRequestException("Tài khoản này đã đăng ký");
        }

        User newUser = User.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(UserRole.USER)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        userRepository.save(newUser);

        // Lưu thông tin user vào trong session
        session.setAttribute("currentUser", newUser);
    }

    public void logout() {
        session.setAttribute("currentUser", null);
    }
}
