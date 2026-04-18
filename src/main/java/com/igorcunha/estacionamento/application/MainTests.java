package com.igorcunha.estacionamento.application;

import com.igorcunha.estacionamento.dao.DaoFactory;
import com.igorcunha.estacionamento.dao.RecordDao;
import com.igorcunha.estacionamento.dao.VehicleDao;
import com.igorcunha.estacionamento.model.entities.Vehicle;
import com.igorcunha.estacionamento.service.ParkingService;

import java.time.LocalDateTime;
import java.util.Scanner;

public class MainTests {
    public static void main(String[] args) {
        VehicleDao vehicleDao = DaoFactory.createVehicleDao();
        RecordDao recordDao = DaoFactory.createRecordDao();
        Vehicle vehicle = vehicleDao.findVehicleByPlate("hxj7l87");
        
//       if (vehicle == null) {
//           vehicle = new Vehicle("hxj7l87", "Honda Civic");
//           vehicleDao.insertVehicle(vehicle);
//           System.out.println("New vehicle created successfully.");
//       } else {
//           System.out.println("Vehicle already exists in the database.");
//       }
//        Scanner sc = new Scanner(System.in);
//        ParkingService parkingService = new ParkingService();
//        String plate = parkingService.plateValidator(sc);
//        System.out.println("Validated plate: " + plate);



//       vehicleDao.deleteVehicleByPlate("lto2k25");
//       Vehicle Vehicle2 = vehicleDao.findVehicleByPlate("lto2k25");
//       if (Vehicle2 == null) {
//           System.out.println("Vehicle does not exist in the database.");
//       }else{
//           System.out.println("Vehicle already exists in the database.");
//       }


//       var existingRecords = recordDao.findAllRecords();
//       final Vehicle finalVehicle = vehicle;
//
//       // Verification logic for Record 1
//       boolean record1Exists = existingRecords.stream()
//               .anyMatch(r -> r.getVehicle().getPlate().equals(finalVehicle.getPlate()) &&
//                            r.getStatus() == RecordStatus.ACTIVE);
//
//       ParkingRecords record1 = new ParkingRecords(vehicle, LocalDateTime.now().plusHours(8), RecordStatus.ACTIVE);
//
//       if (!record1Exists) {
//           recordDao.insertRecord(record1);
//           System.out.println("Parking record 2 created successfully.");
//       } else {
//           System.out.println("Parking record 2 already exists in the database.");
//           record1.setPrice(17.0);
//           record1.setExitTime(LocalDateTime.now().plusHours(10));
//           recordDao.updateRecordExit(record1);
//           System.out.println("Parking record 2 updated successfully.");
//      }

//        vehicleDao.deleteVehicleByPlate("lto2k26".toUpperCase());
//         Vehicle Vehicle2 = vehicleDao.findVehicleByPlate("hxj7l87".toUpperCase());
//         if (Vehicle2 == null) {

//        recordDao.findAllRecords().forEach(System.out::println);
//        ParkingService ps = new ParkingService();
//        LocalDateTime exit = ps.exitTimeVerifier(new Scanner(System.in), "hxj7l87");
//        System.out.println("Exit time: " + exit);

//        vehicleDao.deleteVehicleByPlate("HJX2T67");

    }
}
