package com.kakaw.peepshow.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.kakaw.peepshow.R;
import com.kakaw.peepshow.fragment.DropInfoListFragment;
import com.kakaw.peepshow.fragment.DropInfoMapFragment;
import com.kakaw.peepshow.module.UserModule;

import java.util.Arrays;


public class MainActivity extends BaseActivity {

    private boolean mShowingList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity_layout);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new DropInfoMapFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_view_as_map:
                flipCard();
                return true;
            case R.id.action_view_as_list:
                flipCard();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void flipCard() {
        if (mShowingList) {
            getFragmentManager().popBackStack();
            mShowingList = false;
            return;
        }

        // Flip to the back.

        mShowingList = true;

        // Create and commit a new fragment transaction that adds the fragment for the back of
        // the card, uses custom animations, and is part of the fragment manager's back stack.

        getFragmentManager()
                .beginTransaction()

                        // Replace the default fragment animations with animator resources representing
                        // rotations when switching to the back of the card, as well as animator
                        // resources representing rotations when flipping back to the front (e.g. when
                        // the system Back button is pressed).
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)

                        // Replace any fragments currently in the container view with a fragment
                        // representing the next page (indicated by the just-incremented currentPage
                        // variable).
                .replace(R.id.container, new DropInfoListFragment())

                        // Add this transaction to the back stack, allowing users to press Back
                        // to get to the front of the card.
                .addToBackStack(null)

                        // Commit the transaction.
                .commit();
    }


    /**
     * A list of modules to use for the individual activity graph. Subclasses can override this
     * method to provide additional modules provided they call and include the modules returned by
     * calling {@code super.getModules()}.
     */
    @Override
    protected java.util.List<Object> getModules() {
        return Arrays.<Object>asList(new UserModule(this));
    }

}
