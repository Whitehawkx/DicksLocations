
package com.jonathansteadman.dickslocations;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FoodListFragment extends ListFragment implements Constants {

    ArrayList<Food> foods;

    public static final String LOG_KEY = "Steadman";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        foods = FoodSet.newInstance(getActivity()).getFood();

        Log.i(LOG_KEY, "In on create for todolistfragment");

        ArrayAdapter<Food> adapter = new FoodAdapter(foods);

        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        ListView listView = (ListView)view.findViewById(android.R.id.list);
        registerForContextMenu(listView);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.food_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_food:
                Intent intent = new Intent(getActivity(), FoodActivity.class);
                intent.putExtra(EXTRA_FOOD_ID, -1);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.food_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        FoodAdapter adapter = (FoodAdapter)getListAdapter();
        Food foods = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_food:
                FoodSet.deleteFood(foods);
                adapter.notifyDataSetChanged();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        Intent intent = new Intent(getActivity(), FoodPagerActivity.class);
        intent.putExtra(EXTRA_FOOD_ID, position);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((FoodAdapter)getListAdapter()).notifyDataSetChanged();
    }

    private class FoodAdapter extends ArrayAdapter<Food> {
        public FoodAdapter(ArrayList<Food> foods) {
            super(getActivity(), 0, foods);
        }

        @SuppressLint("NewApi")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_food,
                        null);
            }

            Food food = getItem(position);

            TextView titleView = (TextView)convertView.findViewById(R.id.textview_list_title);
            titleView.setText(food.getTitle());

            TextView descView = (TextView)convertView.findViewById(R.id.textview_list_desc);
            descView.setText("$" + food.getDescription());

            ImageView pictureView = (ImageView)convertView.findViewById(R.id.imageview_list_item);
            String photoFileName = "";

            if (food.getImage() != null) {
                photoFileName = food.getImage();
            } else {
                // do nothing
            }

            BitmapDrawable bitmap = null;

            if (getActivity().getFileStreamPath(photoFileName) == null)
                return convertView;

            String path = getActivity().getFileStreamPath(photoFileName).getAbsolutePath();
            if (path == null)
                return convertView;

            bitmap = PictureUtils.getScaledDrawable(getActivity(), path);
            if (bitmap == null)
                return convertView;

            pictureView.setImageDrawable(bitmap);

            return convertView;
        }

    }

}
