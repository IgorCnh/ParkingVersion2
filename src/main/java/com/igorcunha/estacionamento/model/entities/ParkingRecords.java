package com.igorcunha.estacionamento.model.entities;

import com.igorcunha.estacionamento.model.enums.RecordStatus;

import java.time.LocalDateTime;

public class ParkingRecords {
    private int recordId;
    private Vehicle vehicle;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private RecordStatus status;
    private double price;

    public ParkingRecords(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.entryTime = LocalDateTime.now();
        this.status = RecordStatus.ACTIVE;
    }

    public ParkingRecords() {
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "RECORD DATA: \n" +
                "Vehicle: " + vehicle.getPlate() + " - " + vehicle.getModel() +
                ", Entry Time: " + entryTime +
                ", Exit Time: " + exitTime +
                ", Price: " + price +
                ", Status: " + status;
    }
}
