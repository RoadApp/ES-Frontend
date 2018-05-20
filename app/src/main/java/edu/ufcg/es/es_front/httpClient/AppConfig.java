package edu.ufcg.es.es_front.httpClient;

public class AppConfig {

    private static AppConfig instance;

    private final String host = "http://localhost.. <IP DO SERVIDOR>:<PORTA>";

    private final String all = "/all";

    private static AppConfig getInstance(){
        if(instance == null){
            instance = new AppConfig();
        }

        return instance;
    }
}
