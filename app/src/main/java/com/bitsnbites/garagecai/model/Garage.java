package com.bitsnbites.garagecai.model;

public class Garage {
    String id, address;
    int spacePerCar, bike, bicycle;
    long latitude, longitude;
    boolean availability;

    public static int BIKE_PER_CAR=2;
    public static int BICYCLE_PER_CAR=4;

    public Garage(String id, String address, int spacePerCar, int bike, int bicycle, long latitude, long longitude, boolean availability) {
        this.id = id;
        this.address = address;
        this.spacePerCar = spacePerCar;
        this.bike = bike;
        this.bicycle = bicycle;
        this.latitude = latitude;
        this.longitude = longitude;
        this.availability = availability;
    }

    public Garage() {
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

    public float getSpacePerCar() {
        return spacePerCar;
    }

    public void setSpacePerCar(int spacePerCar) {
        this.spacePerCar = spacePerCar;
    }

    public float getBike() {
        return ( (float)spacePerCar/4)*BIKE_PER_CAR;
    }

    public void setBike(int bike) {
        this.bike = bike;
    }

    public float getBicycle() {
        return ((float)spacePerCar/4)*BICYCLE_PER_CAR;
    }

    public void setBicycle(int bicycle) {
        this.bicycle = bicycle;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
