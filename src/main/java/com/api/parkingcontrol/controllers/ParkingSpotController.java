package com.api.parkingcontrol.controllers;
import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotServices;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController{
    final ParkingSpotServices parkingSpotServices;

    public ParkingSpotController(ParkingSpotServices parkingSpotServices) {
        this.parkingSpotServices = parkingSpotServices;
    }
    @GetMapping("/list")
    public List<ParkingSpotModel> findAllParkingSpot() {
       return  parkingSpotServices.findAllParkingSpot();
    }

    @GetMapping("/list/{block}")
    public List<ParkingSpotModel> findAllParkingSpotBlock(@PathVariable("block") String block) {
        return  parkingSpotServices.findAllParkingSpotByBlock(block);
    }

    @GetMapping("/listBlock")
    public List<String> findAllBlock() {

        return  parkingSpotServices.findAllBlock();
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
//ToDo : Transferir validações para classe serviço(Dica; Na classe serviço receber objetoDto)



        if (parkingSpotServices.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is already in use !");
        }
        if (parkingSpotServices.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot Number is already in use !");
        }
        if (parkingSpotServices.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block !");
        }
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotServices.save(parkingSpotModel));
    }

    @GetMapping

    public ResponseEntity<List<ParkingSpotModel>> getGetAllParkingSpots() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotServices.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id) {
        try{
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotServices.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
        }catch(ParkingSpotModelNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Parking Spot Not Found", ex);
        }
        }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeleteParkingSpot(@PathVariable(value= "id")UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotServices.findById(id);
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }
        parkingSpotServices.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDto parkingSpotDto){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotServices.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotServices.save(parkingSpotModel));
    }



}
