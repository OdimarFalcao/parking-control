package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositores.ParkingSpotRepository;
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
        public List<ParkingSpotModel> findAll() {
        return parkingSpotRepository.findAll();
    }

    public Optional<ParkingSpotModel> findById(UUID id) {
        return parkingSpotRepository.findById(id);
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
