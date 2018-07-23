package edu.ufcg.es.es_front.Activities;

import android.app.DatePickerDialog;
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
import edu.ufcg.es.es_front.controllers.UserController;
import edu.ufcg.es.es_front.httpClient.RequestQueueSingleton;
import edu.ufcg.es.es_front.httpClient.callbacks.OnPostServiceCallback;
import edu.ufcg.es.es_front.httpClient.requests.PostCarRequest;
import edu.ufcg.es.es_front.httpClient.requests.PostServiceRequest;
import edu.ufcg.es.es_front.models.Service;
import edu.ufcg.es.es_front.utils.ActivityUtils;

public class CreateServiceActivity extends AppCompatActivity {

    private AutoCompleteTextView edtName, edtPrice, edtDate, edtActualOdometer, edtPlace;
    private String name, price, actualOdometer, place, date, carId;
    private Button submitButton;

    private SimpleDateFormat dateFormater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);
        init();

        this.submitButton.setOnClickListener(this.submitOnClickListener());
        this.edtDate.setOnLongClickListener(this.dateClick(this.edtDate));
    }

    private void init(){
        this.dateFormater = new SimpleDateFormat("yyyy-MM-dd");

        this.carId = getIntent().getStringExtra("carId");

        this.edtName = findViewById(R.id.edt_formNewService_name);
        this.edtPrice = findViewById(R.id.edt_formNewService_price);
        this.edtDate = findViewById(R.id.edt_formNewService_date);
        this.edtActualOdometer = findViewById(R.id.edt_formNewService_actualOdometer);
        this.edtPlace = findViewById(R.id.edt_formNewService_place);
        this.edtDate = findViewById(R.id.edt_formNewService_date);
        this.submitButton = findViewById(R.id.button_formNewService_submit);
    }

    private View.OnClickListener submitOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edtName.getText().toString();
                price = edtPrice.getText().toString();
                actualOdometer = edtActualOdometer.getText().toString();
                place = edtPlace.getText().toString();
                date = edtDate.getText().toString();

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
        return new DatePickerDialog(CreateServiceActivity.this, R.style.AppTheme, new DatePickerDialog.OnDateSetListener() {
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
        this.edtName.setError(null);
        this.edtPrice.setError(null);
        this.edtActualOdometer.setError(null);

        boolean error = false;
        View focusView = null;

        if(TextUtils.isEmpty(name)){
            this.edtName.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.edtName;
            error = true;

        }else if(TextUtils.isEmpty(price)){
            this.edtPrice.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.edtPrice;
            error = true;
        }

        if(TextUtils.isEmpty(actualOdometer)){
            this.edtActualOdometer.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.edtActualOdometer;
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
        Map<String, String> headers = new HashMap<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        params.put("madeAt", date);
        params.put("car", carId);
        params.put("mileage", actualOdometer);
        params.put("expense", price);
        params.put("description", name);
        if (!(place.equals(""))){
            params.put("place", place);
        }

        headers.put("authorization", "bearer " + UserController.getUserLogged().getToken());

        ActivityUtils.showProgressDialog(this, "Registering Service");

        PostServiceRequest postServiceRequest = new PostServiceRequest(postServiceCallback());
        RequestQueueSingleton.getInstance(this).addToRequestQueue(postServiceRequest.getRequest(params, headers));

    }

    private OnPostServiceCallback postServiceCallback() {
        return new OnPostServiceCallback() {
            @Override
            public void onPostServiceCallbackSucess(Service service) {
                ActivityUtils.cancelProgressDialog();
                //Todo: something

            }

            @Override
            public void onPostServiceCallbackError(String message) {
                ActivityUtils.cancelProgressDialog();
                //Todo: something
            }
        };
    }
}
