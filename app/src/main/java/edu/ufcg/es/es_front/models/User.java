package edu.ufcg.es.es_front.models;

import java.util.ArrayList;
import java.util.Date;

public class User {

    private Date created_at;
    private String fullName;
    private String firebaseUid;
    private ArrayList<Car> cars;

    public User(Date created_at, String fullName, String firebaseUid, ArrayList<Car> cars) {
        this.created_at = created_at;
        this.fullName = fullName;
        this.firebaseUid = firebaseUid;
        this.cars = cars;
    }

    public Date getCreated_at() {
        return created_at;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }


    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }
}
