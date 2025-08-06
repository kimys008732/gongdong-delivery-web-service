package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "stores")
public class Store {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")   
    private Long id;
    
    private String name;
    private String address;
    private String category;
    
    @Column(name = "min_order_amount")  
    private Integer minOrderAmount;


    @Column(name = "delivery_fee")
    private Integer deliveryFee;
            
    @Column(name = "latitude",  nullable = true)
    private Double latitude;

    @Column(name = "longitude", nullable = true)
    private Double longitude;

    // 기본 생성자
    public Store() {}

    // getter / setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getMinOrderAmount() { return minOrderAmount; }
    public void setMinOrderAmount(Integer minOrderAmount) { this.minOrderAmount = minOrderAmount; }

    public Integer getDeliveryFee() { return deliveryFee; }
    public void setDeliveryFee(Integer deliveryFee) { this.deliveryFee = deliveryFee; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
