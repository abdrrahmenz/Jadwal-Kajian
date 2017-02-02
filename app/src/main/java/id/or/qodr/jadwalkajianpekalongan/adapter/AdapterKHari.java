package id.or.qodr.jadwalkajianpekalongan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import id.or.qodr.jadwalkajianpekalongan.DetailKHari;
import id.or.qodr.jadwalkajianpekalongan.R;
import id.or.qodr.jadwalkajianpekalongan.model.JadwalModel;

/**
 * Created by adul on 20/01/17.
 */

public class AdapterKHari extends RecyclerView.Adapter<VHKHari> {

    private Context context;
    private List<JadwalModel> listJadwal;

    public AdapterKHari(Context context, List<JadwalModel> listJadwal) {
        this.context = context;
        this.listJadwal = listJadwal;
    }

    @Override
    public VHKHari onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_kajian_hari_ini, null);
        return new VHKHari(layoutView);
    }

    @Override
    public void onBindViewHolder(VHKHari holder, final int position) {
        String[] mule = listJadwal.get(position).mulai.split(":");
        holder.mulai.setText(mule[0]+":"+mule[1]);
        String[] sampe = listJadwal.get(position).sampai.split(":");
        holder.sampai.setText(sampe[0]+":"+sampe[1]);

        holder.tema.setText(listJadwal.get(position).tema);
        holder.pemateri.setText(listJadwal.get(position).pemateri);
        holder.lokasi.setText(listJadwal.get(position).lokasi);
        holder.cp.setText(listJadwal.get(position).cp);

        holder.rootViewHari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailKHari.class);
                context.startActivity(intent);
                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listJadwal.size();
    }
}
