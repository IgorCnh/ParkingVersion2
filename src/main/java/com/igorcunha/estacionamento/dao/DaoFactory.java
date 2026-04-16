package com.igorcunha.estacionamento.dao;

import com.igorcunha.estacionamento.util.DB;

public class DaoFactory {
    public static VehicleDao createVehicleDao() {
        return new VehicleDao(DB.getConnection());
    }
    public static RecordDao createRecordDao() {
        return new RecordDao(DB.getConnection());
    }
}
