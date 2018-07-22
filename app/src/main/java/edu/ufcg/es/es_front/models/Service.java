package edu.ufcg.es.es_front.models;

import java.util.Date;

public class Service {

    private String name, price, actualOdometer, description;
    private Date date;

    public Service(String name, String price, Date date, String actualOdometer, String description) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.actualOdometer = actualOdometer;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getActualOdometer() {
        return actualOdometer;
    }

    public void setActualOdometer(String actualOdometer) {
        this.actualOdometer = actualOdometer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
