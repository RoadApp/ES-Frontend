package edu.ufcg.es.es_front.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.ufcg.es.es_front.adapters.ServicesListAdapter;
import edu.ufcg.es.es_front.controllers.UserController;
import edu.ufcg.es.es_front.httpClient.RequestQueueSingleton;
import edu.ufcg.es.es_front.httpClient.callbacks.OnGetServicesByCarCallback;
import edu.ufcg.es.es_front.httpClient.requests.GetServicesByCarRequest;
import edu.ufcg.es.es_front.models.Service;
import edu.ufcg.es.es_front.utils.ActivityUtils;
import edu.ufcg.es.es_front.R;

public class ServiceListActivity extends AppCompatActivity {

    private ListView servicesListView;
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
    }

    private void getServices(){
        ActivityUtils.showProgressDialog(this, "Getting cars");
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


}
