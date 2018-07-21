package edu.ufcg.es.es_front.models;

import java.io.Serializable;

public class Car implements Serializable {

    private String _id;
    private String brand;
    private String model;
    private String year;
    private String plate;
    private String odometer;


    public Car(String _id, String brand, String model, String year, String plate, String odometer) {
        this._id = _id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.plate = plate;
        this.odometer = odometer;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    @Override
    public String toString() {
        return "Car{" +
                "_id='" + _id + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year='" + year + '\'' +
                ", plate='" + plate + '\'' +
                ", odometer='" + odometer + '\'' +
                '}';
    }
}
