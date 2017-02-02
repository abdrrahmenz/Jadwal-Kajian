package id.or.qodr.jadwalkajianpekalongan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import id.or.qodr.jadwalkajianpekalongan.MapsActivity;
import id.or.qodr.jadwalkajianpekalongan.R;
import id.or.qodr.jadwalkajianpekalongan.core.Utils;
import id.or.qodr.jadwalkajianpekalongan.model.JadwalModel;

/**
 * Created by adul on 23/01/17.
 */

public class AdapterKPekan extends RecyclerView.Adapter<VHKPekan> {

    private Context context;
    private List<JadwalModel> listJadwal;
    private Utils utils;

    public AdapterKPekan(Context context, List<JadwalModel> listJadwal) {
        this.context = context;
        this.listJadwal = listJadwal;
    }

    @Override
    public VHKPekan onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_kajian_pekan_ini, null);

        utils = new Utils(context);
        VHKPekan rcv = new VHKPekan(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(VHKPekan holder, int position) {

        String[] pisah = listJadwal.get(position).tanggal.split("-");
        holder.tanggal.setText(pisah[2]);
        holder.bulan.setText(utils.monthPekan(pisah[1]));

        String[] mule = listJadwal.get(position).mulai.split(":");
        holder.mulai.setText(mule[0]+":"+mule[1]);

        String[] sampe = listJadwal.get(position).sampai.split(":");
        holder.sampai.setText(sampe[0]+":"+sampe[1]);

        holder.tema.setText(listJadwal.get(position).tema);
        holder.pemateri.setText(listJadwal.get(position).pemateri);
        holder.lokasi.setText(listJadwal.get(position).lokasi);
        holder.cp.setText(listJadwal.get(position).cp);
        holder.rootViewPekan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MapsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listJadwal.size();
    }

}
