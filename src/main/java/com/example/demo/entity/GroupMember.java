package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "group_member")
public class GroupMember {

    @EmbeddedId
    private GroupMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberId")
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role; // 'OWNER' 또는 'MEMBER'

    @Column(name = "joined_ts", nullable = false)
    private LocalDateTime joinedTs;

    // 기본 생성자
    public GroupMember() {}

    // 생성자 편의 메서드
    public GroupMember(Group group, Member member, Role role) {
        this.id = new GroupMemberId(group.getGroupId(), member.getId());
        this.group = group;
        this.member = member;
        this.role = role;
        this.joinedTs = LocalDateTime.now();
    }

      // (1) member를 읽어올 수 있도록 getter 추가
    public Member getMember() {
        return this.member;
    }

      // (2) role을 읽어올 수 있도록 getter 추가
    public Role getRole() {
        return this.role;
    }

      // (3) 필요하다면, GroupMemberId를 반환해 주는 getter도 추가 가능
    public GroupMemberId getId() {
        return this.id;
    }

}
