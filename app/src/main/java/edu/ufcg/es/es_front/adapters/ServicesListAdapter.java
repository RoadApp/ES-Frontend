package edu.ufcg.es.es_front.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import edu.ufcg.es.es_front.R;
import edu.ufcg.es.es_front.models.Service;

public class ServicesListAdapter extends BaseAdapter {

    private ArrayList<Service> services;
    private Context context;
    private float totalCost;

    public ServicesListAdapter(ArrayList<Service> services, Context context) {
        super();
        this.services = services;
        this.context = context;

        this.totalCost = this.calculateCost();
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int position) {
        return services.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Service service = services.get(position);
        @SuppressLint("ViewHolder")
        View view = LayoutInflater.from(context).inflate(R.layout.card_service, parent, false);
        LinearLayout serviceCard = view.findViewById(R.id.cardService);
        TextView tvName = view.findViewById(R.id.nameServiceCard);
        TextView tvPrice = view.findViewById(R.id.priceServiceCard);

        String name = service.getDescription() + " -- ";
        tvName.setText(name);
        tvPrice.setText("$: " + String.valueOf(service.getExpense()));

        return view;
    }

    public float getTotalCost() {
        return totalCost;
    }

    private float calculateCost(){
        float cost = 0;
        for (Service service: services) {
            cost += service.getExpense();
        }

        return cost;
    }
}
