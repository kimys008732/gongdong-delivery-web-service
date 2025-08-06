package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT g FROM Group g JOIN FETCH g.participants")
    List<Group> findAllWithMembers();
}
