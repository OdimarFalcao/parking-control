package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositores.ParkingSpotRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
//        public List<ParkingSpotModel> findAll() {
//        return parkingSpotRepository.findAll();
//    }
}
