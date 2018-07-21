package edu.ufcg.es.es_front.httpClient.requests;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import edu.ufcg.es.es_front.httpClient.AppConfig;
import edu.ufcg.es.es_front.httpClient.callbacks.OnPostLoginCallback;
import edu.ufcg.es.es_front.models.User;

public class PostLoginRequest {

    private final OnPostLoginCallback callback;

    public PostLoginRequest(OnPostLoginCallback callback) {
        this.callback = callback;
    }

    private User parseUser(JSONObject response) throws JSONException {

        String _id = response.getString("_id");
        String fullName = response.getString("fullName");
        String email = response.getString("email");
        String token = response.getString("token");

        return new User(_id, fullName, email, token);
    }

    public Request getRequest(Map<String, String> params){
        String url = AppConfig.getInstance().login();

        final Request request = new JsonObjectRequest(url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("@@@@USER", response.toString());
                try {
                    User user = parseUser(response);
                    callback.onPostLoginCallbackSucess(user);
                } catch (JSONException e) {
                    callback.onPostUserCallbackERror(e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
                Log.i("###########@", String.valueOf(error));
                Log.i("############", String.valueOf(error.networkResponse));
//                Log.i("############@@", String.valueOf(error.networkResponse.statusCode));
                callback.onPostUserCallbackERror(error.getMessage());
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
