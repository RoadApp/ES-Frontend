package edu.ufcg.es.es_front.httpClient.requests;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.ufcg.es.es_front.httpClient.AppConfig;
import edu.ufcg.es.es_front.httpClient.callbacks.OnPostCarCallback;
import edu.ufcg.es.es_front.models.Car;

public class PostCarRequest {

    private final OnPostCarCallback callback;

    public PostCarRequest(OnPostCarCallback callback) {
        this.callback = callback;
    }



    public Request getRequest(Map<String, String> params, final Map<String, String> CustomHeaders) {
        String url = AppConfig.getInstance().car();

        Request request = new JsonObjectRequest(url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Car car = gson.fromJson(gson.toJson(response), Car.class);
                callback.onPostCarCallbackSucess(car);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onPostCarCallbackError(error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                headers.putAll(CustomHeaders);

                return headers;
            }
        };

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
