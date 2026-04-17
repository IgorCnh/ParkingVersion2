package com.igorcunha.estacionamento.dao;

import com.igorcunha.estacionamento.model.entities.ParkingRecords;
import com.igorcunha.estacionamento.model.entities.Vehicle;
import com.igorcunha.estacionamento.model.enums.RecordStatus;
import com.igorcunha.estacionamento.util.DB;
import com.igorcunha.estacionamento.util.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao implements VehicleDaoInterface {

    private Connection conn;

    public VehicleDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertVehicle(Vehicle vehicle) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("INSERT INTO vehicles (vehiclePlate, vehicleModel) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, vehicle.getPlate());
            st.setString(2, vehicle.getModel());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()) {
                    int id = rs.getInt(1);
                    vehicle.setVehicleId(id);
                }
                DB.closeResultSet(rs);
            }
            else{
                throw new DbException("Unexpected error while inserting vehicle");
            }
            }catch (SQLException e) {
            throw new DbException(e.getMessage());
            }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE vehicles SET vehiclePlate=?, vehicleModel=? WHERE vehicleID=?");
            st.setString(1, vehicle.getPlate());
            st.setString(2, vehicle.getModel());
            st.setInt(3, vehicle.getVehicleId());
            st.executeUpdate();
        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteVehicleByPlate(String plate) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM vehicles WHERE vehiclePlate=?");
            st.setString(1, plate);
            st.executeUpdate();
        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Vehicle findVehicleByPlate(String plate) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM vehicles WHERE vehiclePlate=?");
            st.setString(1, plate);
            rs = st.executeQuery();
            if (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setPlate(rs.getString("vehiclePlate"));
                vehicle.setModel(rs.getString("vehicleModel"));
                vehicle.setVehicleId(rs.getInt("vehicleID"));
                return vehicle;
            }
            return null;
        }catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Vehicle> findAllVehicles() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            st = conn.prepareStatement("SELECT * FROM vehicles");
            rs = st.executeQuery();
            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setPlate(rs.getString("plate"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setVehicleId(rs.getInt("vehicleID"));
                vehicles.add(vehicle);
            }
            return vehicles;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
    @Override
    public List<ParkingRecords> findAllVehicleRecords(Vehicle vehicle) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<ParkingRecords> records = new ArrayList<>();
        try {
            st = conn.prepareStatement("SELECT parkingRecord.* FROM parkingRecord WHERE parkingRecord.vehiclePlate = ? ORDER BY entryTime");
            st.setString(1, vehicle.getPlate());
            rs = st.executeQuery();
            while (rs.next()) {
                ParkingRecords record = new ParkingRecords();
                record.setRecordId(rs.getInt("registerID"));
                record.setVehicle(vehicle);
                record.setEntryTime(rs.getTimestamp("entryTime").toLocalDateTime());
                if (rs.getTimestamp("exitTime") != null) {
                    record.setExitTime(rs.getTimestamp("exitTime").toLocalDateTime());
                }
                if (rs.getString("status") != null) {
                    record.setStatus(RecordStatus.valueOf(rs.getString("status")));
                }
                if (rs.getDouble("price") != 0) {
                    record.setPrice(rs.getDouble("price"));
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
    public List<Vehicle> findAllActiveVehicles() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            st = conn.prepareStatement("SELECT DISTINCT vehicles.* FROM vehicles JOIN parkingRecord ON vehicles.vehiclePlate = parkingRecord.vehiclePlate WHERE parkingRecord.status = 'ACTIVE'");
            rs = st.executeQuery();
            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setPlate(rs.getString("vehiclePlate"));
                vehicle.setModel(rs.getString("vehicleModel"));
                vehicle.setVehicleId(rs.getInt("vehicleID"));
                vehicles.add(vehicle);
            }
            return vehicles;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
