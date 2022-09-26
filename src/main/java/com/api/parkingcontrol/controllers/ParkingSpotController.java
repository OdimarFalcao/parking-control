package com.api.parkingcontrol.controllers;
import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.exception.BadRequestException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotServices;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")

public class ParkingSpotController {
    final ParkingSpotServices parkingSpotServices;

    public ParkingSpotController(ParkingSpotServices parkingSpotServices) {
        this.parkingSpotServices = parkingSpotServices;
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotServices.save(parkingSpotDto));
    }

    @GetMapping
    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotServices.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpotModel> getOneParkingSpot(@PathVariable(value = "id") UUID id) {

        return ResponseEntity.ok(parkingSpotServices.findById(id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeleteParkingSpot(@PathVariable(value = "id") UUID id) {
        // Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotServices.findById(id);
//        if(!parkingSpotModelOptional.isPresent()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found!");
//        }
        // parkingSpotServices.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot delete successfully");

    }

    @PutMapping("/{id}" +
            "")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDto parkingSpotDto) {
//        try {
//            Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotServices.findById(id);
////        if (!parkingSpotModelOptional.isPresent()) {
////            return ResponseEntity.ok(parkingSpotServices.findById(id));
////        }
//            var parkingSpotModel = new ParkingSpotModel();
//            BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
//            parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
//            parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());
//            return ResponseEntity.status(HttpStatus.OK).body(parkingSpotServices.save(parkingSpotModel));
//        } catch(BadRequestException ex){
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//            }
        return null;
    }

    }

