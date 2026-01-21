package com.nearbyplaces.controller;

import com.nearbyplaces.dto.NearbyPlacesResponse;
import com.nearbyplaces.dto.PlaceDto;
import com.nearbyplaces.service.PlaceService;
import javax.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/places")
@Validated
@CrossOrigin(origins = "*")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @GetMapping("/nearby")
    public ResponseEntity<NearbyPlacesResponse> getNearbyPlaces(
            @RequestParam @NotNull @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0") Double lat,
            @RequestParam @NotNull @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0") Double lng,
            @RequestParam(defaultValue = "3000") @Min(1) @Max(20000) Integer radius,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "50") @Min(1) @Max(200) Integer limit) {

        NearbyPlacesResponse response = placeService.findNearbyPlaces(lat, lng, radius, type, q, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDto> getPlaceById(@PathVariable UUID id) {
        return placeService.findPlaceById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        try {
            long count = placeService.countPlaces();
            return ResponseEntity.ok("Database connection OK. Found " + count + " places.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Database error: " + e.getMessage());
        }
    }
}