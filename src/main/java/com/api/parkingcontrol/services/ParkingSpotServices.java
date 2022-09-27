package com.api.parkingcontrol.services;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.exception.GenericConflictException;
import com.api.parkingcontrol.exception.GenericExceptionNotFound;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositores.ParkingSpotRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public ParkingSpotModel savePSM(ParkingSpotDto parkingSpotDto) {

        ParkingSpotModel parkingSpotModel = new ParkingSpotModel();

        if (existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
            throw new GenericConflictException("Conflict: License Plate Car is already in use !");
        }

        if (existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
            throw new GenericConflictException("Conflict: Parking Spot Number is already in use !");
        }
        if (existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
            throw new GenericConflictException("Conflict: Parking Spot already registered for this apartment/block !");
        }
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return parkingSpotRepository.save(parkingSpotModel);
    }

    @Transactional
    public ParkingSpotModel update(UUID id, ParkingSpotDto parkingSpotDto) {

            Optional<ParkingSpotModel> parkingSpotModelOptional = Optional.ofNullable(parkingSpotRepository.findById(id).
                    orElseThrow(() -> new GenericExceptionNotFound("Parking Spot Model Not Found!")));



        var parkingSpotModel = new ParkingSpotModel();
            BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
            parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
            parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());
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

    public ParkingSpotModel findById(UUID id) {
        return parkingSpotRepository.findById(id).
                orElseThrow(() -> new GenericExceptionNotFound("Parking Spot Model Not Found!"));
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
