package id.or.qodr.jadwalkajianpekalongan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import id.or.qodr.jadwalkajianpekalongan.R;

/**
 * Created by adul on 23/01/17.
 */

public class VHKPekan extends RecyclerView.ViewHolder {

    public TextView tanggal,bulan,mulai, sampai, tema, pemateri, lokasi, cp;
    public View rootViewPekan;

    public VHKPekan(View itemView) {
        super(itemView);
        rootViewPekan = itemView;
        tanggal = (TextView) itemView.findViewById(R.id.tanggal);
        bulan = (TextView) itemView.findViewById(R.id.bulan);
        mulai = (TextView) itemView.findViewById(R.id.mulaiPekan);
        sampai = (TextView) itemView.findViewById(R.id.sampaiPekan);
        tema = (TextView) itemView.findViewById(R.id.temaPekan);
        pemateri = (TextView) itemView.findViewById(R.id.pemateriPekan);
        lokasi = (TextView) itemView.findViewById(R.id.lokasiPekan);
        cp = (TextView) itemView.findViewById(R.id.cpPekan);
    }
}
