package com.nearbyplaces.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public class PlaceDto {
    private UUID id;
    private String name;
    private String type;
    private String address;
    private Double lat;
    private Double lng;
    private BigDecimal rating;
    
    @JsonProperty("distance_m")
    private Double distanceM;

    // Constructors
    public PlaceDto() {}

    public PlaceDto(UUID id, String name, String type, String address, 
                   Double lat, Double lng, BigDecimal rating, Double distanceM) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.rating = rating;
        this.distanceM = distanceM;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }

    public Double getLng() { return lng; }
    public void setLng(Double lng) { this.lng = lng; }

    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }

    public Double getDistanceM() { return distanceM; }
    public void setDistanceM(Double distanceM) { this.distanceM = distanceM; }
}