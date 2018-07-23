package edu.ufcg.es.es_front.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import edu.ufcg.es.es_front.R;
import edu.ufcg.es.es_front.controllers.UserController;
import edu.ufcg.es.es_front.httpClient.RequestQueueSingleton;
import edu.ufcg.es.es_front.httpClient.callbacks.OnPostLoginCallback;
import edu.ufcg.es.es_front.httpClient.requests.PostLoginRequest;
import edu.ufcg.es.es_front.models.User;
import edu.ufcg.es.es_front.utils.ActivityUtils;


public class LoginActivity extends AppCompatActivity{

    private AutoCompleteTextView edtEmail;

    private TextInputEditText edtPassword;

    private Button loginButton, registerButton;

    private String email, password;


    @Override
    protected void onStart() {
        super.onStart();

        updateUI();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.init();

    }

    private void init() {

        this.edtEmail = findViewById(R.id.edt_formLogin_Email);
        this.edtPassword = findViewById(R.id.edt_formLogin_password);
        this.loginButton = findViewById(R.id.login_btnLogin);
        this.registerButton = findViewById(R.id.login_btnRegister);


        this.registerButton.setOnClickListener(registerOnclick());
        this.loginButton.setOnClickListener(loginOnclick());
    }


    private void updateUI(){
        if(UserController.getUserLogged() != null) {
            Intent main = new Intent(getApplicationContext(), CarListActivity.class);

            startActivity(main);
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

    private OnClickListener loginOnclick(){

        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();

                validadeFields();
            }
        };

    }

    private void validadeFields(){

        //Reset errors
        this.edtEmail.setError(null);
        this.edtPassword.setError(null);

        boolean error = false;
        View focusView = null;

        if(TextUtils.isEmpty(email)){
            this.edtEmail.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.edtEmail;
            error = true;

        }

        if(TextUtils.isEmpty(password)){
            this.edtPassword.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.edtPassword;
            error = true;

        }
        if(error){
            focusView.requestFocus();
        }else{
            sendRegistration();
        }

    }

    private void sendRegistration(){
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        ActivityUtils.showProgressDialog(this, "Registering User");

        PostLoginRequest postLoginRequest = new PostLoginRequest(postLoginCallback());
        RequestQueueSingleton.getInstance(this).addToRequestQueue(postLoginRequest.getRequest(params));
    }

    private OnPostLoginCallback postLoginCallback(){
        return new OnPostLoginCallback() {
            @Override
            public void onPostLoginCallbackSucess(User response) {
                ActivityUtils.cancelProgressDialog();
                UserController.setUserLogged(response);
                updateUI();
            }

            @Override
            public void onPostUserCallbackERror(String message) {
                ActivityUtils.cancelProgressDialog();
                ActivityUtils.showToast(getApplicationContext(), "Error on login. Try again");
            }
        };
    }


}
