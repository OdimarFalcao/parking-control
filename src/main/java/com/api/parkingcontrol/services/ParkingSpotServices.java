package com.api.parkingcontrol.services;

import com.api.parkingcontrol.exception.BadRequestException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositores.ParkingSpotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpotServices {

    final   ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotServices (ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }
    @Transactional
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        return parkingSpotRepository.save(parkingSpotModel);
    }

    public boolean existsByLicensePlateCar(String LicensePlateCar){
        return parkingSpotRepository.existsByLicensePlateCar(LicensePlateCar);
    }

    public  boolean existsByParkingSpotNumber(String parkingSpotNumber){
        return  parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment,String block){
        return  parkingSpotRepository.existsByApartmentAndBlock(apartment,block);
    }
    public Page<ParkingSpotModel> findAll(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable);
    }

    public Optional<ParkingSpotModel> findById(UUID id) {
        return Optional.ofNullable(parkingSpotRepository.findById(id).
                orElseThrow(() -> new BadRequestException("Parking Spot Model Not Found!")));
    }


    @Transactional
    public void delete(ParkingSpotModel parkingSpotModel) {
        parkingSpotRepository.delete(parkingSpotModel);
    }

    public List<ParkingSpotModel> findAllParkingSpot() {
        return parkingSpotRepository.findAllQuery();
    }

    public List<String> findAllBlock() {
        return parkingSpotRepository.findAllQueryBlock();
    }

    public List<ParkingSpotModel> findAllParkingSpotByBlock(String block) {
        return parkingSpotRepository.findAllParkingSpot(block);
    }
}
