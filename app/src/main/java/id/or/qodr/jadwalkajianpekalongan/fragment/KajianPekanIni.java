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


        dataKajian.getJadwalKPekan(rvKPekan, api.GET_JADWAL);
    }
}
