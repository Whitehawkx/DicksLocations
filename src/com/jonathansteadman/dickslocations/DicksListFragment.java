
package com.jonathansteadman.dickslocations;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DicksListFragment extends ListFragment implements Constants {
    ArrayList<Dicks> dicks = new ArrayList<Dicks>();

    private Callbacks callbacks;

    public interface Callbacks {
        void onDicksSelected(int dicksId);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        dicks = DicksSet.getInstance().getLocations();

        ArrayAdapter<Dicks> adapter = new DicksAdapter(dicks);

        setListAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.start, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map:
                Intent map = new Intent(getActivity(), MappingActivity.class);
                map.putExtra(EXTRA_DICKS_ID, 6);
                startActivity(map);
                return true;
            case R.id.action_food:
                Intent food = new Intent(getActivity(), FoodListActivity.class);
                // food.putExtra(EXTRA_FOOD_ID, -1);
                startActivity(food);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        Dicks dicks = (Dicks)(getListAdapter()).getItem(position);
        callbacks.onDicksSelected(position);

    }

    private class DicksAdapter extends ArrayAdapter<Dicks> {
        public DicksAdapter(ArrayList<Dicks> comics) {
            super(getActivity(), 0, dicks);
        }

        @SuppressLint("NewApi")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_dicks,
                        null);
            }

            Dicks dicks = getItem(position);

            TextView titleView = (TextView)convertView.findViewById(R.id.textview_list_title);
            titleView.setText(dicks.getTitle());

            return convertView;
        }

    }
}
