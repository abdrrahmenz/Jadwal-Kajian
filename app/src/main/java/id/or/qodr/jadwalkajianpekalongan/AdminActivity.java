package id.or.qodr.jadwalkajianpekalongan;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.or.qodr.jadwalkajianpekalongan.adapter.ViewPagerAdapter;
import id.or.qodr.jadwalkajianpekalongan.fragment.KajianHariIni;
import id.or.qodr.jadwalkajianpekalongan.fragment.KajianPekanIni;
import me.leolin.shortcutbadger.ShortcutBadger;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class AdminActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private ViewPager viewpager;
    private TabLayout tabs;
    private Toolbar toolbar;
    private SessionManager session;
    private EditText input_fulname,input_name,input_pass,input_cfrpass;

    int inc=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        initView();
        // Session class instance
        session = new SessionManager(getApplicationContext());

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        setSupportActionBar(toolbar);

        setupViewPager(viewpager);
        viewpager.setPageTransformer(true, new CubeOutTransformer());
        tabs.setupWithViewPager(viewpager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent input = new Intent(AdminActivity.this, AdminInput.class);
//                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
//                startActivity(input);
//                finish();
            }
        });
    }

    private void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tabs = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
                            Intent intent = new Intent(AdminActivity.this,
                                    SplashScreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                        }
                    }).create().show();

        }
        return super.onKeyDown(keyCode, event);
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

        HashMap<String, String> user = session.getUserDetails();
        final String _id = user.get(SessionManager.KEY_ID);
        String fulname = user.get(SessionManager.KEY_FULNAME);
        String name = user.get(SessionManager.KEY_NAME);
        final String pass = user.get(SessionManager.KEY_PASS);

        switch (item.getItemId()) {
            case R.id.profil:
                AlertDialog.Builder alert = new AlertDialog.Builder(AdminActivity.this);
                LayoutInflater inflat = this.getLayoutInflater();
                View dialog = inflat.inflate(R.layout.view_profile_admin, null);
                TextView txt_fulname = (TextView) dialog.findViewById(R.id.fullname);
                TextView txt_name = (TextView) dialog.findViewById(R.id.txtusername);
                final TextView txt_pass = (TextView) dialog.findViewById(R.id.txtpassword);
                final ImageButton show_pass = (ImageButton) dialog.findViewById(R.id.show_pass);
                final ImageButton hide_pass = (ImageButton) dialog.findViewById(R.id.hide_pass);

                final String pas_uniq = "******";
                txt_fulname.setText("Fullname : "+fulname);
                txt_name.setText("Username : "+name);
                txt_pass.setText("Password : "+pas_uniq);
                show_pass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_pass.setText("Password : "+pass);
                        show_pass.setVisibility(View.GONE);
                        hide_pass.setVisibility(View.VISIBLE);
                    }
                });
                hide_pass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txt_pass.setText("Password : "+pas_uniq);
                        hide_pass.setVisibility(View.GONE);
                        show_pass.setVisibility(View.VISIBLE);
                    }
                });
                alert.setTitle("Profile Admin Account");

                alert.setView(dialog);

                // Set up the buttons
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.setCancelable(false);
                alert.show();
                return true;
            case R.id.change_password:
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                // get user data from session

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.form_change_password, null);
                input_fulname = (EditText) dialogView.findViewById(R.id.edt_fulname_form);
                input_name = (EditText) dialogView.findViewById(R.id.edt_user_form);
                input_pass = (EditText) dialogView.findViewById(R.id.edt_pass_form);
                input_cfrpass = (EditText) dialogView.findViewById(R.id.edt_cfrpass_form);

                input_fulname.setText(fulname);
                input_name.setText(name);
                input_pass.setText(pass);
                input_cfrpass.setText(pass);
                builder.setTitle("Change Data Account");

                builder.setView(dialogView);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String fulnamUpdate = input_fulname.getText().toString();
                        String namUpdate = input_name.getText().toString();
                        String passUpdate = input_pass.getText().toString();
                        String cfrpassUpdate = input_cfrpass.getText().toString();
                        String url = "http://api.bbenkpartnersolo.com/login/update";

                        //update the nama
                        HashMap<String, String> params = new HashMap<String, String>();
//                        if (fulnamUpdate.trim().length() > 0 && namUpdate.trim().length() > 0 && passUpdate.trim().length() > 0 ){
                        if (validateAccount()){
                            if (passUpdate.equals(cfrpassUpdate)) {
                                SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date date = new Date();
                                params.put("id", _id);
                                params.put("full_name", fulnamUpdate);
                                params.put("username", namUpdate);
                                params.put("password", passUpdate);
                                params.put("created_at", currentDate.format(date));
                                params.put("updated_at", currentDate.format(date));
                                changeUserAccount(url,params);
                                session.logoutUser();
                                startActivity(new Intent(AdminActivity.this, Login.class));
                                finish();
                                Toast.makeText(getApplicationContext(), _id + "\n " + fulnamUpdate + "\n " + namUpdate + " " + passUpdate, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(), "Password don't match", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Please insert correctly", Toast.LENGTH_LONG).show();
                        }



                    }
                });
                builder.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setCancelable(false);
                builder.show();
                return true;
            case R.id.logout:
                // Clear the session data
                // This will clear all session data and
                // redirect user to LoginActivity
                session.logoutUser();
                Intent intent = new Intent(AdminActivity.this,
                        Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            case R.id.push_notif:

                sendNotifToKajian();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void sendNotifToKajian() {

        JSONObject bodyParamJson = new JSONObject();
        try {
            bodyParamJson.put("to", "/topics/news");
            bodyParamJson.put("priority", "high");

            JSONObject dataJson = new JSONObject();
            dataJson.put("title", "Info Kajian");
            dataJson.put("body", "Jangan Lupa Kajian Hari ini :)");
            dataJson.put("sound", "default");
            dataJson.put("badge", inc);
            dataJson.put("msgCode", "newKajian");
            bodyParamJson.put("data", dataJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
inc++;
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody bodyParam = RequestBody.create(JSON, bodyParamJson.toString());
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "key=AAAAhaL4GqQ:APA91bFaoBu4H1HXTxI_bZjeEUycWqYtqLO_iCDQKXxlFIydYEMmL04tZR3ifiwo29lFIgI_Z88rLG-L_6WwfBAd9njeXyBdcmo3LzxEbg6pDg_7pYLv0Cx9N1Lpj-qf4QqTO71PsVbI")
                .post(bodyParam)
                .build();
        SplashScreen.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AdminActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ShortcutBadger.removeCount(this);
    }

    private boolean validateAccount() {
        if (TextUtils.isEmpty(input_fulname.getText())){
            input_fulname.setError("kosong");
            return false;
        }else if (TextUtils.isEmpty(input_name.getText())) {
            input_name.setError("kosong");
            return false;
        }else if (TextUtils.isEmpty(input_pass.getText())) {
            input_pass.setError("kosong");
            return false;
        }else if (TextUtils.isEmpty(input_cfrpass.getText())) {
            input_cfrpass.setError("kosong");
            return false;
        }
        return true;
    }

    private void changeUserAccount(String url, final HashMap<String, String> params){
        StringRequest post = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(getApplicationContext(), "Successfuly Change Password", Toast.LENGTH_SHORT).show();
                    Log.i("RESPONSE", "onResponse: "+response);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Gagal Change Password, Coba Lagi!", Toast.LENGTH_SHORT).show();
                Log.d("error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = params;
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(post);
    }

    private void showProfile() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.view_bottom_sheet_profile, null);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }
}

