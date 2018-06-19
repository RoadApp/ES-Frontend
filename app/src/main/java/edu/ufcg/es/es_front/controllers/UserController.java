package edu.ufcg.es.es_front.controllers;

import edu.ufcg.es.es_front.models.User;

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

    public static void logout(){
        UserController.userLogged = null;
        UserController.userToken = null;
    }
}
