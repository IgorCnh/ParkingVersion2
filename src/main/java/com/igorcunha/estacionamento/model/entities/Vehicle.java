package com.igorcunha.estacionamento.model.entities;

public class Vehicle {
    private int vehicleId;
    private String plate;
    private String model;

    public String getPlate() {
        return plate.toUpperCase();
    }

    public void setPlate(String plate) {
        this.plate = plate.toUpperCase();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    private int id;
    public Vehicle(String plate, String model) {
        this.plate = plate.toUpperCase();
        this.model = model;
    }
    public Vehicle() {
    }

    @Override
    public String toString() {
        return "Vehicle data: \nId:" + vehicleId + " \nplate: " + plate + " \nmodel: " + model;
    }


}
