package edu.ufcg.es.es_front.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import edu.ufcg.es.es_front.R;
import edu.ufcg.es.es_front.controllers.UserController;
import edu.ufcg.es.es_front.httpClient.RequestQueueSingleton;
import edu.ufcg.es.es_front.httpClient.callbacks.OnPostLogoutCallback;
import edu.ufcg.es.es_front.httpClient.requests.PostLogoutRequest;
import edu.ufcg.es.es_front.utils.ActivityUtils;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.init();
        this.setListeners();

        setSupportActionBar(this.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



    }

    private void init(){
        this.toolbar = findViewById(R.id.toolbar);
        this.floatingActionButton = findViewById(R.id.fab);
        this.drawerLayout = findViewById(R.id.drawer_layout);
        this.navigationView = findViewById(R.id.nav_view);

        this.context = this.getApplicationContext();
    }

    private void setListeners() {
        this.floatingActionButton.setOnClickListener(this.floatActionButtonClickListener());
        navigationView.setNavigationItemSelectedListener(this.navigationItemSelectedListener());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            this.logout();
        }

        return super.onOptionsItemSelected(item);
    }

    private NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener(){
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_manage) {
                    Intent aboutIntent = new Intent(context, SettingsActivity.class);
                    startActivity(aboutIntent);

                } else if (id == R.id.about_option) {
                    Intent aboutIntent = new Intent(context, AboutActivity.class);
                    startActivity(aboutIntent);

                } else if (id == R.id.my_vehicles_option){
                    Intent myVehiclesIntent = new Intent(context, CarListActivity.class);
                    startActivity(myVehiclesIntent);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
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

    private void logout() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("id", UserController.getUserLogged().get_id());

        ActivityUtils.showProgressDialog(this, "Loging out");

        PostLogoutRequest postLogoutRequest = new PostLogoutRequest(postLogoutCallback());
        RequestQueueSingleton.getInstance(this).addToRequestQueue(postLogoutRequest.getRequest(params));
    }

    private OnPostLogoutCallback postLogoutCallback(){
        return new OnPostLogoutCallback() {
            @Override
            public void onPostLogoutCallbackSucess() {
                ActivityUtils.cancelProgressDialog();
                UserController.logout();
                finish();
            }

            @Override
            public void onPostLogoutCallbackError(String message) {
                ActivityUtils.cancelProgressDialog();
                ActivityUtils.showToast(getApplicationContext(), "Error on logout");
            }
        };
    }
}
