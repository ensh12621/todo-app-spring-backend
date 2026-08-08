package com.kkh.todoapp.service;

import com.kkh.todoapp.vo.LoginDTO;
import com.kkh.todoapp.vo.MemberDTO;

public interface MemberService {
    boolean add(MemberDTO memberDTO);
    boolean matchLogin(LoginDTO loginDTO);
}
