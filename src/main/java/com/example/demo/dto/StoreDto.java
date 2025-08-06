package com.example.demo.dto;

import com.example.demo.entity.Store;

public class StoreDto {
    private Long storeId;
    private String name;
    private Integer minOrderAmount;
    private Integer deliveryFee;

    // 1) 기본 생성자 (직렬화/역직렬화용)
    public StoreDto() {}

    // 2) 전체 필드를 초기화하는 생성자
    public StoreDto(Long storeId, String name, Integer minOrderAmount, Integer deliveryFee) {
        this.storeId        = storeId;
        this.name           = name;
        this.minOrderAmount = minOrderAmount;
        this.deliveryFee    = deliveryFee;
    }

    // 3) Entity → DTO 변환용 생성자
    public StoreDto(com.example.demo.entity.Store s) {
        this.storeId        = s.getId();    
        this.name           = s.getName();
        this.minOrderAmount = s.getMinOrderAmount();
        this.deliveryFee    = s.getDeliveryFee();
    }

    // Getter / Setter
    public Long getStoreId() {
        return storeId;
    }
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinOrderAmount() {
        return minOrderAmount;
    }
    public void setMinOrderAmount(Integer minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public Integer getDeliveryFee() {
        return deliveryFee;
    }
    public void setDeliveryFee(Integer deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
}