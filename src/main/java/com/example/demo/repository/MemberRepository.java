package com.example.demo.repository;

import com.example.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 카카오에서 넘어온 oauthId 로 회원 찾기
    Optional<Member> findBykakaoId(String kakaoId);

    // 이메일 중복 체크
    Optional<Member> findByEmail(String email);

    Optional<Member> findByname(String name);
}
