package edu.ufcg.es.es_front.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import edu.ufcg.es.es_front.R;

public class NewUserActivity extends AppCompatActivity {

    private AutoCompleteTextView edtFullName, edtEmail, edtPassword, edtConfirmPassword;

    private String fullName, email, password, confirmPassword;

    private Button submitButton;

    private final String  EMPTY_ERROR = "This field can not be empty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        init();
    }

    private void init(){
        this.edtFullName = findViewById(R.id.edt_formNewUser_fullName);
        this.edtEmail = findViewById(R.id.edt_formNewUser_email);
        this.edtPassword = findViewById(R.id.edt_formNewUser_password);
        this.edtConfirmPassword = findViewById(R.id.edt_formNewUser_confirmPassword);
        this.submitButton = findViewById(R.id.button_formNewUser_submit);
    }

    private View.OnClickListener submitOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = edtFullName.getText().toString();
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();
                confirmPassword = edtConfirmPassword.getText().toString();


            }
        };
    }

    private void validateFields(){

        //Reset errors
        this.edtFullName.setError(null);
        this.edtEmail.setError(null);
        this.edtPassword.setError(null);
        this.edtConfirmPassword.setError(null);

        boolean error = false;
        View focusView = null;

        if(TextUtils.isEmpty(password)){
            this.edtPassword.setError(EMPTY_ERROR);
            focusView = this.edtPassword;
            error = true;

        }else if(TextUtils.isEmpty(confirmPassword)){
            this.edtConfirmPassword.setError(EMPTY_ERROR);
            focusView = this.edtConfirmPassword;
            error = true;

        }else if(!password.equals(confirmPassword)){
            this.edtConfirmPassword.setError("As senhas não conferem!");
            focusView = this.edtConfirmPassword;
            error = true;
        }

        if(TextUtils.isEmpty(fullName)){
            this.edtFullName.setError(EMPTY_ERROR);
            focusView = this.edtFullName;
            error = true;
        }

        if(TextUtils.isEmpty(email)){
            this.edtEmail.setError(EMPTY_ERROR);
            focusView = this.edtEmail;
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
        params.put("fullName", fullName);
        params.put("email", email);
        params.put("password", password);

        ActivityUtils.showProgressDialog(this, "Cadastrando Cambista");

        PostCambistaRequest postCambistaRequest = new PostCambistaRequest(postCambistaCallback());
        RequestQueueSingleton.getInstance(this).addToRequestQueue(postCambistaRequest.getRequest(params));
    }
}
