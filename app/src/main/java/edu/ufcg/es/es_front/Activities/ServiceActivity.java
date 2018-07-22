package edu.ufcg.es.es_front.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.ufcg.es.es_front.R;
import edu.ufcg.es.es_front.models.Service;

public class ServiceActivity extends AppCompatActivity {

    private Service service;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        this.init();

        this.service = (Service) getIntent().getSerializableExtra("service");

        Log.d("@@", service.toString());
    }

    private void init(){
        this.context = this.getApplicationContext();
    }

    }


