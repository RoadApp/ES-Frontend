package edu.ufcg.es.es_front.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.ufcg.es.es_front.R;
import edu.ufcg.es.es_front.models.Car;

public class VehicleActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private Context context;
    private Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        this.init();

        this.car = (Car) getIntent().getSerializableExtra("car");

        Log.d("@@", car.toString());
    }

    private void init(){
        this.context = this.getApplicationContext();
        this.floatingActionButton = findViewById(R.id.fab_newVehicleService);

        this.floatingActionButton.setOnClickListener(this.floatActionButtonClickListener());
    }

    private View.OnClickListener floatActionButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createServiceIntent = new Intent(context, CreateServiceActivity.class);
                startActivity(createServiceIntent);
            }
        };
    }
}
