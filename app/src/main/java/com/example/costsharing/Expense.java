package com.example.costsharing;

import java.util.UUID;

public class Expense {

    private int id;
    private String name;
    private Double value;
    private int partID;
    private int tripID;

    public Expense(){}
    public Expense(String name, Double value, int partID, int tripID) {
        this.name = name;
        this.value = value;
        this.partID = partID;
        this.tripID = tripID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int getPartID() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID = partID;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }
}
