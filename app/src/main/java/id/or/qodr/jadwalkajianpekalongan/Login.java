package id.or.qodr.jadwalkajianpekalongan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Login extends BaseActivity {

    private TextInputEditText input_user, input_pass;
    private Button login;
    private SessionManager session;
    // temporary string to show the parsed response
    private String jsonUsername,jsonFullname,jsonId;
    private String edt_pasword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        // Session Manager
        session = new SessionManager(getApplicationContext());

        input_user = (TextInputEditText) findViewById(R.id.input_email);
        input_pass = (TextInputEditText) findViewById(R.id.input_password);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        login = (Button) findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = input_user.getText().toString();
                edt_pasword = input_pass.getText().toString();

                if (username.trim().length() > 0 && edt_pasword.trim().length() > 0){
                    if(!username.trim().isEmpty() && !edt_pasword.trim().isEmpty()){

                        getDataUser(username,edt_pasword);
                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
//                        Toast.makeText(getApplicationContext(), " Login Successfully", Toast.LENGTH_LONG).show();

                        // Staring MainActivity
//                        Intent i = new Intent(getApplicationContext(), AdminActivity.class);
//                        startActivity(i);
//                        finish();
                    }else{
                        //]    // username / password doesn't match
//                        Toast.makeText(getApplicationContext(), " Username/Password is incorrect", Toast.LENGTH_LONG).show();

                    }
                }else {
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    Snackbar.make(login, "Please enter username and password",Snackbar.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), " Please enter username and password", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void getDataUser(final String username,final String password) {
        JsonObjectRequest request = new JsonObjectRequest("http://api.bbenkpartnersolo.com/login/"+username+"/"+password, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray jsonArray = null;
                try {

                    String check = response.getString("code");

                    jsonArray = response.getJSONArray("login");
                    jsonUsername = "";
                    jsonFullname = "";
                    jsonId= "";
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        String id = jsonObject.getString("id");
                        String fulname = jsonObject.getString("full_name");
                        String uname = jsonObject.getString("username");
                        jsonId = id;
                        jsonFullname = fulname;
                        jsonUsername = uname;
                        Log.d("AADUL", jsonObject.getString("username"));
                    }
                    if (!check.equals("0")){

                        session.createLoginSession(jsonId,jsonFullname,jsonUsername, edt_pasword);

                        Intent i = new Intent(Login.this, AdminActivity.class);

                        i.putExtra("_id", jsonId);
                        i.putExtra("fulnem", jsonFullname);
                        i.putExtra("user", jsonUsername);
                        i.putExtra("pass", edt_pasword);
                        Snackbar.make(login, " Login Successfully",Snackbar.LENGTH_SHORT).show();
                        startActivity(i);

                        Log.i("ADUL", "onResponse: " + response.toString());
                        Log.i("ADUL", "onResponse: " + jsonUsername);
                        Log.i("ADUL", "onResponse: " + edt_pasword);

                    }else {
                        Snackbar.make(login, " Username/Password is incorrect",Snackbar.LENGTH_SHORT).show();
                        Log.i("ADUL", "onResponse: NUUUULLL");
                    }

                } catch (JSONException e) {
                    Log.d("Exception", e.toString());
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        })
        {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(Login.this);

        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setShouldCache(false);
        queue.add(request);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Login.this,
                    DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
        return super.onKeyDown(keyCode, event);
    }
}
