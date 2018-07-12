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
import edu.ufcg.es.es_front.models.Car;

public class CarsListAdapter extends BaseAdapter {

    private ArrayList<Car> cars;
    private Context context;

    public CarsListAdapter(ArrayList<Car> cars, Context context) {
        super();
        this.cars = cars;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cars.size();
    }

    @Override
    public Object getItem(int position) {
        return cars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Car car = cars.get(position);
        @SuppressLint("ViewHolder")
        View view = LayoutInflater.from(context).inflate(R.layout.card_car, parent, false);
        LinearLayout carCard = view.findViewById(R.id.cardCar);
        TextView tvModel = view.findViewById(R.id.modelCarCard);
        TextView tvPlate = view.findViewById(R.id.plateCarCard);

        String model = car.getModel() + " -- ";
        tvModel.setText(model);
        tvPlate.setText(car.getPlate());

        return view;
    }
}
