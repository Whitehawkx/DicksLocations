
package com.jonathansteadman.dickslocations;

import android.support.v4.app.Fragment;

public class MappingActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MappingFragment();
    }

}
