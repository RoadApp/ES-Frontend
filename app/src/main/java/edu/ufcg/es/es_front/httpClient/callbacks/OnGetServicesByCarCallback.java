package edu.ufcg.es.es_front.httpClient.callbacks;

import java.util.ArrayList;

import edu.ufcg.es.es_front.models.Service;

public interface OnGetServicesByCarCallback {
    void onGetServicesByCarCallbackSucess(ArrayList<Service> response);
    void onGetServicesByCarCallbackError(String error);
}