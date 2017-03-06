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

    public TextView id, tanggal,bulan,mulai, sampai, tema, pemateri, lokasi, cp;
    public View rootViewPekan;
    public ImageView img;

    public VHKPekan(View itemView) {
        super(itemView);
        rootViewPekan = itemView;
//        img = (ImageView) itemView.findViewById(R.id.img);
        id = (TextView) itemView.findViewById(R.id._id);
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
