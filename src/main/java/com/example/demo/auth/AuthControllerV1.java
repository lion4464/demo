package com.example.demo.auth;

import com.example.demo.security.CustomPrincipal;
import com.example.demo.security.SecurityService;
import com.example.demo.user.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping(Path.baseUrl+"auth")
public class AuthControllerV1 {
    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/sign-up")
    public Mono<UserDto> signUp(@RequestBody UserDto dto) {
        UserEntity entity = userMapper.map(dto);
        return userService.signUp(entity).map(userMapper::map);
    }

    @PostMapping("/sign-in")
    public Mono<AuthResponseDto> signIn(@Valid @RequestBody AuthRequestDto dto) {
        return securityService.authenticate(dto.getUsername(), dto.getPassword())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDto.builder()
                                .id(tokenDetails.getUserId())
                                .token(tokenDetails.getToken())
                                .issuedAt(tokenDetails.getCreatedAt())
                                .expiredAt(tokenDetails.getExpiredAt())
                                .build()
                ));

    }
    @GetMapping("/info")
    public Mono<UserDto> info(Authentication authentication){
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return userService.getUserById(customPrincipal.getUserId()).map(userMapper::map);
    }
}
