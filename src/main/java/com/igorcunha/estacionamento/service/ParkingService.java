package com.igorcunha.estacionamento.service;

import com.igorcunha.estacionamento.dao.DaoFactory;
import com.igorcunha.estacionamento.dao.RecordDao;
import com.igorcunha.estacionamento.dao.VehicleDao;
import com.igorcunha.estacionamento.model.entities.ParkingRecords;
import com.igorcunha.estacionamento.model.entities.Vehicle;
import com.igorcunha.estacionamento.util.DB;
import com.igorcunha.estacionamento.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ParkingService {

    RecordDao recordDao = DaoFactory.createRecordDao();
    VehicleDao vehicleDao = DaoFactory.createVehicleDao();

    public String plateValidator(Scanner sc) {
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

    public double calculateFinalPrice(ParkingRecords record) {
        if (record == null || record.getExitTime() == null) {
            System.out.println("Error: Record or exit time is null. Cannot calculate price.");
            return 0;
        }
        long durationInMinutes = Duration.between(record.getEntryTime(), record.getExitTime()).toMinutes();
        long durationInHours = durationInMinutes / 60;

        if (durationInMinutes % 60 != 0) {
            durationInHours++;
        }
        double price;
        if (durationInHours < 1) {
            price = 0;
        } else {
            price = durationInHours * 8;
        }
        System.out.println(String.format("R$ %.2f", price));
        return price;
    }
    public boolean isVehicleAlreadyRegistered(Vehicle vehicle) {
        return vehicleDao.findVehicleByPlate(vehicle.getPlate()) != null;
    }

    public LocalDateTime exitTimeVerifier(Scanner sc, String plate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime entryTime = recordDao.getEntryTime(plate);
        LocalDateTime exitTime = null;
        try {
            exitTime = LocalDateTime.parse(sc.nextLine(), formatter);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please enter the date and time in the format dd/MM/yyyy HH:mm");
            return exitTimeVerifier(sc, plate);
        }
        boolean isValid = exitTime.isAfter(entryTime);
        while (true) {
            if (isValid) {
                return exitTime;
            }else{
                System.out.println("Exit time must be after entry time. Please enter a valid exit time in the format dd/MM/yyyy HH:mm");
                try {
                    exitTime = LocalDateTime.parse(sc.nextLine(), formatter);
                } catch (Exception e) {
                    System.out.println("Invalid date format. Please enter the date and time in the format dd/MM/yyyy HH:mm");
                    return exitTimeVerifier(sc, plate);
                }
            }
            isValid = exitTime.isAfter(entryTime);
        }
    }
    public void handleVehicleEntry(String plate, String model) {
        Connection conn = DB.getConnection();
        try {
            conn.setAutoCommit(false);

            Vehicle vehicle = new Vehicle(plate, model);

            if (!isVehicleAlreadyRegistered(vehicle)) {
                vehicleDao.insertVehicle(vehicle);
            } else {
                vehicle = vehicleDao.findVehicleByPlate(plate);
                System.out.println("Vehicle already registered, skipping insert.");
            }

            if (!isVehicleAlreadyParked(vehicle)) {
                ParkingRecords record = new ParkingRecords(vehicle);
                recordDao.insertRecord(record);
                System.out.println("Vehicle parked successfully!\n" + record);
            } else {
                System.out.println("Vehicle already parked.\n" + recordDao.findActiveRecord(vehicle));
            }

            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DbException("Rollback failed: " + ex.getMessage());
            }
            throw new DbException("Transaction failed: " + e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
