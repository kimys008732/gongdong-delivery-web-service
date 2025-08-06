package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.StoreRepository;
import com.example.demo.dto.StoreDto;

@RestController
@RequestMapping("/api/stores")
@CrossOrigin(origins = "https://localhost:3000")
public class StoreController {

    private final StoreRepository storeRepo;

    public StoreController(StoreRepository storeRepo) {
        this.storeRepo = storeRepo;
    }

@GetMapping
public List<StoreDto> listByCategory(@RequestParam String category) {
        return storeRepo.findAllByCategory(category)
                        .stream()
                        .map(StoreDto::new)
                        .collect(Collectors.toList());
    }
}
