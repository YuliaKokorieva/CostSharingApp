package com.example.costsharing;

import java.util.UUID;

public class Participant {

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Participant(){
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
}
