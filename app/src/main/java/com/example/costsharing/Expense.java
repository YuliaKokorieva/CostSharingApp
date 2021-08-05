package com.example.costsharing;

import java.util.UUID;

public class Expense {

    private int id;
    private String name;
    private Double value;
    private long partID;
    private long tripID;

    public Expense(){}
    public Expense(String name, Double value, int partID, long tripID) {
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

    public long getPartID() {
        return partID;
    }

    public void setPartID(long partID) {
        this.partID = partID;
    }

    public long getTripID() {
        return tripID;
    }

    public void setTripID(long tripID) {
        this.tripID = tripID;
    }
}
