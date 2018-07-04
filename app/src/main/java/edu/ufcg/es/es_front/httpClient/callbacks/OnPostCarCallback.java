package edu.ufcg.es.es_front.httpClient.callbacks;

import edu.ufcg.es.es_front.models.Car;

public interface OnPostCarCallback {
    void onPostCarCallbackSucess(Car car);
    void onPostCarCallbackError(String message);
}
