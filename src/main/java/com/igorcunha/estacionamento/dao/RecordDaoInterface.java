package com.igorcunha.estacionamento.dao;

import com.igorcunha.estacionamento.model.entities.ParkingRecords;
import com.igorcunha.estacionamento.model.entities.Vehicle;

import java.util.List;

public interface RecordDaoInterface {
     void insertRecord(ParkingRecords record);
     void updateRecordExitTime(ParkingRecords record);
     void finishRecord(ParkingRecords record);
     void deleteRecordByPlate(String plate);
     List<ParkingRecords> findAllRecords();
     ParkingRecords findActiveRecord(Vehicle vehicle);
}
