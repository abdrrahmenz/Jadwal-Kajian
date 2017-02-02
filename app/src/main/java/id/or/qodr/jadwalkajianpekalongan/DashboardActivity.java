package id.or.qodr.jadwalkajianpekalongan;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import id.or.qodr.jadwalkajianpekalongan.adapter.ViewPagerAdapter;
import id.or.qodr.jadwalkajianpekalongan.fragment.KajianHariIni;
import id.or.qodr.jadwalkajianpekalongan.fragment.KajianPekanIni;

public class DashboardActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new KajianHariIni(), "Kajian Hari Ini");
        adapter.addFragment(new KajianPekanIni(), "Kajian Pekan Ini");
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                startActivity(new Intent(this, Login.class));
                return true;
//            case R.id.logout:
//                startActivity(new Intent(this, DashboardActivity.class));
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
