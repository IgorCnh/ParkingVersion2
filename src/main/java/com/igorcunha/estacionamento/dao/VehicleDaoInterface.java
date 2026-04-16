package com.igorcunha.estacionamento.dao;

import com.igorcunha.estacionamento.model.entities.ParkingRecords;
import com.igorcunha.estacionamento.model.entities.Vehicle;

import java.util.List;

public interface VehicleDaoInterface {
    void insertVehicle(Vehicle vehicle);
    void updateVehicle(Vehicle vehicle);
    void deleteVehicleByPlate(String plate);
    Vehicle findVehicleByPlate(String plate);
    List<Vehicle> findAllVehicles();
    List<ParkingRecords> findAllVehicleRecords(Vehicle vehicle);
}
