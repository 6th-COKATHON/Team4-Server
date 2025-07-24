package com.example.team4.service;

import com.example.team4.domain.User;
import com.example.team4.dto.request.LoginRequest;
import com.example.team4.dto.request.SignupRequest;
import com.example.team4.dto.response.TokenResponse;
import com.example.team4.global.exception.AppException;
import com.example.team4.global.exception.user.EmailAlreadyExistsException;
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


    // 이메일 중복 확인 메소드
    public boolean isEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }


    public void signup(SignupRequest request) {
        // 이미 가입한 이메일인지 확인
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS);
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        userRepository.save(request.toEntity(encodedPassword));
    }

    public TokenResponse login(LoginRequest request) {
        try {
            String email = request.getEmail();
            String password = request.getPassword();

            // AuthenticationManager를 통한 인증 처리
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            User user = findUserByEmail(email);

            // 인증 성공 시 토큰 생성 로직
            String accessToken = jwtTokenProvider.createToken(user.getId());
            String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());

            // 사용자 조회

            user.updateRefreshToken(refreshToken);

            return new TokenResponse(accessToken, refreshToken);

        } catch (AuthenticationException e) {
            // 이메일이나 비밀번호가 잘못된 경우
            throw new InvalidCredentialsException(INVALID_CREDENTIALS);
        } catch (Exception e) {
            // 기타 예외 처리
            throw new AppException(INTERNAL_SERVER_ERROR);
        }
    }


    // RefreshToken을 받아 Access Token 재발급
    public String reissue(String refreshToken) {

        // Refresh Token 유효성 검사
        // JWT 토큰이 유효하지 않거나 만료된 경우 예외 발생
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new InvalidRefreshTokenException(INVALID_REFRESH_TOKEN);
        }

        Long userId = jwtTokenProvider.getUserId(refreshToken);
        User user = findUserById(userId);

        // Refresh Token이 사용자 정보와 일치하는지 확인
        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new InvalidRefreshTokenException(INVALID_REFRESH_TOKEN);
        }

        return jwtTokenProvider.createToken(user.getId());
    }


    public void logout(Long userId) {
        User user = findUserById(userId);
        user.updateRefreshToken(null); // Refresh Token 무효화
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND));
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND));
    }

}
