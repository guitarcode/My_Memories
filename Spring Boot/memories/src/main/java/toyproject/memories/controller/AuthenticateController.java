package toyproject.memories.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import toyproject.memories.dto.LoginDto;
import toyproject.memories.jwt.JwtFilter;
import toyproject.memories.jwt.TokenProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api")
public class AuthenticateController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/login")
    public Result authorize(HttpServletResponse response, @Valid @RequestBody LoginDto loginDto) {

        System.out.println("로그인 Dto를 통해 인증 토큰을 생성합니다..");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getName(), loginDto.getPassword());

        System.out.println("생성한 인증 토큰으로 인증을 진행합니다..");

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        System.out.println("인증이 완료되어 context에 인증 정보를 저장합니다..");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        System.out.println("헤더에 jwt 토큰을 추가합니다..");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new Result(resultEnum.SUCCESS, jwt);
    }
}
