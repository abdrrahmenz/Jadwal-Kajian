package id.or.qodr.jadwalkajianpekalongan.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.or.qodr.jadwalkajianpekalongan.DashboardActivity;
import id.or.qodr.jadwalkajianpekalongan.R;
import id.or.qodr.jadwalkajianpekalongan.ToolbarHide;
import id.or.qodr.jadwalkajianpekalongan.adapter.AdapterKHari;
import id.or.qodr.jadwalkajianpekalongan.adapter.AdapterKPekan;
import id.or.qodr.jadwalkajianpekalongan.adapter.ArrayAdapterFactory;
import id.or.qodr.jadwalkajianpekalongan.classes.DataKajian;
import id.or.qodr.jadwalkajianpekalongan.classes.DataParser;
import id.or.qodr.jadwalkajianpekalongan.core.API;
import id.or.qodr.jadwalkajianpekalongan.model.JadwalModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class KajianHariIni extends Fragment {

    public RecyclerView rvKajian;
    public API api;
    public View rootView;
    public List<JadwalModel> jadwal;
    private SwipeRefreshLayout swipeRefresh;
    private String rspString;
    OkHttpClient okHttpClient;
    AdapterKHari adapterListHari;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = container;
        View view = inflater.inflate(R.layout.rcv_list_kajian, container, false);

        setHasOptionsMenu(true);

        okHttpClient = new OkHttpClient();

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        rvKajian = (RecyclerView) view.findViewById(R.id.rcvKajian);
        adapterListHari = new AdapterKHari(getActivity());
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        rvKajian.setLayoutManager(layoutManager);
        rvKajian.setAdapter(adapterListHari);

        fetchData();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
                fetchData();
            }
        });

        View tabBar = view.findViewById(R.id.tab_layout);
        View coloredBackgroundView = view.findViewById(R.id.colored_background_view);
        View toolbarContainer = view.findViewById(R.id.toolbar_container);
        View toolbar = view.findViewById(R.id.toolbar_lis);

        rvKajian.setOnScrollListener(new ToolbarHide(toolbarContainer, toolbar, tabBar, coloredBackgroundView));
        return view;
    }

    void fetchData(){
        Date today = new Date();
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        final String todate = dateFormat1.format(today.getTime());

        final ProgressDialog progress = ProgressDialog.show(getActivity(), "Loading", "Please wait ...");
        Request request = new Request.Builder().url(DashboardActivity.BASE_URL + "jadwal/" + todate).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("adul", "hari: " + e.getMessage());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        Snackbar.make(rootView, "Internet error", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String strResponse = response.body().string();
                Log.i("aduull", "hari : " + strResponse);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progress.dismiss();
                            JSONObject jsonResponse = new JSONObject(strResponse);
                            if (jsonResponse.getBoolean("result")) {
                                JSONArray array = jsonResponse.getJSONArray("jadwal");
                                adapterListHari.setJson_listkajian(array);

                            } else {
                                Snackbar.make(rootView, jsonResponse.getString("jadwal"), Snackbar.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

}
