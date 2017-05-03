package id.or.qodr.jadwalkajianpekalongan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.or.qodr.jadwalkajianpekalongan.R;

/**
 * Created by adul on 23/01/17.
 */

public class VHKPekan extends RecyclerView.ViewHolder {

    public TextView mulai, sampai, tema, pemateri, lokasi, lat, lng, cp, id, type, tanggal, bulan;
    public View rootViewPekan;
    public ImageView img;

    public VHKPekan(View itemView) {
        super(itemView);
        rootViewPekan = itemView;
//        img = (ImageView) itemView.findViewById(R.id.img);
        id = (TextView) rootViewPekan.findViewById(R.id._id);
        type = (TextView) rootViewPekan.findViewById(R.id.jniskjian);
        tanggal = (TextView) rootViewPekan.findViewById(R.id.tanggal);
        bulan = (TextView) rootViewPekan.findViewById(R.id.bulan);
        mulai = (TextView) rootViewPekan.findViewById(R.id.mulaiPekan);
        sampai = (TextView) rootViewPekan.findViewById(R.id.sampaiPekan);
        tema = (TextView) rootViewPekan.findViewById(R.id.temaPekan);
        pemateri = (TextView) rootViewPekan.findViewById(R.id.pemateriPekan);
        lokasi = (TextView) rootViewPekan.findViewById(R.id.lokasiPekan);
        lat = (TextView) rootViewPekan.findViewById(R.id.lat);
        lng = (TextView) rootViewPekan.findViewById(R.id.lng);
        cp = (TextView) rootViewPekan.findViewById(R.id.cpPekan);
    }
}
