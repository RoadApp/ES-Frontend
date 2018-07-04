package edu.ufcg.es.es_front.Activities;

import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import edu.ufcg.es.es_front.R;
import edu.ufcg.es.es_front.controllers.UserController;
import edu.ufcg.es.es_front.httpClient.RequestQueueSingleton;
import edu.ufcg.es.es_front.httpClient.callbacks.OnPostCarCallback;
import edu.ufcg.es.es_front.httpClient.requests.PostCarRequest;
import edu.ufcg.es.es_front.models.Car;
import edu.ufcg.es.es_front.utils.ActivityUtils;

public class CreateVehicleActivity extends AppCompatActivity {

    private EditText vehicleBrand, vehicleModel, vehicleYear, vehicleOdometer, vehicleSemanalMedia, vehiclePlate, vehicleExpirationMonth;
    private String brand, model, year, odometer, media, plate, expiration;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vehicle);
        init();

    }

    private void init() {
        this.vehicleBrand = findViewById(R.id.vehicle_brand);
        this.vehicleModel = findViewById(R.id.vehicle_model);
        this.vehicleYear = findViewById(R.id.vehicle_year);
        this.vehicleOdometer = findViewById(R.id.vehicle_odometer);
        this.vehicleSemanalMedia = findViewById(R.id.vehicle_semanal_media);
        this.vehiclePlate = findViewById(R.id.vehicle_plate);
        this.buttonSubmit = findViewById(R.id.button_submit);
        this.vehicleExpirationMonth = findViewById(R.id.vehicle_expiration_month);
        this.buttonSubmit.setOnClickListener(this.submitOnClickListener());
    }





    private View.OnClickListener submitOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brand = vehicleBrand.getText().toString();
                model = vehicleModel.getText().toString();
                year = vehicleYear.getText().toString();
                odometer = vehicleOdometer.getText().toString();
                media = vehicleSemanalMedia.getText().toString();
                plate = vehiclePlate.getText().toString();
                expiration = vehicleExpirationMonth.getText().toString();
            }
        };
    }

    private void validadeFields(){

        //Reset errors
        this.vehicleBrand.setError(null);
        this.vehicleModel.setError(null);
        this.vehicleYear.setError(null);
        this.vehicleOdometer.setError(null);
        this.vehicleSemanalMedia.setError(null);
        this.vehiclePlate.setError(null);
        this.vehicleExpirationMonth.setError(null);

        boolean error = false;
        View focusView = null;

        if(TextUtils.isEmpty(brand)){
            this.vehicleBrand.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.vehicleBrand;
            error = true;
        }
        if(TextUtils.isEmpty(model)){
            this.vehicleModel.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.vehicleModel;
            error = true;

        }
        if(TextUtils.isEmpty(year)){
            this.vehicleYear.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.vehicleYear;
            error = true;
        }
        if(TextUtils.isEmpty(odometer)){
            this.vehicleOdometer.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.vehicleOdometer;
            error = true;
        }

        if(TextUtils.isEmpty(media)){
            this.vehicleSemanalMedia.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.vehicleSemanalMedia;
            error = true;
        }
        if(TextUtils.isEmpty(plate)){
            this.vehiclePlate.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.vehiclePlate;
            error = true;
        }
        if(TextUtils.isEmpty(expiration)){
            this.vehicleExpirationMonth.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.vehicleExpirationMonth;
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

        params.put("brand", brand);
        params.put("model", model);
        params.put("year", year);
        params.put("plate", plate);
        params.put("odometer", odometer);

        headers.put("authorization", "bearer " + UserController.getUserLogged().getToken());

        ActivityUtils.showProgressDialog(this, "Registering Vehicle");

        PostCarRequest postCarRequest = new PostCarRequest(postCarCallback());
        RequestQueueSingleton.getInstance(this).addToRequestQueue(postCarRequest.getRequest(params, headers));


    }

    //Todo: fix
    private OnPostCarCallback postCarCallback(){
        return new OnPostCarCallback() {
            @Override
            public void onPostCarCallbackSucess(Car car) {
                ActivityUtils.cancelProgressDialog();
            }

            @Override
            public void onPostCarCallbackError(String message) {
                ActivityUtils.cancelProgressDialog();
            }
        };
    }
}
