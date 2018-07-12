package edu.ufcg.es.es_front.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ufcg.es.es_front.R;
import edu.ufcg.es.es_front.adapters.CarsListAdapter;
import edu.ufcg.es.es_front.controllers.UserController;
import edu.ufcg.es.es_front.httpClient.RequestQueueSingleton;
import edu.ufcg.es.es_front.httpClient.callbacks.OnGetCarsByUserCallback;
import edu.ufcg.es.es_front.httpClient.requests.GetCarsByUserRequest;
import edu.ufcg.es.es_front.models.Car;
import edu.ufcg.es.es_front.utils.ActivityUtils;

public class CarListActivity extends AppCompatActivity {

    private ListView carsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        init();

        getCars();
    }

    private void init() {
        this.carsListView = findViewById(R.id.listViewCars);
    }

    private void getCars(){
        ActivityUtils.showProgressDialog(this, "Getting cars");
        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "bearer " + UserController.getUserLogged().getToken());
        GetCarsByUserRequest getCarsByUserRequest = new GetCarsByUserRequest(getCarsByUserCallback());
        RequestQueueSingleton.getInstance(this).addToRequestQueue(getCarsByUserRequest.getRequest(headers));
    }

    private OnGetCarsByUserCallback getCarsByUserCallback(){
        return new OnGetCarsByUserCallback() {
            @Override
            public void onGetCarsByUserCallbackSucess(ArrayList<Car> response) {
                CarsListAdapter adapter = new CarsListAdapter(response, CarListActivity.this);
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


}
