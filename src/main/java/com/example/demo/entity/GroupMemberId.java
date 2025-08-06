package com.example.demo.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

// 복합키(PK)를 나타내는 Embeddable 클래스
@Embeddable
public class GroupMemberId implements Serializable {
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "member_id")
    private Long memberId;

    public GroupMemberId() {}

    public GroupMemberId(Long groupId, Long memberId) {
        this.groupId = groupId;
        this.memberId = memberId;
    }

    // equals() / hashCode() 구현 (필수)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupMemberId)) return false;
        GroupMemberId that = (GroupMemberId) o;
        return Objects.equals(groupId, that.groupId) &&
            Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, memberId);
    }

}
