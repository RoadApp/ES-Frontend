package edu.ufcg.es.es_front.models;

import java.io.Serializable;
import java.util.Date;

public class Service implements Serializable {

    private String _id, madeAt, car, mileage, place, description;
    private float expense;

    public Service(String _id, String madeAt, String car, String mileage, float expense, String place, String description) {
        this._id = _id;
        this.madeAt = madeAt;
        this.car = car;
        this.mileage = mileage;
        this.expense = expense;
        this.place = place;
        this.description = description;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMadeAt() {
        return madeAt;
    }

    public void setMadeAt(String madeAt) {
        this.madeAt = madeAt;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public float getExpense() {
        return expense;
    }

    public void setExpense(float expense) {
        this.expense = expense;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Service{" +
                "_id='" + _id + '\'' +
                ", madeAt='" + madeAt + '\'' +
                ", car='" + car + '\'' +
                ", mileage='" + mileage + '\'' +
                ", expense='" + expense + '\'' +
                ", place='" + place + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}