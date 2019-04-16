package com.example.finalproject.flighttracker;

import java.io.Serializable;

public class TimeTable implements Serializable {
    private String type;
    private String status;
    private String depiatacode;
    private String arrIataCode;
    private String airlineName;
    private String airlineIataCode;
    private String flightNum;
    private String flightIataNum;

    public TimeTable(String type, String status, String depiatacode, String arrIataCode, String airlineName, String airlineIataCode, String flightNum, String flightIataNum) {
        this.type = type;
        this.status = status;
        this.depiatacode = depiatacode;
        this.arrIataCode = arrIataCode;
        this.airlineName = airlineName;
        this.airlineIataCode = airlineIataCode;
        this.flightNum = flightNum;
        this.flightIataNum = flightIataNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepiatacode() {
        return depiatacode;
    }

    public void setDepiatacode(String depiatacode) {
        this.depiatacode = depiatacode;
    }

    public String getArrIataCode() {
        return arrIataCode;
    }

    public void setArrIataCode(String arrIataCode) {
        this.arrIataCode = arrIataCode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getAirlineIataCode() {
        return airlineIataCode;
    }

    public void setAirlineIataCode(String airlineIataCode) {
        this.airlineIataCode = airlineIataCode;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public String getFlightIataNum() {
        return flightIataNum;
    }

    public void setFlightIataNum(String flightIataNum) {
        this.flightIataNum = flightIataNum;
    }
}
