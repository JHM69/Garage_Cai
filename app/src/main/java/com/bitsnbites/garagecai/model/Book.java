package com.bitsnbites.garagecai.model;

import static com.bitsnbites.garagecai.model.Garage.BICYCLE_PER_CAR;
import static com.bitsnbites.garagecai.model.Garage.BIKE_PER_CAR;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    String id;
    String vehicleType;
    int bookingAmount;

    String status = "pending";

    String userId, garageId;


    int hourlyRate, number_of_vehicle = 0;

    long startTimestamp, endTimestamp;

    public Book(String id, String vehicleType, int bookingAmount, String status, String userId, String garageId, int hourlyRate, int number_of_vehicle, long startTimestamp, long endTimestamp) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.bookingAmount = bookingAmount;
        this.status = status;
        this.userId = userId;
        this.garageId = garageId;
        this.hourlyRate = hourlyRate;
        this.number_of_vehicle = number_of_vehicle;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
    }

    public Book() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getBookingAmount() {
        int amount = 0;
        int h = (int) ((endTimestamp-startTimestamp)/3600);
        if(Objects.equals(vehicleType, "car")){
            amount = h * hourlyRate;
        }else if(Objects.equals(vehicleType, "bike")){
            amount = ( h * hourlyRate ) / BIKE_PER_CAR;
        }else if(Objects.equals(vehicleType, "bicycle")){
            amount =  ( h * hourlyRate ) / BICYCLE_PER_CAR;
        }
        return amount;
    }

    public void setBookingAmount(int bookingAmount) {
        this.bookingAmount = bookingAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGarageId() {
        return garageId;
    }

    public void setGarageId(String garageId) {
        this.garageId = garageId;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getNumber_of_vehicle() {
        if(vehicleType.equals("car")) return number_of_vehicle;
        else if(vehicleType.equals("bike")) return number_of_vehicle*BIKE_PER_CAR;
        else if(vehicleType.equals("bicycle")) return number_of_vehicle*BICYCLE_PER_CAR;
        return number_of_vehicle;
    }

    public void setNumber_of_vehicle(int number_of_vehicle) {
        this.number_of_vehicle = number_of_vehicle;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }
}
