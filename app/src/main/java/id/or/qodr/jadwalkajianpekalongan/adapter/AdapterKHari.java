package id.or.qodr.jadwalkajianpekalongan.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import id.or.qodr.jadwalkajianpekalongan.AdminActivity;
import id.or.qodr.jadwalkajianpekalongan.AdminInput;
import id.or.qodr.jadwalkajianpekalongan.DashboardActivity;
import id.or.qodr.jadwalkajianpekalongan.DetailKHari;
import id.or.qodr.jadwalkajianpekalongan.R;
import id.or.qodr.jadwalkajianpekalongan.SessionManager;
import id.or.qodr.jadwalkajianpekalongan.core.API;
import id.or.qodr.jadwalkajianpekalongan.model.JadwalModel;
import id.or.qodr.jadwalkajianpekalongan.model.Location;

/**
 * Created by adul on 20/01/17.
 */

public class AdapterKHari extends RecyclerView.Adapter<VHKHari> {

    private Context context;
    private List<JadwalModel> listJadwal;
    private SessionManager session;
    private API api;

    AdapterKHari adpter;
    private String _id, lat, lng, imgUri, tema, pemateri, lokasi, cp, mulei,smpei;

    public AdapterKHari(Context context, List<JadwalModel> listJadwal) {
        this.context = context;
        this.listJadwal = listJadwal;
    }

    @Override
    public VHKHari onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_kajian_hari_ini, null);

        // Session Manager
        session = new SessionManager(context);

        adpter = new AdapterKHari(context,listJadwal);

        api = new API();

        return new VHKHari(layoutView);
    }

    @Override
    public void onBindViewHolder(VHKHari holder, final int position) {
        final JadwalModel index = listJadwal.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = listJadwal.get(position).tanggal.toString();
        Date dateJadwal = null;
        try {
            dateJadwal = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.AM_PM);
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        Date today = cal.getTime();

        if (dateJadwal.equals(today)){
            //menampilkan jam mulai s/d selesai kajian
            String[] mule = index.mulai.split(":");
            holder.mulai.setText(mule[0]+":"+mule[1]);
            String[] sampe = index.sampai.split(":");
            holder.sampai.setText(sampe[0]+":"+sampe[1]);

            holder.tema.setText(index.tema);
            holder.pemateri.setText(index.pemateri);
            holder.lokasi.setText(index.lokasi);
            holder.lat.setText(index.lat);
            holder.lng.setText(index.lng);
            holder.cp.setText(index.cp);
        }else {
            System.out.println(" tanggal : salah "+today+" kemarin : "+dateJadwal);
        }


        //menampilkan gambar di viewpager sementara dicoment dulu
//        String imgUri = listJadwal.get(position).foto_masjid.toString();
//        Glide.with(context).load(imgUri).into(holder.img);


        holder.rootViewHari.setOnLongClickListener(new View.OnLongClickListener() {
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
        holder.rootViewHari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                Intent intent = new Intent(context, DetailKHari.class);
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

                Toast.makeText(context, "Detail Kajian ", Toast.LENGTH_SHORT).show();
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
