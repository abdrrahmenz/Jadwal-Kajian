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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.or.qodr.jadwalkajianpekalongan.DashboardActivity;
import id.or.qodr.jadwalkajianpekalongan.R;
import id.or.qodr.jadwalkajianpekalongan.ToolbarHide;
import id.or.qodr.jadwalkajianpekalongan.adapter.AdapterKPekan;
import id.or.qodr.jadwalkajianpekalongan.classes.DataKajian;
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
public class KajianPekanIni extends Fragment {

    private DataKajian dataKajian;
    private API api;
    private List<JadwalModel> jadwal;
    private String start;
    private String ends;
    private RecyclerView rvKajian;
    private SwipeRefreshLayout swipeRefresh;
    View rootView;
    OkHttpClient okHttpClient;
    AdapterKPekan adapterListPekan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = container;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.rcv_list_kajian, container, false);

        setHasOptionsMenu(true);

        okHttpClient = new OkHttpClient();

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        rvKajian = (RecyclerView) view.findViewById(R.id.rcvKajian);
        adapterListPekan = new AdapterKPekan(getActivity());
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        rvKajian.setLayoutManager(layoutManager);
        rvKajian.setAdapter(adapterListPekan);

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

        return  view;

    }

    void fetchData(){
        Date today = new Date();
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-", Locale.getDefault());
        final String todate = dateFormat1.format(today.getTime());
        SimpleDateFormat format = new SimpleDateFormat("F");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK_IN_MONTH, 0);
        Date week1 = c.getTime();
        String weeks = format.format(week1.getTime());
        int week=Integer.parseInt(weeks);
        int from=0;
        int until=0;
        if(week==1){
            from=1;
            until=7;
        }else if(week==2){
            from=8;
            until=14;
        }else if(week==3){
            from=15;
            until=21;
        }else if(week==4){
            from=22;
            until=28;
        }else if(week==5){
            from=29;
            until=31;
        }

        final ProgressDialog progress = ProgressDialog.show(getActivity(), "Loading", "Please wait ...");
        Request request = new Request.Builder().url(DashboardActivity.BASE_URL + "jadwal/" + todate + from + "/" + todate + until).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Log.i("adul", "pekan: "+e.getMessage());
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
//                Log.i("aduull", "pekan: "+strResponse);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progress.dismiss();
                            JSONObject jsonResponse = new JSONObject(strResponse);
                            if (jsonResponse.getBoolean("result")) {
                                JSONArray array = jsonResponse.getJSONArray("jadwal");
                                adapterListPekan.setJson_listkajian(array);

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
