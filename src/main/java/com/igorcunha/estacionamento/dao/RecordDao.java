package com.igorcunha.estacionamento.dao;

import com.igorcunha.estacionamento.model.entities.ParkingRecords;
import com.igorcunha.estacionamento.model.entities.Vehicle;
import com.igorcunha.estacionamento.model.enums.RecordStatus;
import com.igorcunha.estacionamento.util.DB;
import com.igorcunha.estacionamento.util.DbException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RecordDao implements RecordDaoInterface {

    private Connection conn;

    public RecordDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertRecord(ParkingRecords record) {
        PreparedStatement st = null;
        Vehicle vehicle = record.getVehicle();
        try {
            st = conn.prepareStatement("INSERT INTO parkingRecord (vehiclePlate, entryTime, status) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, vehicle.getPlate());
            st.setTimestamp(2, Timestamp.valueOf(record.getEntryTime()));
            st.setString(3, RecordStatus.ACTIVE.name());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    vehicle.setVehicleId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error while inserting vehicle");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void updateRecordExitTime(ParkingRecords record) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE parkingRecord SET exitTime=? WHERE vehiclePlate=? AND status = 'ACTIVE'");
            st.setTimestamp(1, Timestamp.valueOf(record.getExitTime()));
            st.setString(2, record.getVehicle().getPlate());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void finishRecord(ParkingRecords record) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE parkingRecord SET status=?, price=? WHERE vehiclePlate=? AND status = 'ACTIVE'");
            st.setString(1, RecordStatus.FINISHED.name());
            st.setDouble(2, record.getPrice());
            st.setString(3, record.getVehicle().getPlate());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("Warning: No records were updated. The record may not exist or status is not ACTIVE.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteRecordByPlate(String plate) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM parkingRecord WHERE vehiclePlate = ?");
            st.setString(1, plate);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public List<ParkingRecords> findAllRecords() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<ParkingRecords> records = new ArrayList<>();
        try {
            st = conn.prepareStatement("select * from vehicles join parkingRecord on vehicles.vehiclePlate = parkingRecord.vehiclePlate;");
            rs = st.executeQuery();
            while (rs.next()) {
                ParkingRecords record = new ParkingRecords();
                record.setRecordId(rs.getInt("registerID"));
                Vehicle vehicle = new Vehicle();
                vehicle.setPlate(rs.getString("vehiclePlate"));
                vehicle.setModel(rs.getString("vehicleModel"));
                record.setVehicle(vehicle);
                record.setEntryTime(rs.getTimestamp("entryTime").toLocalDateTime());
                if (rs.getTimestamp("exitTime") != null) {
                    record.setExitTime(rs.getTimestamp("exitTime").toLocalDateTime());
                }
                if (rs.getString("status") != null) {
                    record.setStatus(RecordStatus.valueOf(rs.getString("status")));
                }
                records.add(record);
            }
            return records;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }


    }

    @Override
    public ParkingRecords findActiveRecord(Vehicle vehicle) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT parkingRecord.*, vehicles.vehicleModel FROM parkingRecord join vehicles on vehicles.vehiclePlate = parkingRecord.vehiclePlate WHERE parkingRecord.vehiclePlate = ? AND status = 'ACTIVE'");
            st.setString(1, vehicle.getPlate());
            rs = st.executeQuery();
            if (rs.next()) {
                ParkingRecords record = new ParkingRecords();
                record.setRecordId(rs.getInt("registerID"));
                record.setVehicle(vehicle = new Vehicle(vehicle.getPlate(), rs.getString("vehicleModel")));
                record.setEntryTime(rs.getTimestamp("entryTime").toLocalDateTime());
                if (rs.getTimestamp("exitTime") != null) {
                    record.setExitTime(rs.getTimestamp("exitTime").toLocalDateTime());
                }
                if (rs.getString("status") != null) {
                    record.setStatus(RecordStatus.valueOf(rs.getString("status")));
                }
                return record;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    public LocalDateTime getEntryTime(String plate) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT entryTime FROM parkingRecord WHERE status = 'ACTIVE' AND vehiclePlate = ?");
            st.setString(1, plate);
            rs = st.executeQuery();
            if (rs.next()) {
                return rs.getTimestamp("entryTime").toLocalDateTime();
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

}
