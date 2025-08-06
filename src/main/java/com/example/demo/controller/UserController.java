// src/main/java/com/example/demo/controller/UserController.java
package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserLocationDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "https://localhost:3000")
public class UserController {

    private final MemberRepository memberRepo;

    public UserController(MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    /**
     * 현재 로그인된 사용자의 정보를 가져옵니다.
     * 인증 안 된 경우에도 200 OK + null 을 반환합니다.
     */
    @GetMapping("/me")
    public ResponseEntity<UserDto> me(@AuthenticationPrincipal Member member) {
        if (member == null) {
            // 익명 사용자
            return ResponseEntity.ok(null);
        }
        // UserDto는 memberId, name, email 등 최소 정보만 담는 DTO
        UserDto dto = new UserDto(member.getId(), member.getNickname(), member.getEmail());
        return ResponseEntity.ok(dto);
    }

    /**
     * 사용자의 위도/경도 정보를 저장합니다.
     * 인증이 필요하며, 인증 안 됐으면 401을 던집니다.
     */
    @PostMapping("/location")
    public ResponseEntity<Void> setLocation(
            @RequestBody UserLocationDto locationDto,
            @AuthenticationPrincipal Member member) {

        if (member == null) {
            // 인증 안 됐으면 401
            return ResponseEntity.status(401).build();
        }

        member.setLatitude(locationDto.getLatitude());
        member.setLongitude(locationDto.getLongitude());
        memberRepo.save(member);

        return ResponseEntity.ok().build();
    }
}
