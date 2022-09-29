package com.api.parkingcontrol.repositores;

import com.api.parkingcontrol.models.ParkingSpotModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID> {



    boolean existsByLicensePlateCar(String licensePlateCar);
    boolean existsByParkingSpotNumber(String parkingSpotNumber);
    boolean existsByApartmentAndBlock(String apartment, String block);


    @Query(nativeQuery = true, value = "SELECT *FROM TB_PARKING_SPOT")
    List<ParkingSpotModel> findAllQuery();

    @Query(nativeQuery = true, value = "SELECT block FROM TB_PARKING_SPOT")
    List<String> findAllQueryBlock();

    @Query(nativeQuery = true, value = "SELECT  *FROM TB_PARKING_SPOT WHERE block=?1 ")
    List<ParkingSpotModel> findAllParkingSpot(String block);


}