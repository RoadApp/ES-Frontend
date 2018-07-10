package edu.ufcg.es.es_front.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

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
        return null;
    }
}
