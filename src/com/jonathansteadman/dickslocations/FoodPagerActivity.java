
package com.jonathansteadman.dickslocations;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class FoodPagerActivity extends FragmentActivity implements Constants {
    private ViewPager viewPager;

    private Context context;

    private ArrayList<Food> data = FoodSet.newInstance(context).getFood();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewPager);
        setContentView(viewPager);

        FragmentManager manager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(manager) {

            @Override
            public Fragment getItem(int position) {
                return FoodFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return data.size();
            }

        });

        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int pos) {
                Food food = data.get(pos);
                setTitle(food.getTitle());
            }

        });

        int id = getIntent().getIntExtra(EXTRA_FOOD_ID, 0);
        viewPager.setCurrentItem(id);
    }
}
