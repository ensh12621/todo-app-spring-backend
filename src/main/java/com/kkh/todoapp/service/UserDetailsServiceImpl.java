package com.kkh.todoapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kkh.todoapp.entity.MemberEntity;
import com.kkh.todoapp.jpa.MemberRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private MemberRepository memberRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    public UserDetailsServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String subject) throws UsernameNotFoundException {

        logger.info("loadUserByName() ... id: {}", subject);
        MemberEntity memberEntity = memberRepository.findByEmail(subject).orElseThrow(() -> new UsernameNotFoundException("no user found"));
        logger.info("loadUserByName() ... memberEntity email? {}, password {}", memberEntity.getEmail(), memberEntity.getPassword());
        

        return new UserDetailsImpl(
            memberEntity.getEmail(), 
            memberEntity.getPassword(),
            List.of(memberEntity.getRoles().split(","))
                .stream()
                .map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
    }
    
}
