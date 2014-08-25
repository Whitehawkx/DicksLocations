
package com.jonathansteadman.dickslocations;

import android.support.v4.app.Fragment;

public class FoodActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new FoodFragment();
    }

}
