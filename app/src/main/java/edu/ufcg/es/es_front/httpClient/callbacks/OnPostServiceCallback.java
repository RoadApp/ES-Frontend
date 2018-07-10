package edu.ufcg.es.es_front.httpClient.callbacks;

import edu.ufcg.es.es_front.models.Service;

public interface OnPostServiceCallback {
    void onPostServiceCallbackSucess(Service service);
    void onPostServiceCallbackError(String message);
}
