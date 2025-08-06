package com.example.demo.dto;

import java.util.List;



public class GroupDetailDto {
    // 그룹 정보
    private Long groupId;
    private String destination;
    private List<MemberInfo> members;
    private Double destinationLat;
    private Double destinationLng;

    public Long getGroupId() { return groupId; }
    public String getDestination() { return destination; }
    public List<MemberInfo> getMembers() { return members; }
    public Double getDestinationLat() { return destinationLat; }
    public Double getDestinationLng() { return destinationLng; }

    public void setGroupId(Long groupId)      { this.groupId = groupId; }
    public void setDestination(String dest)   { this.destination = dest; }
    public void setMembers(List<MemberInfo> m){ this.members = m; }
    public void setDestinationLat(Double destinationLat) { this.destinationLat = destinationLat;}
    public void setDestinationLng(Double destinationLng) { this.destinationLng = destinationLng;}

    // 가게
    private Long storeId;
    private String storeName;
    private Integer minOrderAmount;
    private Integer deliveryFee;

    public Long getStoreId()        { return storeId; }
    public String getStoreName()    { return storeName; }
    public Integer getMinOrderAmount() { return minOrderAmount; }
    public Integer getDeliveryFee() { return deliveryFee; }

    public void setStoreId(Long storeId)                { this.storeId = storeId; }
    public void setStoreName(String storeName)          { this.storeName = storeName; }
    public void setMinOrderAmount(Integer minOrderAmount) { this.minOrderAmount = minOrderAmount; }
    public void setDeliveryFee(Integer deliveryFee)     { this.deliveryFee = deliveryFee; }

    // 멤버 정보 클래스
    public static class MemberInfo {
    private Long memberId;
    private String name;
    private boolean owner;

    public Long getMemberId() { return memberId; }
    public String getName() { return name; }
    public boolean isOwner() { return owner; }

    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public void setName(String name) { this.name = name; }
    public void setOwner(boolean owner) { this.owner = owner; }
    }
    
}
