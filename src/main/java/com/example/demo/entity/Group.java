package com.example.demo.entity;

import jakarta.persistence.*;
import com.example.demo.entity.Store;
import com.example.demo.entity.Member;
import com.example.demo.entity.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "groups")
public class Group {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(name = "privacy")
    private String privacy;
    
    @Column(name="max_members", nullable=false)
    private Integer maxMembers;

    @Column(nullable=false)
    private String destination;
    @Column(name = "place_id", nullable = false)
    private String placeId;

    @Column(name = "destination_address", nullable = false)
    private String destinationAddress;

    @Column(name = "destination_lat", nullable = false)
    private Double destinationLat;

    @Column(name = "destination_lng", nullable = false)
    private Double destinationLng;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    // ---- 새로 추가: 방장(owner) 정보를 위한 필드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Member owner;

    // (optional) 편리하게 생성일시 저장
    private LocalDateTime createdTs = LocalDateTime.now();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMember> participants = new ArrayList<>();

    // ─────────────── 기본 생성자 ───────────────
    public Group() {}

    // (2) 편의 메서드: owner 가져오기
    public Member getOwner() {
        for (GroupMember gm : participants) {
            if (gm.getRole() == Role.OWNER) {
                return gm.getMember();
            }
        }
        return null;
    }

    // (3) 편의 메서드: 참가자 리스트(단순 Member 객체 목록) 가져오기
    public List<Member> getMembers() {
    return participants.stream()
        .filter(gm -> gm.getRole() == Role.MEMBER)
        .map(GroupMember::getMember)
        .collect(Collectors.toList());
    }

    // (4) 편의 메서드: 방장 지정하기 (create 시점에 호출)
    public void addOwner(Member owner) {
        GroupMember gm = new GroupMember(this, owner, Role.OWNER);
        participants.add(gm);
    }

    // (5) 편의 메서드: 일반 멤버 추가하기
    public void addMember(Member member) {
        GroupMember gm = new GroupMember(this, member, Role.MEMBER);
        participants.add(gm);
    }


    // ─────────────── 모든 필드에 대한한 GETTER ───────────────
    public Long getGroupId() { 
        return groupId; 
    }

    public String getName()    {
        return name; 
    }

    public String getCategory(){ 
        return category; 
    }

    public String getPrivacy() { 
        return privacy; 
    }

    public Integer getMaxMembers() { 
        return maxMembers; 
    }

    public String getDestination() { 
        return destination; 
    }

    public Store  getStore() { 
        return store; 
    }

    public Member getOwnerEntity() {
        return owner;
    }

    public LocalDateTime getCreatedTs() { 
        return createdTs; 
    }

    public List<GroupMember> getParticipants() {
        return participants;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public Double getDestinationLat() {
        return destinationLat;
    }

    public Double getDestinationLng() {
        return destinationLng;
    }

    public List<Long> getParticipantMemberIds() {
        return participants.stream()
            .map(gm -> gm.getMember().getId()) 
            .collect(Collectors.toList());
    }


    // ─────────────── 모든 필드에 대한 SETTER ───────────────

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxMembers(Integer maxMembers) {
        this.maxMembers = maxMembers;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public void setDestinationLat(Double destinationLat) {
        this.destinationLat = destinationLat;
    }

    public void setDestinationLng(Double destinationLng) {
        this.destinationLng = destinationLng;
    }

    public void setParticipants(List<GroupMember> participants) {
        this.participants = participants;
    }

    

}
