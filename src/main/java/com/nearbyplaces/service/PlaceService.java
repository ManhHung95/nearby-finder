package com.nearbyplaces.service;

import com.nearbyplaces.dto.NearbyPlacesResponse;
import com.nearbyplaces.dto.PlaceDto;
import com.nearbyplaces.entity.Place;
import com.nearbyplaces.repository.PlaceRepository;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    @Cacheable(value = "nearbyPlaces", key = "#lat + '_' + #lng + '_' + #radius + '_' + #type + '_' + #keyword + '_' + #limit")
    public NearbyPlacesResponse findNearbyPlaces(Double lat, Double lng, Integer radius, 
                                               String type, String keyword, Integer limit) {
        
        try {
            // Use the geospatial query to find nearby places
            List<Place> nearbyPlaces = placeRepository.findNearbyPlacesSimple(lat, lng, radius, type, keyword, limit);
            
            // Convert to DTOs and calculate distances
            List<PlaceDto> places = nearbyPlaces.stream()
                .map(place -> {
                    try {
                        Double distance = placeRepository.getDistanceToPlace(place.getId(), lat, lng);
                        return mapToPlaceDto(place, distance);
                    } catch (Exception e) {
                        // If distance calculation fails, return without distance
                        return mapToPlaceDto(place, null);
                    }
                })
                .collect(Collectors.toList());

            NearbyPlacesResponse.CenterDto center = new NearbyPlacesResponse.CenterDto(lat, lng);
            
            return new NearbyPlacesResponse(center, radius, places.size(), places);
        } catch (Exception e) {
            // Fallback: if geospatial query fails, return empty result
            NearbyPlacesResponse.CenterDto center = new NearbyPlacesResponse.CenterDto(lat, lng);
            return new NearbyPlacesResponse(center, radius, 0, new ArrayList<>());
        }
    }

    public Optional<PlaceDto> findPlaceById(UUID id) {
        return placeRepository.findById(id)
            .map(this::mapToPlaceDto);
    }

    public long countPlaces() {
        return placeRepository.count();
    }

    private PlaceDto mapToPlaceDto(Place place) {
        Point location = place.getLocation();
        return new PlaceDto(
            place.getId(),
            place.getName(),
            place.getType(),
            place.getAddress(),
            location.getY(), // latitude
            location.getX(), // longitude
            place.getRating(),
            null // no distance for this simple implementation
        );
    }

    private PlaceDto mapToPlaceDto(Place place, Double distance) {
        Point location = place.getLocation();
        return new PlaceDto(
            place.getId(),
            place.getName(),
            place.getType(),
            place.getAddress(),
            location.getY(), // latitude
            location.getX(), // longitude
            place.getRating(),
            distance
        );
    }
}