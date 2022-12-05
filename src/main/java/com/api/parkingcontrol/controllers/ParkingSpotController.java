package com.api.parkingcontrol.controllers;
import com.api.parkingcontrol.dtos.ParkingSpoTDtoResponse;
import com.api.parkingcontrol.dtos.ParkingSpotDtoRequest;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")

public class ParkingSpotController {
    final ParkingSpotServices parkingSpotServices;

    public ParkingSpotController(ParkingSpotServices parkingSpotServices) {
        this.parkingSpotServices = parkingSpotServices;
    }

    @GetMapping("/list")
    public List<ParkingSpotModel> findAllParkingSpot() {
        return  parkingSpotServices.findAllParkingSpot();
    }

//    @GetMapping("/list/{block}")
//    public List<ParkingSpotModel> findAllParkingSpotBlock(@PathVariable("block") String block) {
//        return  parkingSpotServices.findAllParkingSpotByBlock(block);
//    }

    @GetMapping("/list/{block}")
    public List<ParkingSpotModel> findParkingSpotBlock(@PathVariable("block") String block) {
        return  parkingSpotServices.findPSMblocks(block);
    }

    @GetMapping
    public ResponseEntity<Page<ParkingSpoTDtoResponse>> getAllParkingSpots
            (@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return  ResponseEntity.status(HttpStatus.OK).body(parkingSpotServices.findAll(pageable));
    }

    @GetMapping("/listBlock")
    public List<String> findAllBlock() {
        return  parkingSpotServices.findAllBlock();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpoTDtoResponse> getOneParkingSpot(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.ok(parkingSpotServices.findById(id));
    }
    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDtoRequest parkingSpotDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotServices.savePSM(parkingSpotDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> DeleteParkingSpot(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotServices.delete(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpoTDtoResponse> updateParkingSpot(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ParkingSpotDtoRequest parkingSpotDto) {

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotServices.update(id,parkingSpotDto));

    }

    }

