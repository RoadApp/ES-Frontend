package edu.ufcg.es.es_front.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.ufcg.es.es_front.R;

public class VehicleActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        this.setListeners();
        this.init();
    }

    private void init(){
        this.floatingActionButton = findViewById(R.id.fab);
        this.context = this.getApplicationContext();
    }

    private void setListeners() {
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
