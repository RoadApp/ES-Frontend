package edu.ufcg.es.es_front.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ufcg.es.es_front.R;
import edu.ufcg.es.es_front.adapters.ServicesListAdapter;
import edu.ufcg.es.es_front.controllers.UserController;
import edu.ufcg.es.es_front.httpClient.RequestQueueSingleton;
import edu.ufcg.es.es_front.httpClient.callbacks.OnGetServicesByCarCallback;
import edu.ufcg.es.es_front.httpClient.requests.GetServicesByCarRequest;
import edu.ufcg.es.es_front.models.Car;
import edu.ufcg.es.es_front.models.Service;
import edu.ufcg.es.es_front.utils.ActivityUtils;

public class VehicleActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private Context context;
    private Car car;

    private TextView tvCarModel, tvCarPlate;

    private ListView servicesListView;
    private ServicesListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        this.car = (Car) getIntent().getSerializableExtra("car");

        this.init();
        this.getServices();

        Log.d("@@", car.toString());
    }

    private void init(){
        this.context = this.getApplicationContext();
        this.floatingActionButton = findViewById(R.id.fab_newVehicleService);
        this.tvCarModel = findViewById(R.id.carModelDetail);
        this.tvCarPlate = findViewById(R.id.carPlateDetail);

        this.tvCarModel.setText(this.car.getModel());
        this.tvCarPlate.setText(this.car.getPlate());

        this.servicesListView = findViewById(R.id.servicesListView);

        this.floatingActionButton.setOnClickListener(this.floatActionButtonClickListener());
    }

    private View.OnClickListener floatActionButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createServiceIntent = new Intent(context, CreateServiceActivity.class);
                createServiceIntent.putExtra("carId", car.get_id());
                startActivity(createServiceIntent);
            }
        };
    }

    private void getServices(){
        ActivityUtils.showProgressDialog(this, "Getting services");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "bearer " + UserController.getUserLogged().getToken());
        GetServicesByCarRequest getServicesByCarRequest = new GetServicesByCarRequest(getServicesByCarCallback());
        RequestQueueSingleton.getInstance(this).addToRequestQueue(getServicesByCarRequest.getRequest(headers));
    }

    private OnGetServicesByCarCallback getServicesByCarCallback(){
        return new OnGetServicesByCarCallback() {
            @Override
            public void onGetServicesByCarCallbackSucess(ArrayList<Service> response) {
                adapter = new ServicesListAdapter(response, VehicleActivity.this);
                servicesListView.setAdapter(adapter);
                ActivityUtils.cancelProgressDialog();

            }

            @Override
            public void onGetServicesByCarCallbackError(String error) {
                ActivityUtils.cancelProgressDialog();
                ActivityUtils.showToast(VehicleActivity.this, "An error ocurred");
            }
        };
    }
}
