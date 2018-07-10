package edu.ufcg.es.es_front.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    private String fullName, email, password, confirmPassword, cnhExpiration, birthDate;

    private Button submitButton;

    private Context context;

    private SimpleDateFormat dateFormater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        init();

        this.submitButton.setOnClickListener(this.submitOnClickListener());
        this.edtBirthDate.setOnLongClickListener(this.dateClick(this.edtBirthDate));
        this.edtCNHExpiration.setOnLongClickListener(this.dateClick(this.edtCNHExpiration));

    }

    private void init(){
        this.edtFullName = findViewById(R.id.edt_formNewUser_fullName);
        this.edtEmail = findViewById(R.id.edt_formNewUser_email);
        this.edtPassword = findViewById(R.id.edt_formNewUser_password);
        this.edtConfirmPassword = findViewById(R.id.edt_formNewUser_confirmPassword);
        this.edtCNHExpiration = findViewById(R.id.edt_formNewUser_cnhExpiration);
        this.edtBirthDate = findViewById(R.id.edt_formNewUser_birthDate);
        this.submitButton = findViewById(R.id.button_formNewUser_submit);

        this.context = getApplicationContext();
        this.dateFormater = new SimpleDateFormat("yyyy-MM-dd");
    }

    private View.OnClickListener submitOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fullName = edtFullName.getText().toString();
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();
                confirmPassword = edtConfirmPassword.getText().toString();
                cnhExpiration = edtCNHExpiration.getText().toString();
                birthDate = edtBirthDate.getText().toString();

                validateFields();
            }
        };
    }

    private View.OnLongClickListener dateClick(final EditText editText){
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = getDatePickerDialog(calendar, editText);
                datePickerDialog.show();
                return true;
            }
        };
    }

    private DatePickerDialog getDatePickerDialog(final Calendar calendarPicked, final EditText editText) {
        return new DatePickerDialog(RegisterUserActivity.this, R.style.AppTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarPicked.set(Calendar.YEAR, year);
                calendarPicked.set(Calendar.MONTH, month);
                calendarPicked.set(Calendar.DATE, dayOfMonth);
                Date date = calendarPicked.getTime();
                editText.setText(dateFormater.format(date));
            }
        }, calendarPicked.get(Calendar.YEAR), calendarPicked.get(Calendar.MONTH), calendarPicked.get(Calendar.DATE));
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
        params.put("birthDate", birthDate);
        params.put("cnhExpiration", cnhExpiration);

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
