package com.example.costsharing;


public class Participant {

    private int id;
    private String name;
    private int tripID;

    public Participant() {}
    public Participant(String name, int tripID) {
        this.name = name;
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

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }
}
