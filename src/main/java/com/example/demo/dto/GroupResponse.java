package com.example.demo.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.dto.GroupResponse.OwnerInfo;
import com.example.demo.entity.Group;
import com.example.demo.entity.Member;

public class GroupResponse {
    private Long groupId;
    private String name;
    private String category;
    private String privacy;
    private Integer maxMembers;
    private String destination;

    public String destinationAddress;
    public Double destinationLat;
    public Double destinationLng;

    // 소유자 정보
    private OwnerInfo owner;
    // 참가 리스트
    private List<Long> participants;
    // 가게 리스트
    private List<StoreDto> stores;
    // (1) 기본 생성자
    public GroupResponse() {}

    // (2) 전체 필드를 초기화하는 생성자

    public GroupResponse(Group g) {
        this.groupId    = g.getGroupId();
        this.name       = g.getName();
        this.category   = g.getCategory();
        this.privacy    = g.getPrivacy();
        this.maxMembers = g.getMaxMembers();
        this.destination= g.getDestination();

         Member o = g.getOwner(); // 위에서 구현한 getOwner() 편의메서드 사용
        this.owner = new OwnerInfo(o.getId(), o.getNickname());

        // participants를 memberId 리스트로 변환
        this.participants = g.getMembers().stream()
                .map(Member::getId)
                .collect(Collectors.toList());
        }

    // (3) getter들
    public Long getGroupId()     { return groupId; }
    public String getName()      { return name; }
    public String getCategory()  { return category; }
    public String getPrivacy()   { return privacy; }
    public Integer getMaxMembers()  { return maxMembers; }
    public String getDestination()  { return destination; }
    public OwnerInfo getOwner()  { return owner; }
    public List<Long> getParticipants() { return participants; }

    public static class OwnerInfo {
        private Long memberId;
        private String nickname;

    public OwnerInfo(Long memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
        }
        
    public Long getMemberId()   { return memberId; }
    public String getNickname() { return nickname; }
    
    }
}
