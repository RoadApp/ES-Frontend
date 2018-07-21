package edu.ufcg.es.es_front.httpClient.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import edu.ufcg.es.es_front.httpClient.AppConfig;
import edu.ufcg.es.es_front.httpClient.GsonReflectionRequest;
import edu.ufcg.es.es_front.httpClient.callbacks.OnGetServicesByCarCallback;
import edu.ufcg.es.es_front.models.Service;

public class GetServicesByCarRequest {
    private final OnGetServicesByCarCallback callback;

    public GetServicesByCarRequest(OnGetServicesByCarCallback callback) {
        this.callback = callback;
    }

    public Request getRequest(Map<String, String> headers){
        String url = AppConfig.getInstance().car();
        Type type = new TypeToken<JsonArray>(){
        }.getType();
        final GsonReflectionRequest request = new GsonReflectionRequest(url, type, headers, new Response.Listener<JsonArray>() {
            @Override
            public void onResponse(JsonArray response) {
                Gson gson = new Gson();
                JsonArray jsonArray = response.getAsJsonArray();
                ArrayList<Service> serviceList = new ArrayList<>();
                for(int i = 0; i < jsonArray.size(); i++){
                    Service service = gson.fromJson(jsonArray.get(i), Service.class);
                    serviceList.add(service);
                }
                callback.onGetServicesByCarCallbackSucess(serviceList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callback.onGetServicesByCarCallbackError(error.getMessage());
            }
        });

        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 300;
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
