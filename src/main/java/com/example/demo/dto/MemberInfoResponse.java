package com.example.demo.dto;

public class MemberInfoResponse {

    private Long memberId;
    private String name;
    private String email;

    public MemberInfoResponse(Long memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
