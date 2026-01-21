package com.nearbyplaces.repository;

import com.nearbyplaces.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<Place, UUID> {

    @Query("SELECT p FROM Place p")
    List<Place> findAllPlaces();

    // Use a simpler native query that returns only Place entities
    @Query(value = "SELECT * FROM places p " +
        "WHERE ST_DWithin(p.location, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)\\:\\:geography, :radius) " +
        "AND (:type IS NULL OR p.type = :type) " +
        "AND (:keyword IS NULL OR p.name ILIKE CONCAT('%', :keyword, '%')) " +
        "ORDER BY ST_Distance(p.location, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)\\:\\:geography) ASC " +
        "LIMIT :limit", nativeQuery = true)
    List<Place> findNearbyPlacesSimple(
        @Param("lat") Double lat,
        @Param("lng") Double lng,
        @Param("radius") Integer radius,
        @Param("type") String type,
        @Param("keyword") String keyword,
        @Param("limit") Integer limit
    );

    // Query to get distance for a specific place
    @Query(value = "SELECT ST_Distance(p.location, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)\\:\\:geography) " +
        "FROM places p WHERE p.id = :placeId", nativeQuery = true)
    Double getDistanceToPlace(
        @Param("placeId") UUID placeId,
        @Param("lat") Double lat,
        @Param("lng") Double lng
    );
}