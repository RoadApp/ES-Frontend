package edu.ufcg.es.es_front.httpClient.callbacks;

public interface OnPostLogoutCallback {
    void onPostLogoutCallbackSucess();
    void onPostLogoutCallbackError(String message);
}
