package com.kkh.todoapp.service;

import java.time.Instant;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.kkh.todoapp.entity.MemberEntity;
import com.kkh.todoapp.entity.RefreshTokenEntity;
import com.kkh.todoapp.jpa.MemberRepository;
import com.kkh.todoapp.jpa.RefreshTokenRepository;
import com.kkh.todoapp.vo.JWTRefreshDTO;
import com.kkh.todoapp.vo.LoginDTO;

import jakarta.transaction.Transactional;


@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{
    
    private static final Logger logger = LoggerFactory.getLogger(RefreshTokenServiceImpl.class);

    private MemberRepository memberRepository;
    private RefreshTokenRepository refreshTokenRepository;
    private JwtService jwtService;
    private MemberExceptionHandler memberExceptionHandler;

    public RefreshTokenServiceImpl(MemberRepository memberRepository, RefreshTokenRepository refreshTokenRepository, JwtService jwtService, MemberExceptionHandler memberExceptionHandler){
        this.memberRepository = memberRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
        this.memberExceptionHandler = memberExceptionHandler;
    }

    @Transactional
    @Override
    public RefreshTokenEntity generateRefreshToken(String email) {
        

        MemberEntity memberEntity = memberRepository.findByEmail(email).orElseThrow(() -> memberExceptionHandler.badCredentailException());

        // 기존 refresh 토큰 데이터 있으면 제거
        if(memberEntity.getRefreshEntity() != null){
            logger.info("deleting old refresth token.. +++++++++++++++");
            refreshTokenRepository.delete(memberEntity.getRefreshEntity());
            memberEntity.setRefreshEntity(null);

            refreshTokenRepository.flush(); // 지워진 영역을 db disk에 즉시 반영
        }

        
        RefreshTokenEntity refreshEntity = RefreshTokenEntity
                                        .builder()
                                        .token(UUID.randomUUID().toString())
                                        .expirationAt(Instant.now().plusSeconds(1 * 60 * 15))
                                        .memberEntity(memberRepository.findByEmail(email).get())
                                        .build();

        memberEntity.setRefreshEntity(refreshEntity);

        return refreshTokenRepository.save(refreshEntity);
    }



    private boolean checkRefreshTokenExpiration(RefreshTokenEntity refreshTokenEntity){
        //logger.info("expiration exception 발동이 왜 됐지?? {} / {}", refreshTokenEntity.getExpirationAt().toString(), Instant.now());
        return refreshTokenEntity.getExpirationAt().isAfter(Instant.now());
    }

    @Transactional
    @Override
    public String validateRefreshToken(String refreshToken) {
        
        
        // 1. refresh 토큰을 db에서도 조회되는지 확인한다.

        // 2. db에서 조회되는 refresh 토큰 정보가 없다면 bad credential exception을 발동한다.

        // 3. db에서 조회되는 refresh 토큰 정보가 있다면 만료기한을 체크한다.

        // 4. 만료기한이 다 되었으면 db에서 엔트리를 제거하고 aheadofexpirationexception을 발동한다.

        // 5. 만료기한이 남아있다면 만료기한을 재설정하여 db에 저장하고 JWT를 재생성하여 반환한다.


        RefreshTokenEntity refreshEntity = refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> memberExceptionHandler.badCredentailException());
        
        if(!checkRefreshTokenExpiration(refreshEntity)){
            refreshTokenRepository.delete(refreshEntity);
            throw memberExceptionHandler.aheadOfExpirationException();
        }

        refreshEntity.setExpirationAt(Instant.now().plusSeconds(1 * 60 * 15));
        refreshTokenRepository.save(refreshEntity);

        logger.info("validation success.. expiration date will be updated");

        MemberEntity member = refreshEntity.getMemberEntity();

        return jwtService.generateJwt(
                                LoginDTO.builder()
                                .email(member.getEmail())
                                .password(member.getPassword())
                                .build()
        );
    }
}
