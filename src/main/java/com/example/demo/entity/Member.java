package com.example.demo.entity;

import java.util.ArrayList;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "members")
public class Member {

    // DB에서 자동 증가되는 고유 member_id
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;              

    // 카카오가 발급해주는 고유 식별자 (sub, id 등)
    @Column(name = "kakao_id", nullable = false, unique = true)
    private String kakaoId;

    // 카카오 닉네임
    @Column(name = "name", nullable = false)
    private String nickname;     

    // 카카오 이메일
    //@Column(name = "email", nullable = false, unique = true)
    //private String email;
    
    @Column(name = "email", nullable =true)
    private String email;
    
    // 카카오 맵
    @Column(nullable = true)
    private Double latitude;
    @Column(nullable = true)
    private Double longitude;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMember> groupMemberships = new ArrayList<>();

    // 기본 생성자 및 편의 생성자
    public Member() {}

    public Member(String kakaoId, String nickname) {
        this.kakaoId = kakaoId;
        this.email = email;
    }
/*
    public Member(String kakaoId, String email, String nickname) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.email = email;
    }
*/

    // Getter / Setter
    public Long getId() {
        return id;
    }

    public String getkakaoId() {
        return kakaoId;
    }

    public void setOauthId(String kakaoId) {
        this.kakaoId= kakaoId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
