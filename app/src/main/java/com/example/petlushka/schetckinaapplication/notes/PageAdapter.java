package com.example.petlushka.schetckinaapplication.notes;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import com.example.petlushka.schetckinaapplication.R;

/**
 * Created by Irina on 19.05.2016.
 */
public class PageAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public PageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        InProgressFragment fragment = new InProgressFragment();
        fragment.setType(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = mContext.getString(R.string.at_work);
                break;
            case 1:
                title = mContext.getString(R.string.done);
                break;
            case 2:
                title = mContext.getString(R.string.not_done);
                break;
        }
        return title;
    }
}
