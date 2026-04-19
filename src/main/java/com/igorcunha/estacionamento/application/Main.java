package com.igorcunha.estacionamento.application;

import com.igorcunha.estacionamento.dao.DaoFactory;
import com.igorcunha.estacionamento.dao.RecordDao;
import com.igorcunha.estacionamento.dao.VehicleDao;
import com.igorcunha.estacionamento.model.entities.ParkingRecords;
import com.igorcunha.estacionamento.model.entities.Vehicle;
import com.igorcunha.estacionamento.model.enums.RecordStatus;
import com.igorcunha.estacionamento.service.ParkingService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ParkingService parkingService = new ParkingService();
        VehicleDao vehicleDao = DaoFactory.createVehicleDao();
        RecordDao recordDao = DaoFactory.createRecordDao();
        Scanner sc = new Scanner(System.in);
        int option = 0;
        System.out.println("Welcome to the Parking Lot Management System!");
        while (option != 5) {
            System.out.print("=========================//=========================\nSelect an option: \n1) - Park \n2) - Exit with a vehicle \n3) - Find All parked vehicles\n4) - Find all vehicle records \n5) - Exit Program\n=========================//=========================\nSelect one => ");
            try {
                option = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid option, please select a valid option.");
            }
            switch (option) {
                case 1:
                    System.out.println("Option 1 selected: Park \nType the vehicle plate: ");
                    String plate = parkingService.plateValidator(sc);
                    sc.nextLine();
                    System.out.println("Type the vehicle model: ");
                    String model = sc.nextLine();
                    System.out.println("Confirm plate: " + plate + "? (y/n): ");
                    String confirm = sc.nextLine();
                    if (!confirm.equalsIgnoreCase("y")) {
                        System.out.println("Registration cancelled.");
                        break;
                    }
                    parkingService.handleVehicleEntry(plate, model);
                    break;
                case 2:
                    System.out.println("Option 2 selected: Exit with a vehicle \nType the vehicle plate: ");
                    plate = parkingService.plateValidator(sc);
                    sc.nextLine();
                    vehicle = vehicleDao.findVehicleByPlate(plate);
                    ParkingRecords record = null;
                    if (vehicle == null) {
                        System.out.println("Vehicle with plate " + plate + " not found");
                    } else {
                        boolean parked = parkingService.isVehicleAlreadyParked(vehicle);
                        if (parked) {
                            record = recordDao.findActiveRecord(vehicle);
                            System.out.println("Vehicle is inside the parking! \nType the exit date and time in the format dd/MM/yyyy HH:mm: ");
                            LocalDateTime exitTime = parkingService.exitTimeVerifier(sc, plate);
                            record.setExitTime(exitTime);
                            recordDao.updateRecordExitTime(record);
                            System.out.println("Exit time succesfully saved! \nThe final price is: ");
                            double finalPrice = parkingService.calculateFinalPrice(record);
                            record.setPrice(finalPrice);
                            record.setStatus(RecordStatus.FINISHED);
                            recordDao.finishRecord(record);
                            System.out.println("Vehicle exited successfully! \n" + record);
                        } else {
                            System.out.println("Vehicle not parked. Please check the plate and try again.");
                        }
                    }
                    break;
                case 3:
                    System.out.println("Option 3 selected: Find All parked vehicles\nParked vehicles DATA: ");
                    vehicleDao.findAllActiveVehicles().forEach(x -> System.out.println("Vehicle: " + x));
                    break;
                case 4:
                    System.out.print("Option 4 selected: Find all vehicle records: \nType the vehicle plate: ");
                    plate = parkingService.plateValidator(sc);
                    vehicle = vehicleDao.findVehicleByPlate(plate);
                    if (vehicle == null) {
                        System.out.println("Vehicle with plate " + plate + " not found");
                    }else {
                        System.out.println("Records: ");
                        vehicleDao.findAllVehicleRecords(vehicle).forEach(System.out::println);
                    }
                    break;
                case 5:
                    System.out.println("Option 5 selected: Exit Program\nExiting...");
                    break;
                default:
                    System.out.println("Invalid option, please select a valid option.");
            }
        }
    }
}
