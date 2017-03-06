package id.or.qodr.jadwalkajianpekalongan.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.or.qodr.jadwalkajianpekalongan.AdminActivity;
import id.or.qodr.jadwalkajianpekalongan.AdminInput;
import id.or.qodr.jadwalkajianpekalongan.DetailKHari;
import id.or.qodr.jadwalkajianpekalongan.DetailKPekan;
import id.or.qodr.jadwalkajianpekalongan.MapsActivity;
import id.or.qodr.jadwalkajianpekalongan.R;
import id.or.qodr.jadwalkajianpekalongan.SessionManager;
import id.or.qodr.jadwalkajianpekalongan.core.API;
import id.or.qodr.jadwalkajianpekalongan.core.Utils;
import id.or.qodr.jadwalkajianpekalongan.model.JadwalModel;
import id.or.qodr.jadwalkajianpekalongan.model.Location;

/**
 * Created by adul on 23/01/17.
 */

public class AdapterKPekan extends RecyclerView.Adapter<VHKPekan> {

    private Context context;
    private List<JadwalModel> listJadwal;
    private Utils utils;
    private SessionManager session;
    private API api;

    AdapterKHari adpter;
    private String _id, lat, lng, imgUri, tema, pemateri, lokasi, cp, mulei,smpei;

    public AdapterKPekan(Context context, List<JadwalModel> listJadwal) {
        this.context = context;
        this.listJadwal = listJadwal;
    }

    @Override
    public VHKPekan onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_kajian_pekan_ini, null);

        // Session Manager
        session = new SessionManager(context);

        adpter = new AdapterKHari(context,listJadwal);

        utils = new Utils(context);
        api = new API();

        return new VHKPekan(layoutView);
    }

    @Override
    public void onBindViewHolder(VHKPekan holder,final int position) {
        final JadwalModel index = listJadwal.get(position);

        String[] pisah = index.tanggal.split("-");
        holder.tanggal.setText(pisah[2]);
        holder.bulan.setText(utils.monthPekan(pisah[1]));

        String[] mule = index.mulai.split(":");
        holder.mulai.setText(mule[0]+":"+mule[1]);

        String[] sampe = index.sampai.split(":");
        holder.sampai.setText(sampe[0]+":"+sampe[1]);

//        String imgUri = listJadwal.get(position).foto_masjid.toString();
//        Glide.with(context).load(imgUri).into(holder.img);

        holder.tema.setText(index.tema);
        holder.pemateri.setText(index.pemateri);
        holder.lokasi.setText(index.lokasi);
        holder.cp.setText(index.cp);

        holder.rootViewPekan.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        // get user data from session
                        HashMap<String, String> user = session.getUserDetails();
                        // name
                        String name = user.get(SessionManager.KEY_NAME);

                        //mengirim data ke activity yg lain
                        _id = index.id.toString();
                        lat = index.lat.toString();
                        lng = index.lng.toString();
                        imgUri = index.foto_masjid.toString();
                        tema = index.tema.toString();
                        pemateri = index.pemateri.toString();
                        lokasi = index.lokasi.toString();
                        cp = index.cp.toString();
                        mulei = index.mulai.toString();
                        smpei = index.sampai.toString();
                        switch (which) {
                            case 0:
                                if (!session.isLoggedIn()){
                                    Toast.makeText(context, "Maaf hanya admin yang bisa Edit", Toast.LENGTH_SHORT).show();
                                }else {
                                    name.equals("");
                                    Intent intent = new Intent(context, AdminInput.class);
                                    intent.putExtra("id_key", _id);
                                    intent.putExtra("lat_key", lat);
                                    intent.putExtra("lng_key", lng);
                                    intent.putExtra("mule_key", mulei);
                                    intent.putExtra("sampe_key", smpei);
                                    intent.putExtra("img_key", imgUri);
                                    intent.putExtra("tema_key", tema);
                                    intent.putExtra("pemteri_key", pemateri);
                                    intent.putExtra("lokasi_key", lokasi);
                                    intent.putExtra("cp_key", cp);

                                    context.startActivity(intent);
                                    Toast.makeText(context, "Position is " + _id, Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case 1:
                                if (!session.isLoggedIn()){
                                    Toast.makeText(context, "Maaf hanya admin yang bisa Hapus", Toast.LENGTH_SHORT).show();

                                }else {
                                    name.equals("");
                                    new AlertDialog.Builder(context)
                                            .setTitle("Delete Kajian")
                                            .setMessage("Are you sure want to delete this?")
                                            .setNegativeButton(android.R.string.no, null)
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface arg0, int arg1) {
                                                    deleteKajian(api.DEL_JADWAL + _id);
                                                    Intent intent = new Intent(context, AdminActivity.class);
                                                    context.startActivity(intent);

                                                }
                                            }).create().show();
                                }
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });

        holder.rootViewPekan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _id = index.id.toString();
                String lat = index.lat.toString();
                String lng = index.lng.toString();
                String imgUri = index.foto_masjid.toString();
                String tema = index.tema.toString();
                String pemateri = index.pemateri.toString();
                String lokasi = index.lokasi.toString();
                String cp = index.cp.toString();
                String mule = index.mulai.toString();
                String smpai = index.sampai.toString();
                String tggl = index.tanggal.toString();

                Intent intent = new Intent(context, DetailKPekan.class);
                intent.putExtra("id_key", _id);
                intent.putExtra("lat_key", lat);
                intent.putExtra("lng_key", lng);
                intent.putExtra("mule_key", mule);
                intent.putExtra("sampe_key", smpai);
                intent.putExtra("tgl_key", tggl);
                intent.putExtra("img_key", imgUri);
                intent.putExtra("tema_key", tema);
                intent.putExtra("pemteri_key", pemateri);
                intent.putExtra("lokasi_key", lokasi);
                intent.putExtra("cp_key", cp);

                context.startActivity(intent);
            }
        });
    }

    public void deleteKajian(String url){

        StringRequest post = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(context, "Berhasil Dihapus !", Toast.LENGTH_SHORT).show();
                    adpter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Gagal Hapus, Coba Lagi!", Toast.LENGTH_SHORT).show();

                Log.d("error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", _id);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(post);
    }

    @Override
    public int getItemCount() {
        return listJadwal.size();
    }

}
