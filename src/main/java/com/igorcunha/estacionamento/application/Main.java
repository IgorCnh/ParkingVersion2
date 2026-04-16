package com.igorcunha.estacionamento.application;

import com.igorcunha.estacionamento.dao.DaoFactory;
import com.igorcunha.estacionamento.dao.RecordDao;
import com.igorcunha.estacionamento.dao.VehicleDao;
import com.igorcunha.estacionamento.model.entities.ParkingRecords;
import com.igorcunha.estacionamento.model.entities.Vehicle;
import com.igorcunha.estacionamento.model.enums.RecordStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        VehicleDao vehicleDao = DaoFactory.createVehicleDao();
        RecordDao recordDao = DaoFactory.createRecordDao();
        Vehicle vehicle = vehicleDao.findVehicleByPlate("HJL3H89");
        
        if (vehicle == null) {
            vehicle = new Vehicle("HJL3H89", "Fiat Kronos");
            vehicleDao.insertVehicle(vehicle);
            System.out.println("New vehicle created successfully.");
        } else {
            System.out.println("Vehicle already exists in the database.");
        }



//        var existingRecords = recordDao.findAllRecords();
//        final Vehicle finalVehicle = vehicle;
//
//        // Verification logic for Record 1
//        boolean record1Exists = existingRecords.stream()
//                .anyMatch(r -> r.getVehicle().getPlate().equals(finalVehicle.getPlate()) &&
//                             r.getStatus() == RecordStatus.ACTIVE);
//
//        ParkingRecords record1 = new ParkingRecords(vehicle, LocalDateTime.now().plusHours(8), RecordStatus.ACTIVE);
//
//        if (!record1Exists) {
//            recordDao.insertRecord(record1);
//            System.out.println("Parking record 2 created successfully.");
//        } else {
//            System.out.println("Parking record 2 already exists in the database.");
//            record1.setPrice(17.0);
//            record1.setExitTime(LocalDateTime.now().plusHours(10));
//            recordDao.updateRecordExit(record1);
//            System.out.println("Parking record 2 updated successfully.");
//        }

        List<ParkingRecords> records = recordDao.findAllRecords();
        records.forEach(System.out::println);

    }
}
