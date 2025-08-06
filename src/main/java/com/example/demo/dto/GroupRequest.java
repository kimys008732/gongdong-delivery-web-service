package com.example.demo.dto;

public class GroupRequest {
    public String name;
    public String category;
    public String privacy;
    public Integer maxMembers;
    // ↓ 장소 관련
    public String placeId;
    public String destinationAddress;
    public Double destinationLat;
    public Double destinationLng;
}
