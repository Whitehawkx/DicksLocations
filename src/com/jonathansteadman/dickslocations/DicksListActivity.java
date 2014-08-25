
package com.jonathansteadman.dickslocations;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class DicksListActivity extends SingleFragmentActivity implements
        DicksListFragment.Callbacks, Constants {

    @Override
    protected Fragment createFragment() {
        return new DicksListFragment();
    }

    protected int getLayoutResId() {
        return R.layout.activity_master_layout;
    }

    @Override
    public void onDicksSelected(int dicksId) {
        if (findViewById(R.id.detail_fragment_container) == null) { // phone
            Intent intent = new Intent(this, DicksPagerActivity.class);
            intent.putExtra(EXTRA_DICKS_ID, dicksId);
            startActivity(intent);
        } else { // tablet
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            Fragment oldFragment = manager.findFragmentById(R.id.detail_fragment_container);
            Fragment newFragment = DicksDetailsFragment.newInstance(dicksId);

            if (oldFragment != null) {
                transaction.remove(oldFragment);
            }

            transaction.add(R.id.detail_fragment_container, newFragment);
            transaction.commit();
        }
    }
}
