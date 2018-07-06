package edu.ufcg.es.es_front.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.ufcg.es.es_front.R;
import edu.ufcg.es.es_front.controllers.UserController;
import edu.ufcg.es.es_front.httpClient.RequestQueueSingleton;
import edu.ufcg.es.es_front.httpClient.requests.PostCarRequest;
import edu.ufcg.es.es_front.utils.ActivityUtils;

public class CreateServiceActivity extends AppCompatActivity {

    private AutoCompleteTextView edtName, edtPrice, edtDate, edtActualOdometer, edtDescription;
    private String name, price, actualOdometer, description;
    private Date date;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);
        init();
        this.submitButton.setOnClickListener(this.submitOnClickListener());
    }

    private void init(){
        this.edtName = findViewById(R.id.edt_formNewService_name);
        this.edtPrice = findViewById(R.id.edt_formNewService_price);
        this.edtDate = findViewById(R.id.edt_formNewService_date);
        this.edtActualOdometer = findViewById(R.id.edt_formNewService_actualOdometer);
        this.edtDescription = findViewById(R.id.edt_formNewService_description);
        this.edtDate = findViewById(R.id.edt_formNewService_date);
        this.submitButton = findViewById(R.id.button_formNewService_submit);
    }

    private View.OnClickListener submitOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pattern = "MMddyyyy";
                SimpleDateFormat simpleDate = new SimpleDateFormat(pattern);
                name = edtName.getText().toString();
                price = edtPrice.getText().toString();
                actualOdometer = edtActualOdometer.getText().toString();
                description = edtDescription.getText().toString();

                try {
                    date = simpleDate.parse(edtDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                validateFields();
            }
        };
    }
    private void validateFields(){

        //Reset errors
        this.edtName.setError(null);
        this.edtPrice.setError(null);
        this.edtActualOdometer.setError(null);
        this.edtDescription.setError(null);

        boolean error = false;
        View focusView = null;

        if(TextUtils.isEmpty(name)){
            this.edtName.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.edtName;
            error = true;

        }else if(TextUtils.isEmpty(price)){
            this.edtPrice.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.edtPrice;
            error = true;
        }

        if(TextUtils.isEmpty(actualOdometer)){
            this.edtActualOdometer.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.edtActualOdometer;
            error = true;
        }

        if(TextUtils.isEmpty(description)){
            this.edtDescription.setError(ActivityUtils.EMPTY_ERROR);
            focusView = this.edtDescription;
            error = true;
        }


        if(error){
            focusView.requestFocus();
        }else{
            sendRegistration();
        }

    }

    private void sendRegistration(){
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String> headers = new HashMap<>();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        params.put("name", name);
        params.put("price", price);
        params.put("date", format.format(date));
        params.put("actualOdometer", actualOdometer);
        params.put("description", description);

        headers.put("authorization", "bearer " + UserController.getUserLogged().getToken());

        ActivityUtils.showProgressDialog(this, "Registering Service");

        PostServiceRequest postServiceRequest = new PostCarRequest(postServiceCallback());
        RequestQueueSingleton.getInstance(this).addToRequestQueue(postServiceRequest.getRequest(params, headers));


    }
}
