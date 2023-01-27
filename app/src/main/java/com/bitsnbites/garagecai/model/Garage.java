package com.bitsnbites.garagecai.model;

import java.io.Serializable;

public class Garage  implements Serializable  {
    String id, name, address;
    int numberOfSlots, bike, bicycle;
    double latitude, longitude;
    boolean availability;
    boolean verified;
    double rating;

    String ownerId;

    int hourly_rate = 0;
    String seq_number;

    public static int BIKE_PER_CAR=2;
    public static int BICYCLE_PER_CAR=4;


    public Garage(String id, String name, String address, int numberOfSlots, int bike, int bicycle, double latitude, double longitude, boolean availability, boolean verified, String ownerId, int hourly_rate, String seq_number) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.numberOfSlots = numberOfSlots;
        this.bike = bike;
        this.bicycle = bicycle;
        this.latitude = latitude;
        this.longitude = longitude;
        this.availability = availability;
        this.verified = verified;
        this.ownerId = ownerId;
        this.hourly_rate = hourly_rate;
        this.seq_number = seq_number;
    }

    public Garage() {
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(int hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeq_number() {
        return seq_number;
    }

    public void setSeq_number(String seq_number) {
        this.seq_number = seq_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public float getNumberOfSlots() {
        return numberOfSlots;
    }

    public void setNumberOfSlots(int numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
    }

    public float getBike() {
        return ( (float) numberOfSlots /4)*BIKE_PER_CAR;
    }

    public void setBike(int bike) {
        this.bike = bike;
    }

    public float getBicycle() {
        return ((float) numberOfSlots /4)*BICYCLE_PER_CAR;
    }

    public void setBicycle(int bicycle) {
        this.bicycle = bicycle;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isAvailability() {
        return availability;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }


}
