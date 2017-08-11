package id.or.qodr.jadwalkajianpekalongan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import id.or.qodr.jadwalkajianpekalongan.adapter.ViewPagerAdapter;
import id.or.qodr.jadwalkajianpekalongan.fragment.KajianHariIni;
import id.or.qodr.jadwalkajianpekalongan.fragment.KajianPekanIni;
import me.leolin.shortcutbadger.ShortcutBadger;
import okhttp3.OkHttpClient;

public class DashboardActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private TextView textConection;
    private Button tryAgain;
    private RecyclerView rvKajian;
    public static final String BASE_URL = "http://api.bbenkpartnersolo.com/";
    View toolbarContainer,tabBar,coloredBackgroundView,tolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().getAttributes().windowAnimations = R.style.Fade;

        textConection = (TextView) findViewById(R.id.txconection);
        tryAgain = (Button) findViewById(R.id.tryagain);
        rvKajian = (RecyclerView) findViewById(R.id.rcvKajian);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewpager);
        viewpager.setPageTransformer(true, new CubeOutTransformer());

        tabLayout.setupWithViewPager(viewpager);
        isInternetAvailable();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(DashboardActivity.this,
                                    SplashScreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                        }
                    }).create().show();

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ShortcutBadger.removeCount(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new KajianHariIni(), "HARI INI");
        adapter.addFragment(new KajianPekanIni(), "PEKAN INI");
        viewPager.setAdapter(adapter);
        viewpager.getAdapter().notifyDataSetChanged();
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void isInternetAvailable() {
        ConnectivityManager icheck = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi = icheck.getActiveNetworkInfo() != null;
        if(wifi) {
            Toast.makeText(this, "Internet is on.", Toast.LENGTH_SHORT).show();
        } else {
            textConection.setVisibility(View.VISIBLE);
            tryAgain.setVisibility(View.VISIBLE);
            tryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });
            Toast.makeText(this, "Internet is off.", Toast.LENGTH_SHORT).show();
        }
    }
}
