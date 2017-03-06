package id.or.qodr.jadwalkajianpekalongan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import id.or.qodr.jadwalkajianpekalongan.R;

/**
 * Created by adul on 20/01/17.
 */
public class VHKHari extends RecyclerView.ViewHolder {

    public TextView mulai, sampai, tema, pemateri, lokasi, lat, lng, cp, id, type, tggl;
    public ImageView img;
    public View rootViewHari;

    public VHKHari(View itemView) {
        super(itemView);
        rootViewHari = itemView;
//        img = (ImageView) itemView.findViewById(R.id.img);
        id = (TextView) itemView.findViewById(R.id._id);
        type = (TextView) itemView.findViewById(R.id.jniskjian);
        tggl = (TextView) itemView.findViewById(R.id.tggl);
        mulai = (TextView) itemView.findViewById(R.id.mulaiHari);
        sampai = (TextView) itemView.findViewById(R.id.sampaiHari);
        tema = (TextView) itemView.findViewById(R.id.temaHari);
        pemateri = (TextView) itemView.findViewById(R.id.pemateriHari);
        lokasi = (TextView) itemView.findViewById(R.id.lokasiHari);
        lat = (TextView) itemView.findViewById(R.id.lat);
        lng = (TextView) itemView.findViewById(R.id.lng);
        cp = (TextView) itemView.findViewById(R.id.cpHari);
    }


}
