package id.or.qodr.jadwalkajianpekalongan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adul on 19/01/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> kajianHariniList = new ArrayList<>();
    private final List<String> titleKajianList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return kajianHariniList.get(position);
    }

    @Override
    public int getCount() {
        return kajianHariniList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        kajianHariniList.add(fragment);
        titleKajianList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleKajianList.get(position);
    }
}
