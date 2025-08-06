package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.GroupDetailDto;

import com.example.demo.entity.GroupMember;
import com.example.demo.entity.Group;
import com.example.demo.entity.Member;

import com.example.demo.repository.GroupMemberRepository;
import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class GroupService {

    @Autowired private GroupRepository groupRepo;
    @Autowired private GroupMemberRepository groupMemberRepo;
    @Autowired private MemberRepository memberRepo;

    public GroupDetailDto getGroupDetail(Long id) {
        Group group = groupRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("그룹을 찾을 수 없습니다. id=" + id));

        List<GroupMember> members = groupMemberRepo.findByGroup_GroupId(id);
        List<GroupDetailDto.MemberInfo> infos = members.stream().map(gm -> {
            GroupDetailDto.MemberInfo i = new GroupDetailDto.MemberInfo();
            i.setMemberId(gm.getMember().getId());
            i.setName(gm.getMember().getNickname());
            i.setOwner(gm.getMember().getId()
                        .equals(group.getOwner().getId()));
            return i;
        }).collect(Collectors.toList());

        GroupDetailDto dto = new GroupDetailDto();
        dto.setGroupId(id);
        dto.setDestination(group.getDestination());
        dto.setMembers(infos);
        dto.setDestinationLat(group.getDestinationLat());
        dto.setDestinationLng(group.getDestinationLng());
        return dto;
    }

    @Transactional
    public void deleteGroup(Long id) {
        if (!groupRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found");
        }
        groupRepo.deleteById(id);
    }

    @Transactional
    public void leaveGroup(Long groupId, Long memberId) {
        GroupMember gm = groupMemberRepo
            .findByGroup_GroupIdAndMember_Id(groupId, memberId)
            .orElseThrow(() -> 
                new ResponseStatusException(HttpStatus.NOT_FOUND, 
                                            "GroupMember not found"));

        groupMemberRepo.delete(gm);
    }
}