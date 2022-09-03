package com.pubmania.professionista.FragmentInro;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class fragment_adapter extends FragmentStatePagerAdapter {

    public fragment_adapter(@NonNull FragmentManager fm) {
        super( fm );
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new fragment_1();
            case 1:
                return new fragment_2();
            case 2:
                return new fragment_3();
            default:
                return new fragment_1();
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 3;
    }
}
