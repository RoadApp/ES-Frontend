package edu.ufcg.es.es_front.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.ufcg.es.es_front.R;
import edu.ufcg.es.es_front.httpClient.RequestQueueSingleton;
import edu.ufcg.es.es_front.httpClient.callbacks.OnPostUserCallback;
import edu.ufcg.es.es_front.httpClient.requests.PostUserRequest;
import edu.ufcg.es.es_front.utils.ActivityUtils;

public class RegisterUserActivity extends AppCompatActivity {

    private AutoCompleteTextView edtFullName, edtEmail, edtPassword, edtConfirmPassword, edtCNHExpiration, edtBirthDate;

    private String fullName, email, password, confirmPassword;

    private Date cnhExpiration, birthDate;;

    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        init();

        this.submitButton.setOnClickListener(this.submitOnClickListener());
    }

    private void init(){
        this.edtFullName = findViewById(R.id.edt_formNewUser_fullName);
        this.edtEmail = findViewById(R.id.edt_formNewUser_email);
        this.edtPassword = findViewById(R.id.edt_formNewUser_password);
        this.edtConfirmPassword = findViewById(R.id.edt_formNewUser_confirmPassword);
        this.edtCNHExpiration = findViewById(R.id.edt_formNewUser_cnhExpiration);
        this.edtBirthDate = findViewById(R.id.edt_formNewUser_birthDate);
        this.submitButton = findViewById(R.id.button_formNewUser_submit);
    }

    private View.OnClickListener submitOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pattern = "MMddyyyy";
                SimpleDateFormat simpleDate = new SimpleDateFormat(pattern);
                fullName = edtFullName.getText().toString();
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();
                confirmPassword = edtConfirmPassword.getText().toString();
                try {
                    cnhExpiration = simpleDate.parse(edtCNHExpiration.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    birthDate = simpleDate.parse(edtBirthDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                validateFields();


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
            this.edtPassword.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.edtPassword;
            error = true;

        }else if(TextUtils.isEmpty(confirmPassword)){
            this.edtConfirmPassword.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.edtConfirmPassword;
            error = true;

        }else if(!password.equals(confirmPassword)){
            this.edtConfirmPassword.setError("Passwords are different!");
            focusView = this.edtConfirmPassword;
            error = true;
        }

        if(TextUtils.isEmpty(fullName)){
            this.edtFullName.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.edtFullName;
            error = true;
        }

        if(TextUtils.isEmpty(email)){
            this.edtEmail.setError(ActivityUtils.EMPTY_ERROR);
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

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Map<String, String> params = new HashMap<String, String>();
        params.put("fullName", fullName);
        params.put("email", email);
        params.put("password", password);
        params.put("birthDate", format.format(birthDate));
        params.put("cnhExpiration", format.format(cnhExpiration));

        ActivityUtils.showProgressDialog(this, "Registering User");

        PostUserRequest postUserRequest = new PostUserRequest(postUserCallback());
        RequestQueueSingleton.getInstance(this).addToRequestQueue(postUserRequest.getRequest(params));
    }

    private OnPostUserCallback postUserCallback(){
        return new OnPostUserCallback() {
            @Override
            public void onPostUserCallbackSucess() {
                ActivityUtils.cancelProgressDialog();
                ActivityUtils.showToast(getApplicationContext(), "User Registered;");
                finish();
            }

            @Override
            public void onPostUserCallbackError(String message) {
                ActivityUtils.cancelProgressDialog();
                ActivityUtils.showToast(getApplicationContext(), "Error on user register. Try again");
            }
        };
    }
}
