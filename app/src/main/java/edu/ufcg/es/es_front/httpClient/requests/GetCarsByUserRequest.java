package edu.ufcg.es.es_front.httpClient.requests;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

import edu.ufcg.es.es_front.httpClient.AppConfig;
import edu.ufcg.es.es_front.httpClient.GsonReflectionRequest;
import edu.ufcg.es.es_front.httpClient.callbacks.OnGetCarsByUserCallback;
import edu.ufcg.es.es_front.models.Car;

public class GetCarsByUserRequest {
    private final OnGetCarsByUserCallback callback;

    public GetCarsByUserRequest(OnGetCarsByUserCallback callback) {
        this.callback = callback;
    }

    private Car parseCar(JSONObject response) throws JSONException {
        String _id = response.getString("_id");
        String brand = response.getString("brand");
        String model = response.getString("model");
        String year = response.getString("year");
        String plate = response.getString("plate");
        String odometer = response.getString("odometer");

        return new Car(_id, brand, model, year, plate, odometer);
    }

    public Request getRequest(Map<String, String> headers){
        String url = AppConfig.getInstance().car();
        Type type = new TypeToken<JsonArray>(){
        }.getType();
        final GsonReflectionRequest request = new GsonReflectionRequest(url, type, headers, new Response.Listener<JsonArray>() {
            @Override
            public void onResponse(JsonArray response) {
                Gson gson = new Gson();
                    ArrayList<Car> carList = new ArrayList<>();
                    for (int i = 0; i < response.size(); i++) {
                        carList.add(gson.fromJson(response.get(i), Car.class));
                    }
                    callback.onGetCarsByUserCallbackSucess(carList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callback.onGetCarsByUserCallbackError(error.getMessage());
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
