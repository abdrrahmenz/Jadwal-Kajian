package id.or.qodr.jadwalkajianpekalongan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adul on 19/01/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> kajianList = new ArrayList<>();
    private final List<String> titleKajianList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return kajianList.get(position);
    }

    @Override
    public int getCount() {
        return kajianList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        kajianList.add(fragment);
        titleKajianList.add(title);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleKajianList.get(position);
    }
}
