package id.or.qodr.jadwalkajianpekalongan.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import id.or.qodr.jadwalkajianpekalongan.DashboardActivity;
import id.or.qodr.jadwalkajianpekalongan.R;
import id.or.qodr.jadwalkajianpekalongan.classes.DataKajian;
import id.or.qodr.jadwalkajianpekalongan.model.JadwalModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class KajianHariIni extends Fragment {

    private RecyclerView rvKHari;
    private DataKajian dataKajian;
    private List<JadwalModel> jadwal;
    private String URL_LIST_JADWAL_HARI="http://192.168.1.7/api/";

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

        jadwal = new ArrayList<JadwalModel>();
        rvKHari = (RecyclerView) getActivity().findViewById(R.id.rcvKajHari);


        dataKajian.getJadwalKHari(rvKHari, URL_LIST_JADWAL_HARI);

//        ((DashboardActivity) getActivity()).setActionbarTitle("Kajian Harian");
    }
}
