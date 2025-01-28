package com.app.timetable.Entity;

public class SearchEntity {

    private String type;
    private String email;

    public SearchEntity(){

    }

    public SearchEntity(String type, String email) {
        this.type = type;
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
