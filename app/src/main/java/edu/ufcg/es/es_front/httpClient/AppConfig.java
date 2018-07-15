package edu.ufcg.es.es_front.httpClient;

public class AppConfig {

    private static AppConfig instance;

    private final String host = "http://192.168.1.9:5000";

    private final String all = "/all";

    private final String user = "/user";

    private final String login = "/login";

    private final String logout = "/logout";

    private final String car = "/car";

    private final String service = "/service";

    public static AppConfig getInstance(){
        if(instance == null){
            instance = new AppConfig();
        }

        return instance;
    }

    public String registerUser() {
        return host +
                user;
    }

    public String login(){
        return host +
                login;
    }

    public String logout(){
        return host +
                logout;
    }

    public String car(){
        return host +
                car;
    }

    public String service(){
        return host +
                service;
    }
}
