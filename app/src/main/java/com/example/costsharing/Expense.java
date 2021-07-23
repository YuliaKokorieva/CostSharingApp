package com.example.costsharing;

import java.util.UUID;

public class Expense {

    private String id;
    private String name;
    private Double value;
    private String partID;
    private String tripID;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Expense(){
        this.id = UUID.randomUUID().toString();
    }
    public String getId() {
        return id;
    }

    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }

    public String getPartID() {return partID; }
    public void setPartID(String partID) {this.partID = partID; }

    public String getTripID() {return tripID; }
    public void setTripID(String tripID) {this.tripID = tripID; }
}
