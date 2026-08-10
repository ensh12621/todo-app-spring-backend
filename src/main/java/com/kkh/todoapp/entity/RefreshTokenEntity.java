package com.kkh.todoapp.entity;

import java.time.Instant;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="refresh")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="refresh_pk")
    private int refreshPk;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(name="expiration_at", nullable = false)
    private Instant expirationAt;

    @OneToOne
    @JoinColumn(name="member_fk", referencedColumnName = "member_pk")
    private MemberEntity memberEntity;
}
