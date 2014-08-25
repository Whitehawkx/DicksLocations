
package com.jonathansteadman.dickslocations;

import android.support.v4.app.Fragment;

public class DicksDetailsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new DicksDetailsFragment();
    }

}
