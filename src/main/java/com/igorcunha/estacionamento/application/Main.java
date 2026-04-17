package com.igorcunha.estacionamento.application;

import com.igorcunha.estacionamento.dao.DaoFactory;
import com.igorcunha.estacionamento.dao.RecordDao;
import com.igorcunha.estacionamento.dao.VehicleDao;
import com.igorcunha.estacionamento.model.entities.ParkingRecords;
import com.igorcunha.estacionamento.model.entities.Vehicle;
import com.igorcunha.estacionamento.service.ParkingService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ParkingService parkingService = new ParkingService();
        VehicleDao vehicleDao = DaoFactory.createVehicleDao();
        RecordDao recordDao = DaoFactory.createRecordDao();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the parking lot management system! \nSelect an option: \n1) - Register a new Vehicle \n2) - Exit with a vehicle \n3) - Find All Vehicles inside parking \n4) - Exit Program");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                System.out.println("Option 1 selected: Register a new Vehicle \nType the vehicle plate: ");
                String plate = parkingService.signValidator(sc);
                sc.nextLine();
                System.out.println("Type the vehicle model: ");
                String model = sc.nextLine();
                Vehicle vehicle = new Vehicle(plate, model);
                if(parkingService.isVehicleAlreayRegistered(vehicle)){
                    System.out.println("Vehicle with plate " + plate + " already registered");
                }else{
                    System.out.println("Vehicle with plate " + plate + " registered successfully");
                    vehicleDao.insertVehicle(vehicle);
                }
                System.out.println("Entering vehicle in parking lot...");
                if(!parkingService.isVehicleAlreadyParked(vehicle)){
                    ParkingRecords parkingRecords = new ParkingRecords(vehicle);
                    recordDao.insertRecord(parkingRecords);
                    System.out.println("Vehicle Parked Successfully! \n" + recordDao.findActiveRecord(vehicle));
                }else{
                    System.out.println("Vehicle Already Parked\n" + recordDao.findActiveRecord(vehicle));
                }

        }
    }
}
