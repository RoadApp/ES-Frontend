package edu.ufcg.es.es_front.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import edu.ufcg.es.es_front.R;
import edu.ufcg.es.es_front.models.User;


public class LoginActivity extends AppCompatActivity{

    private Button registerButton;


    @Override
    protected void onStart() {
        super.onStart();

//        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.init();

    }

    private void init() {

        this.registerButton = findViewById(R.id.login_btnRegister);


        this.registerButton.setOnClickListener(registerOnclick());
    }


    private void updateUI(User user){

        //ninguém logado, carregar tela de login
        if(user == null) {

        } else { //alguém já logado, redirecionar para tela de home

        }



    }

    private OnClickListener registerOnclick(){
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerUser = new Intent(getApplicationContext(), RegisterUserActivity.class);
                startActivity(registerUser);
            }
        };
    }

}
