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
import edu.ufcg.es.es_front.adapters.ServicesListAdapter;
import edu.ufcg.es.es_front.controllers.UserController;
import edu.ufcg.es.es_front.httpClient.RequestQueueSingleton;
import edu.ufcg.es.es_front.httpClient.callbacks.OnGetServicesByCarCallback;
import edu.ufcg.es.es_front.httpClient.requests.GetServicesByCarRequest;
import edu.ufcg.es.es_front.models.Car;
import edu.ufcg.es.es_front.models.Service;
import edu.ufcg.es.es_front.utils.ActivityUtils;
import edu.ufcg.es.es_front.R;

public class ServiceListActivity extends AppCompatActivity {

    private ListView servicesListView;
    private Context context;
    private ServicesListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        init();

        getServices();
    }

    private void init() {
        this.servicesListView = findViewById(R.id.listViewServices);
        this.context = this.getApplicationContext();
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
                adapter = new ServicesListAdapter(response, ServiceListActivity.this);
                servicesListView.setAdapter(adapter);
                ActivityUtils.cancelProgressDialog();
            }

            @Override
            public void onGetServicesByCarCallbackError(String error) {
                ActivityUtils.cancelProgressDialog();
                ActivityUtils.showToast(ServiceListActivity.this, "An error ocurred");
            }
        };
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

