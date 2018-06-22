package edu.ufcg.es.es_front.httpClient.callbacks;

import edu.ufcg.es.es_front.models.User;

public interface OnPostLoginCallback {
    void onPostLoginCallbackSucess(User response);
    void onPostUserCallbackERror(String message);
}
