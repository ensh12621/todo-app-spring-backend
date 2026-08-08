package com.kkh.todoapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kkh.todoapp.service.JwtService;
import com.kkh.todoapp.service.MemberService;
import com.kkh.todoapp.vo.LoginDTO;
import com.kkh.todoapp.vo.MemberDTO;

@RestController
@RequestMapping("/member")
public class MemberController {
    
    private MemberService memberService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    public MemberController(MemberService memberService, JwtService jwtService, AuthenticationManager authenticationManager){
        this.memberService = memberService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody MemberDTO memberDto){

        if(!memberService.add(memberDto)){
            return ResponseEntity.badRequest().body("error: failed to save new user");
        }
        
        return ResponseEntity.ok("success");

    } 

    @GetMapping("login-done-test")
    public String loginDoneTest(){
        return "success";
    }

    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        

        logger.info("login() 진입 중..");

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        
        logger.info("login email: {}, password: {} authenticated(): {}", loginDTO.getEmail(), loginDTO.getPassword(), authentication.isAuthenticated());


        if(authentication.isAuthenticated()){
            return ResponseEntity.ok(jwtService.generateJwt(loginDTO));
        }

        // if(memberService.matchLogin(loginDTO)){
        //     return ResponseEntity.ok(jwtService.generateJwt(loginDTO));
        // }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("not authenticated");

    }
}
