package edu.ufcg.es.es_front.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ufcg.es.es_front.adapters.CarsListAdapter;
import edu.ufcg.es.es_front.controllers.UserController;
import edu.ufcg.es.es_front.httpClient.RequestQueueSingleton;
import edu.ufcg.es.es_front.httpClient.callbacks.OnGetCarsByUserCallback;
import edu.ufcg.es.es_front.httpClient.requests.GetCarsByUserRequest;
import edu.ufcg.es.es_front.models.Car;
import edu.ufcg.es.es_front.utils.ActivityUtils;
import edu.ufcg.es.es_front.R;

public class CarListActivity extends AppCompatActivity {

    private ListView carsListView;
    private FloatingActionButton floatingActionButton;
    private Context context;
    private CarsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        init();

        getCars();
    }

    private void init() {
        this.carsListView = findViewById(R.id.listViewCars);
        this.floatingActionButton = findViewById(R.id.fab);
        this.context = this.getApplicationContext();

        this.floatingActionButton.setOnClickListener(this.floatActionButtonClickListener());
        this.carsListView.setOnItemClickListener(this.vehicleListClickListener());
    }

    private void getCars(){
        ActivityUtils.showProgressDialog(this, "Getting cars");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "bearer " + UserController.getUserLogged().getToken());
        GetCarsByUserRequest getCarsByUserRequest = new GetCarsByUserRequest(getCarsByUserCallback());
        RequestQueueSingleton.getInstance(this).addToRequestQueue(getCarsByUserRequest.getRequest(headers));
    }

    private OnGetCarsByUserCallback getCarsByUserCallback(){
        return new OnGetCarsByUserCallback() {
            @Override
            public void onGetCarsByUserCallbackSucess(ArrayList<Car> response) {
                adapter = new CarsListAdapter(response, CarListActivity.this);
                carsListView.setAdapter(adapter);
                ActivityUtils.cancelProgressDialog();
            }

            @Override
            public void onGetCarsByUserCallbackError(String error) {
                ActivityUtils.cancelProgressDialog();
                ActivityUtils.showToast(CarListActivity.this, "An error ocurred");
            }
        };
    }

    private View.OnClickListener floatActionButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createVehicleIntent = new Intent(context, CreateVehicleActivity.class);
                startActivity(createVehicleIntent);
            }
        };
    }

    private AdapterView.OnItemClickListener vehicleListClickListener(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent vehicleActivity = new Intent(getApplicationContext(), VehicleActivity.class);
                vehicleActivity.putExtra("car", (Car) adapter.getItem(position));
                startActivity(vehicleActivity);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
        } else if(id == R.id.action_logout) {
            UserController.logout(this);
        }

        return super.onOptionsItemSelected(item);
    }


}
