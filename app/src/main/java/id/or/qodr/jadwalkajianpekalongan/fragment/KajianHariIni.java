package id.or.qodr.jadwalkajianpekalongan.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.or.qodr.jadwalkajianpekalongan.DashboardActivity;
import id.or.qodr.jadwalkajianpekalongan.R;
import id.or.qodr.jadwalkajianpekalongan.classes.DataKajian;
import id.or.qodr.jadwalkajianpekalongan.core.API;
import id.or.qodr.jadwalkajianpekalongan.model.JadwalModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class KajianHariIni extends Fragment {

    private RecyclerView rvKHari;
    private DataKajian dataKajian;
    private List<JadwalModel> jadwal;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.list_kajian_hari, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        dataKajian = new DataKajian(getActivity());
        API api = new API();

        Date today = new Date();
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todate = dateFormat1.format(today.getTime());

        jadwal = new ArrayList<JadwalModel>();
        rvKHari = (RecyclerView) getActivity().findViewById(R.id.rcvKajHari);

        dataKajian.getJadwalKHari(rvKHari, api.GET_JADWAL_HARI+todate);
    }

}
