package com.kkh.todoapp.service;

import java.util.List;
import java.util.stream.Collectors;

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

    public UserDetailsServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String subject) throws UsernameNotFoundException {

        MemberEntity memberEntity = memberRepository.findByEmail(subject).orElseThrow(() -> new UsernameNotFoundException("no user found"));

        return new UserDetailsImpl(
            memberEntity.getEmail(), 
            List.of(memberEntity.getRoles().split(","))
                .stream()
                .map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
    }
    
}
