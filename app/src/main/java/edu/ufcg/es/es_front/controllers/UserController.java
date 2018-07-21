package edu.ufcg.es.es_front.controllers;

import android.app.Activity;

import java.util.HashMap;
import java.util.Map;

import edu.ufcg.es.es_front.httpClient.RequestQueueSingleton;
import edu.ufcg.es.es_front.httpClient.callbacks.OnPostLogoutCallback;
import edu.ufcg.es.es_front.httpClient.requests.PostLogoutRequest;
import edu.ufcg.es.es_front.models.User;
import edu.ufcg.es.es_front.utils.ActivityUtils;

public class UserController {

    private static User userLogged;
    private static String userToken;

    public static User getUserLogged() {
        return userLogged;
    }

    public static void setUserLogged(User userLogged) {
        UserController.userLogged = userLogged;
    }

    public static String getUserToken() {
        return userToken;
    }

    public static void setUserToken(String userToken) {
        UserController.userToken = userToken;
    }

    private static void logoutUser(){
        UserController.userLogged = null;
        UserController.userToken = null;
    }

    public static void logout(Activity activity) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("id", UserController.getUserLogged().get_id());

        ActivityUtils.showProgressDialog(activity, "Loging out");

        PostLogoutRequest postLogoutRequest = new PostLogoutRequest(UserController.postLogoutCallback(activity));
        RequestQueueSingleton.getInstance(activity).addToRequestQueue(postLogoutRequest.getRequest(params));
    }

    private static OnPostLogoutCallback postLogoutCallback(final Activity activity){
        return new OnPostLogoutCallback() {
            @Override
            public void onPostLogoutCallbackSucess() {
                ActivityUtils.cancelProgressDialog();
                UserController.logoutUser();
                activity.finish();
            }

            @Override
            public void onPostLogoutCallbackError(String message) {
                ActivityUtils.cancelProgressDialog();
                ActivityUtils.showToast(activity.getApplicationContext(), "Error on logout");
            }
        };
    }
}
