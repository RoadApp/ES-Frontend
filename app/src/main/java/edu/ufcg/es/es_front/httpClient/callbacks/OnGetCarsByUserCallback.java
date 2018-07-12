package edu.ufcg.es.es_front.httpClient.callbacks;

import java.util.ArrayList;

import edu.ufcg.es.es_front.models.Car;

public interface OnGetCarsByUserCallback {
    void onGetCarsByUserCallbackSucess(ArrayList<Car> response);
    void onGetCarsByUserCallbackError(String error);
}
