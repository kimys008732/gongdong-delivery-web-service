package com.example.demo.controller;

import com.example.demo.entity.Group;
import com.example.demo.entity.Member;
import com.example.demo.entity.Store; 

import com.example.demo.repository.GroupRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.GroupService;
import com.example.demo.dto.CreateGroupDto;
import com.example.demo.dto.GroupDetailDto;
import com.example.demo.dto.GroupResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.stream.Collectors;
import java.security.Principal; 

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "https://localhost:3000")
public class GroupController {
    
    private final GroupRepository groupRepo;
    private final MemberRepository memberRepo;
    private final StoreRepository storeRepo;

    @Autowired 
    private GroupService groupService;

    @GetMapping("/{id}/details")
    public ResponseEntity<GroupDetailDto> detail(@PathVariable Long id) {
        GroupDetailDto dto = groupService.getGroupDetail(id);
        return ResponseEntity.ok(dto);
    }

    public GroupController(GroupRepository groupRepo, MemberRepository memberRepo, StoreRepository storeRepo) {
        this.groupRepo = groupRepo;
        this.memberRepo = memberRepo;
        this.storeRepo = storeRepo;
    }

    // 전체 조회
    @GetMapping
    public List<GroupResponse> list() {
        List<Group> groups = groupRepo.findAll();
        return groupRepo.findAll().stream()
            .map(g -> new GroupResponse(g)) 
            .collect(Collectors.toList());
    }

    // 그룹 생성
    @PostMapping
    public GroupResponse create(@RequestBody CreateGroupDto dto) {
        Member owner = memberRepo.findById(dto.getOwnerId())
                        .orElseThrow(() -> new RuntimeException("Member not found"));
        Store store = storeRepo.findById(dto.getStoreId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found"));
                    
        Group g = new Group();
        g.setName(dto.getName());
        g.setCategory(dto.getCategory());
        g.setPrivacy(dto.getPrivacy()); 

        g.setMaxMembers(dto.getMaxMembers());

        g.setDestination(dto.getDestinationAddress());
        g.setPlaceId(dto.getPlaceId());
        g.setDestinationAddress(dto.getDestinationAddress());
        g.setDestinationLat(dto.getDestinationLat());
        g.setDestinationLng(dto.getDestinationLng());
        
        g.setStore(store);
        g.setOwner(owner);  
        g.addOwner(owner);

        Group saved = groupRepo.save(g);
        return new GroupResponse(saved);
    }



    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        if (!groupRepo.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Group not found");
        }
        groupRepo.deleteById(id);
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<Void> leaveGroup(
            @PathVariable Long id,
            @AuthenticationPrincipal Principal principal // 스프링 시큐리티 사용 시
    ) {
        String name = principal.getName();
        Long memberId = memberRepo.findByname(name)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found"))
            .getId();
        groupService.leaveGroup(id, memberId);
        return ResponseEntity.noContent().build();
    }
}
