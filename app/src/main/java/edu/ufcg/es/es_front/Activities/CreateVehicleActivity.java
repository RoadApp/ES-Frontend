package edu.ufcg.es.es_front.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.ufcg.es.es_front.R;

public class CreateVehicleActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vehicle);
        this.mViewHolder.vehicleBrand = findViewById(R.id.vehicle_brand);
        this.mViewHolder.vehicleModel = findViewById(R.id.vehicle_model);
        this.mViewHolder.vehicleYear = findViewById(R.id.vehicle_year);
        this.mViewHolder.vehicleOdometer = findViewById(R.id.vehicle_odometer);
        this.mViewHolder.vehicleSemanalMedia = findViewById(R.id.vehicle_semanal_media);
        this.mViewHolder.vehiclePlate = findViewById(R.id.vehicle_plate);
        this.mViewHolder.buttonSubmit = findViewById(R.id.button_submit);
        this.mViewHolder.vehicleExpirationMonth = findViewById(R.id.vehicle_expiration_month);
        this.mViewHolder.buttonSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_submit) {
        }
    }

    private static class ViewHolder{
        EditText vehicleBrand, vehicleModel, vehicleYear, vehicleOdometer, vehicleSemanalMedia, vehiclePlate, vehicleExpirationMonth;
        Button buttonSubmit;
    }
}
