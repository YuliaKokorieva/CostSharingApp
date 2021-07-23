package com.example.costsharing;

import java.util.UUID;

public class Participant {

    private String id;
    private String name;
    private String tripID;

    public Participant(){
        this.id = UUID.randomUUID().toString();
    }
    public String getId() {return id; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTripID() {return tripID; }
    public void setTripId(String id) {this.tripID = id;}




}
