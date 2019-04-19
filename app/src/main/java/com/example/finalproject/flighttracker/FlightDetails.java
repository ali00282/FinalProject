package com.example.finalproject.flighttracker;

import java.io.Serializable;

public class FlightDetails implements Serializable {
    private String depiata;
    private String arriata;
    private String airlineIata;
    private String lat;
    private String lon;
    private String direction;
    private String altitude;
    private String speedhorizontal;
    private String speedvertical;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    public FlightDetails(String depiata, String arriata, String airlineIata, String lat, String lon, String direction, String altitude, String speedhorizontal, String speedvertical, String status) {
        this.depiata = depiata;
        this.arriata = arriata;
        this.airlineIata = airlineIata;
        this.lat = lat;
        this.lon = lon;
        this.direction = direction;
        this.altitude = altitude;
        this.speedhorizontal = speedhorizontal;
        this.speedvertical = speedvertical;
        this.status = status;
    }
    public FlightDetails(int id, String depiata, String arriata, String airlineIata, String lat, String lon, String direction, String altitude, String speedhorizontal, String speedvertical, String status) {
        this.id = id;
        this.depiata = depiata;
        this.arriata = arriata;
        this.airlineIata = airlineIata;
        this.lat = lat;
        this.lon = lon;
        this.direction = direction;
        this.altitude = altitude;
        this.speedhorizontal = speedhorizontal;
        this.speedvertical = speedvertical;
        this.status = status;
    }
    public String getDepiata() {
        return depiata;
    }

    public void setDepiata(String depiata) {
        this.depiata = depiata;
    }

    public String getArriata() {
        return arriata;
    }

    public void setArriata(String arriata) {
        this.arriata = arriata;
    }

    public String getAirlineIata() {
        return airlineIata;
    }

    public void setAirlineIata(String airlineIata) {
        this.airlineIata = airlineIata;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getSpeedhorizontal() {
        return speedhorizontal;
    }

    public void setSpeedhorizontal(String speedhorizontal) {
        this.speedhorizontal = speedhorizontal;
    }

    public String getSpeedvertical() {
        return speedvertical;
    }

    public void setSpeedvertical(String speedvertical) {
        this.speedvertical = speedvertical;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
