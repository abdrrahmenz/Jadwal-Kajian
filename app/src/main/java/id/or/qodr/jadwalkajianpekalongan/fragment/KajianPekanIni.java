package id.or.qodr.jadwalkajianpekalongan.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.or.qodr.jadwalkajianpekalongan.R;
import id.or.qodr.jadwalkajianpekalongan.classes.DataKajian;
import id.or.qodr.jadwalkajianpekalongan.core.API;
import id.or.qodr.jadwalkajianpekalongan.model.JadwalModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class KajianPekanIni extends Fragment {

    private RecyclerView rvKPekan;
    private DataKajian dataKajian;
    private List<JadwalModel> jadwal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.list_kajian_pekan, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        dataKajian = new DataKajian(getActivity());
        API api = new API();

        jadwal = new ArrayList<JadwalModel>();
        rvKPekan = (RecyclerView) getActivity().findViewById(R.id.rcvKajPekan);

        Date date = new Date();
        Calendar now = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        now.setTime(date);

        now.add(Calendar.DAY_OF_MONTH, 1);
        Date weekStart = now.getTime();

        now.add(Calendar.DAY_OF_MONTH, 6);
        Date weekEnd = now.getTime();

        String start = format.format(weekStart.getTime());
        String ends = format.format(weekEnd.getTime());

        dataKajian.getJadwalKPekan(rvKPekan, api.GET_JADWAL_PEKAN+start+"/"+ends);
    }
}
