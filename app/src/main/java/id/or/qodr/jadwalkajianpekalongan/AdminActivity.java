package id.or.qodr.jadwalkajianpekalongan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.or.qodr.jadwalkajianpekalongan.adapter.ViewPagerAdapter;
import id.or.qodr.jadwalkajianpekalongan.fragment.KajianHariIni;
import id.or.qodr.jadwalkajianpekalongan.fragment.KajianPekanIni;

public class AdminActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.btnDel)
    com.github.clans.fab.FloatingActionButton btnDel;
    @BindView(R.id.btnEdt)
    com.github.clans.fab.FloatingActionButton btnEdt;
    @BindView(R.id.btnAdd)
    com.github.clans.fab.FloatingActionButton btnAdd;
    @BindView(R.id.fabMenu)
    FloatingActionMenu fabMenu;

    // Session Manager Class
    SessionManager session;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        setSupportActionBar(toolbar);

        setupViewPager(viewpager);

        tabs.setupWithViewPager(viewpager);

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = false;
        Toast.makeText(this, "Please Logout to exit", Toast.LENGTH_SHORT).show();


    }

    @OnClick({R.id.btnDel, R.id.btnEdt, R.id.btnAdd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDel:
                break;
            case R.id.btnEdt:
                if (btnEdt.isClickable()){

                }
                break;
            case R.id.btnAdd:
                Intent input = new Intent(this, AdminInput.class);
//                input.putExtra("input", "dari_input");
                startActivity(input);
                finish();
                break;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new KajianHariIni(), "Kajian Hari Ini");
        adapter.addFragment(new KajianPekanIni(), "Kajian Pekan Ini");
        viewPager.setAdapter(adapter);
        viewpager.getAdapter().notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /**
             * Logout button click event
             * */
            case R.id.logout:
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();
                startActivity(new Intent(AdminActivity.this, Login.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}

