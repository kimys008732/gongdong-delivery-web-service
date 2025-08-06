package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.GroupMemberId;
import com.example.demo.entity.GroupMember;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
        List<GroupMember> findByGroup_GroupId(Long groupId);
        Optional<GroupMember> findByGroup_GroupIdAndMember_Id(Long groupId, Long memberId);
    }

