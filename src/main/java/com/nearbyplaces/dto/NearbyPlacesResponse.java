package com.nearbyplaces.dto;

import java.util.List;

public class NearbyPlacesResponse {
    private CenterDto center;
    private Integer radius;
    private Integer count;
    private List<PlaceDto> items;

    // Constructors
    public NearbyPlacesResponse() {}

    public NearbyPlacesResponse(CenterDto center, Integer radius, Integer count, List<PlaceDto> items) {
        this.center = center;
        this.radius = radius;
        this.count = count;
        this.items = items;
    }

    // Getters and Setters
    public CenterDto getCenter() { return center; }
    public void setCenter(CenterDto center) { this.center = center; }

    public Integer getRadius() { return radius; }
    public void setRadius(Integer radius) { this.radius = radius; }

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }

    public List<PlaceDto> getItems() { return items; }
    public void setItems(List<PlaceDto> items) { this.items = items; }

    public static class CenterDto {
        private Double lat;
        private Double lng;

        public CenterDto() {}

        public CenterDto(Double lat, Double lng) {
            this.lat = lat;
            this.lng = lng;
        }

        public Double getLat() { return lat; }
        public void setLat(Double lat) { this.lat = lat; }

        public Double getLng() { return lng; }
        public void setLng(Double lng) { this.lng = lng; }
    }
}