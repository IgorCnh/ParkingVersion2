package com.igorcunha.estacionamento.dao;

import com.igorcunha.estacionamento.model.entities.ParkingRecords;
import com.igorcunha.estacionamento.model.entities.Vehicle;

import java.util.List;

public interface RecordDaoInterface {
     void insertRecord(ParkingRecords record);
     void updateRecordExit(ParkingRecords record);
     void deleteRecordByPlate(Vehicle vehicle);
     List<ParkingRecords> findAllRecords();
}
