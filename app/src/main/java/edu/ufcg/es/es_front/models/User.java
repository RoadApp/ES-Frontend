package edu.ufcg.es.es_front.models;

import java.util.ArrayList;
import java.util.Date;

public class User {

    private Date created_at;
    private String _id;
    private String fullName;
    private ArrayList<Car> cars;

    public User(Date created_at, String fullName, String _id, ArrayList<Car> cars) {
        this.created_at = created_at;
        this.fullName = fullName;
        this.cars = cars;
        this._id = _id;
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

    public String get_id() {
        return _id;
    }


    public ArrayList<Car> getCars() {
        return cars;
    }

    public void setCars(ArrayList<Car> cars) {
        this.cars = cars;
    }
}
