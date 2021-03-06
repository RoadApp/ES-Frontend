package edu.ufcg.es.es_front.httpClient.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Map;

import edu.ufcg.es.es_front.httpClient.AppConfig;
import edu.ufcg.es.es_front.httpClient.callbacks.OnPostLogoutCallback;

public class PostLogoutRequest {

    private final OnPostLogoutCallback callback;

    public PostLogoutRequest(OnPostLogoutCallback callback) {
        this.callback = callback;
    }

    public Request getRequest(Map<String, String> params) {
        String url = AppConfig.getInstance().logout();

        Request request = new JsonObjectRequest(url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onPostLogoutCallbackSucess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onPostLogoutCallbackError(error.getMessage());
            }
        });

        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 0;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                if(error.networkResponse != null && (error.networkResponse.statusCode == 401)){
                    throw error;
                }
            }
        });

        return request;
    }
}
