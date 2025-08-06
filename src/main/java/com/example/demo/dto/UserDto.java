// src/main/java/com/example/demo/dto/UserDto.java
package com.example.demo.dto;

public class UserDto {
    private Long memberId;
    private String name;
    private String email;

    // 기본 생성자(직렬화/역직렬화용)
    public UserDto() {}

    // 전체 필드를 초기화하는 생성자
    public UserDto(Long memberId, String name, String email) {
        this.memberId = memberId;
        this.name     = name;
        this.email    = email;
    }

    // Getter/Setter
    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
