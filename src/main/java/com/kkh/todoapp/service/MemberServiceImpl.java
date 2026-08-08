package com.kkh.todoapp.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kkh.todoapp.entity.MemberEntity;
import com.kkh.todoapp.jpa.MemberRepository;
import com.kkh.todoapp.vo.LoginDTO;
import com.kkh.todoapp.vo.MemberDTO;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class MemberServiceImpl implements MemberService {
    
    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;
    
    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    
    @Override
    public boolean add(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setEmail(memberDTO.getEmail());
        memberEntity.setNickname(memberDTO.getNickname());
        memberEntity.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        memberEntity.setRoles("tester,normal");
        MemberEntity saved = memberRepository.save(memberEntity);
        return saved != null;
    }

    @Override
    public boolean matchLogin(LoginDTO loginDTO) {
        return memberRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword()).orElseThrow(() -> new UsernameNotFoundException("no user found")) != null;
    }
}
