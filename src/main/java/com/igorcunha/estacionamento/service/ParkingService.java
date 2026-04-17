package com.igorcunha.estacionamento.service;

import com.igorcunha.estacionamento.dao.DaoFactory;
import com.igorcunha.estacionamento.dao.RecordDao;
import com.igorcunha.estacionamento.dao.VehicleDao;
import com.igorcunha.estacionamento.model.entities.ParkingRecords;
import com.igorcunha.estacionamento.model.entities.Vehicle;

import java.time.Duration;
import java.util.Scanner;

public class ParkingService {

    RecordDao recordDao = DaoFactory.createRecordDao();
    VehicleDao vehicleDao = DaoFactory.createVehicleDao();

    public String signValidator(Scanner sc) {
        String pattern = "^[A-Z]{3}[0-9][A-Z][0-9]{2}$";
        String plate = sc.next().toUpperCase();
        boolean isValid = plate.matches(pattern);
        while(true){
            if(isValid){
                return plate;
            }else{
                System.out.println("Invalid Plate, try again: ");
                plate = sc.next().toUpperCase();
            }
            isValid = plate.matches(pattern);
        }

    }
    public boolean isVehicleAlreadyParked(Vehicle vehicle) {
        return recordDao.findActiveRecord(vehicle) != null;
    }
    public double calculateFinalPrice(Vehicle vehicle) {
        ParkingRecords record = recordDao.findActiveRecord(vehicle);
        long duration = Duration.between(record.getEntryTime(), record.getExitTime()).toHours();
        if (Duration.between(record.getEntryTime(), record.getExitTime()).toMinutes() % 60 != 0) {
            duration++;
        }
        if (duration < 15) {
            return 0;
        }else{
            return duration * 8;
        }
    }
    public boolean isVehicleAlreayRegistered(Vehicle vehicle) {
        return vehicleDao.findVehicleByPlate(vehicle.getPlate()) != null;
    }
}
