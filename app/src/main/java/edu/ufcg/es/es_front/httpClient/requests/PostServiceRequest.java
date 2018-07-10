package edu.ufcg.es.es_front.httpClient.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;

import edu.ufcg.es.es_front.httpClient.AppConfig;
import edu.ufcg.es.es_front.httpClient.callbacks.OnPostServiceCallback;
import edu.ufcg.es.es_front.models.Service;

public class PostServiceRequest {

    private final OnPostServiceCallback callback;

    public PostServiceRequest(OnPostServiceCallback callback) {
        this.callback = callback;
    }

    public Request getRequest(Map<String, String> params, final Map<String, String> customHeaders) {
        String url = AppConfig.getInstance().service();

        final Request request = new JsonObjectRequest(url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Service service = gson.fromJson(gson.toJson(response), Service.class);
                callback.onPostServiceCallbackSucess(service);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onPostServiceCallbackError(error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = super.getHeaders();
                headers.putAll(customHeaders);
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

            }
        });
        return request;
    }
}
