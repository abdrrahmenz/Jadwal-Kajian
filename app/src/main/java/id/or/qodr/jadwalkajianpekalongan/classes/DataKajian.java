package id.or.qodr.jadwalkajianpekalongan.classes;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.or.qodr.jadwalkajianpekalongan.AdminInput;
import id.or.qodr.jadwalkajianpekalongan.R;
import id.or.qodr.jadwalkajianpekalongan.adapter.AdapterKHari;
import id.or.qodr.jadwalkajianpekalongan.adapter.AdapterKPekan;
import id.or.qodr.jadwalkajianpekalongan.adapter.ArrayAdapterFactory;
import id.or.qodr.jadwalkajianpekalongan.model.JadwalModel;
import id.or.qodr.jadwalkajianpekalongan.model.Location;

/**
 * Created by adul on 20/01/17.
 */

public class DataKajian {

    private static final String LOG = "DataKajian";
    private ArrayList<JadwalModel> jadwal = null;
    private StaggeredGridLayoutManager layoutManager;
    private ProgressDialog progressDialog;
    private Context context;

    public DataKajian(Context context) {
        this.context = context;
    }

    public void getJadwalKHari(final RecyclerView viewRcvHari, String URL) {
        showLoading();
        JsonObjectRequest request = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
                    DataParser result = gson.fromJson(response.toString(), DataParser.class);

                    jadwal = result.getJadwal();
                    layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);

                    viewRcvHari.setHasFixedSize(true);
                    viewRcvHari.setLayoutManager(layoutManager);

                    AdapterKHari adapterKHari = new AdapterKHari(context, jadwal);

                    viewRcvHari.setAdapter(adapterKHari);

                } catch (Exception e) {
                    Log.d("Exception", e.toString());
                }
                hideLoading();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                hideLoading();
                Log.d(LOG, error.toString());
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(context);

        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void getJadwalKPekan(final RecyclerView viewRcvHari, String URL) {
        showLoading();
        JsonObjectRequest request = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
                    DataParser result = gson.fromJson(response.toString(), DataParser.class);

                    jadwal = result.getJadwal();

                    layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
                    viewRcvHari.setHasFixedSize(true);
                    viewRcvHari.setLayoutManager(layoutManager);

                    AdapterKPekan adapterKHari = new AdapterKPekan(context, jadwal);

                    viewRcvHari.setAdapter(adapterKHari);


                } catch (Exception e) {
                    Log.d("Exception", e.toString());
                }
                hideLoading();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                hideLoading();
                Log.d(LOG, error.toString());
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(context);

        request.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

    public void showLoading() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
    }

    public void hideLoading() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
