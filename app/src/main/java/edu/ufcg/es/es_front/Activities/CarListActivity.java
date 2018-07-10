package edu.ufcg.es.es_front.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import edu.ufcg.es.es_front.R;

public class CarListActivity extends AppCompatActivity {

    private ListView carsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        init();
    }

    private void init() {
        this.carsListView = findViewById(R.id.listViewCars);
    }
}
