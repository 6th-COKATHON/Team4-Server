package com.example.team4.service;

import com.example.team4.domain.User;
import com.example.team4.dto.request.LoginRequest;
import com.example.team4.dto.response.TokenResponse;
import com.example.team4.global.exception.AppException;
import com.example.team4.global.exception.user.InvalidCredentialsException;
import com.example.team4.global.exception.user.InvalidRefreshTokenException;
import com.example.team4.global.exception.user.UserNotFoundException;
import com.example.team4.global.security.JwtTokenProvider;
import com.example.team4.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.team4.global.exception.CommonErrorCode.INTERNAL_SERVER_ERROR;
import static com.example.team4.global.exception.user.UserErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;



    public TokenResponse login(LoginRequest request) {
        try {
            String email = request.getEmail();
            String password = request.getPassword();

            Optional<User> optionalUser = userRepository.findByEmail(email);
            User user;

            if (optionalUser.isEmpty()) {
                // 유저가 없는 경우 → 생성
                String encodedPassword = passwordEncoder.encode(password);
                user = userRepository.save(request.toEntity(encodedPassword));
            } else {
                user = optionalUser.get();
                // 유저가 있는데 비밀번호 틀린 경우
                if (!passwordEncoder.matches(password, user.getPassword())) {
                    throw new AppException(INVALID_CREDENTIALS);
                }

                // 유저 존재 + 비밀번호 일치 → 인증 처리
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password)
                );
            }

            // 토큰 생성
            String accessToken = jwtTokenProvider.createToken(user.getId());

            return new TokenResponse(accessToken);

        } catch (AuthenticationException e) {
            // 이메일이나 비밀번호가 잘못된 경우
            throw new AppException(INVALID_CREDENTIALS);
        } catch (Exception e) {
            // 기타 예외 처리
            throw new AppException(INTERNAL_SERVER_ERROR);
        }
    }

}
