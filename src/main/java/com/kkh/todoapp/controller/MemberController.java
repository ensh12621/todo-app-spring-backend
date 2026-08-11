package com.kkh.todoapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kkh.todoapp.entity.MemberEntity;
import com.kkh.todoapp.service.JwtService;
import com.kkh.todoapp.service.MemberService;
import com.kkh.todoapp.service.RefreshTokenService;
import com.kkh.todoapp.vo.JWTRefreshDTO;
import com.kkh.todoapp.vo.LoginDTO;
import com.kkh.todoapp.vo.MemberDTO;

@RestController
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;
    private JwtService jwtService;
    private RefreshTokenService refreshTokenService;

    private AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    public MemberController(MemberService memberService, JwtService jwtService,
            AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService) {
        this.memberService = memberService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody MemberDTO memberDto) {

        logger.info("웹 요청 들어옴 .. /member/add");
        logger.info("memberDTO - {}", memberDto.toString());

        if (!memberService.add(memberDto)) {
            return ResponseEntity.badRequest().body("error: failed to save new user");
        }

        return ResponseEntity.ok("success");

    }

    @GetMapping("login-done-test")
    public String loginDoneTest() {
        return "success";
    }

    @PreAuthorize("hasAuthority('tester')")
    @GetMapping("login-done-test2")
    public String loginDoneTest2() {
        return "success";
    }

    @PostMapping("/login")
    @ResponseBody
    public JWTRefreshDTO login(@RequestBody LoginDTO loginDTO) {

        logger.info("login() 진입 중..");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

                
        logger.info("login email: {}, password: {} authenticated(): {}", loginDTO.getEmail(), loginDTO.getPassword(),
                authentication.isAuthenticated());

        if (authentication.isAuthenticated()) {

            String jwt = jwtService.generateJwt(loginDTO);
            logger.info("jwt token => ({})", jwt);

            String refreshToken = refreshTokenService.generateRefreshToken(loginDTO.getEmail()).getToken();

            return JWTRefreshDTO
                    .builder()
                    .jwt(jwt)
                    .refresh(refreshToken)
                    .build();
        }

        throw new BadCredentialsException("bad login data");
    }

    @PostMapping("/refresh-JWT")
    @ResponseBody
    public String refreshJwt(@RequestParam String refreshToken) {
        return refreshTokenService.validateRefreshToken(refreshToken);
        
    }
}
