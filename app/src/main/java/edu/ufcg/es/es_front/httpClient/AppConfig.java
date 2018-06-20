package edu.ufcg.es.es_front.httpClient;

public class AppConfig {

    private static AppConfig instance;

    private final String host = "http://192.168.2.11:3000";

    private final String all = "/all";

    private final String user = "/user";

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
}
