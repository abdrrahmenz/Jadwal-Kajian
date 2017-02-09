package id.or.qodr.jadwalkajianpekalongan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.or.qodr.jadwalkajianpekalongan.R;

/**
 * Created by adul on 20/01/17.
 */
public class VHKHari extends RecyclerView.ViewHolder {

    public TextView mulai, sampai, tema, pemateri, lokasi, cp;
    public ImageView img;
    public View rootViewHari;

    public VHKHari(View itemView) {
        super(itemView);
        rootViewHari = itemView;
        img = (ImageView) itemView.findViewById(R.id.img);
        mulai = (TextView) itemView.findViewById(R.id.mulaiHari);
        sampai = (TextView) itemView.findViewById(R.id.sampaiHari);
        tema = (TextView) itemView.findViewById(R.id.temaHari);
        pemateri = (TextView) itemView.findViewById(R.id.pemateriHari);
        lokasi = (TextView) itemView.findViewById(R.id.lokasiHari);
        cp = (TextView) itemView.findViewById(R.id.cpHari);
    }
}
