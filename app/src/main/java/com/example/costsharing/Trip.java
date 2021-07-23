package com.example.costsharing;

import java.util.UUID;

public class Trip {

    private String name;
    private String id;

    public Trip(){
        this.id = UUID.randomUUID().toString();
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trip(String name) {
        this.name = name;
    }
}
