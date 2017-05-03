package id.or.qodr.jadwalkajianpekalongan.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import id.or.qodr.jadwalkajianpekalongan.AdminActivity;
import id.or.qodr.jadwalkajianpekalongan.AdminInput;
import id.or.qodr.jadwalkajianpekalongan.DetailKHari;
import id.or.qodr.jadwalkajianpekalongan.R;
import id.or.qodr.jadwalkajianpekalongan.SessionManager;
import id.or.qodr.jadwalkajianpekalongan.core.API;
import id.or.qodr.jadwalkajianpekalongan.model.JadwalModel;

/**
 * Created by adul on 20/01/17.
 */

public class AdapterKHari extends RecyclerView.Adapter<VHKHari> {

    private Context context;
    private List<JadwalModel> listJadwal;
    private SessionManager session;
    private API api;
    private AdapterKHari adpter;
    JSONArray json_listkajian;
    private String date, _id, type, tgl, pekan, day, lat, lng, imgUri, tema, pemateri, lokasi, cp, mulei,smpei;

//    public AdapterKHari(Context context, List<JadwalModel> listJadwal) {
//        this.context = context;
//        this.listJadwal = listJadwal;
//    }

    public void setJson_listkajian(JSONArray json_listkajian) {
        this.json_listkajian = json_listkajian;
        notifyDataSetChanged();
    }

    public AdapterKHari(Context context) {
        this.context = context;
    }

    @Override
    public VHKHari onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_kajian_hari_ini, null);

        // Session Manager
        session = new SessionManager(context);

//        adpter = new AdapterKHari(context,listJadwal);

        api = new API();

        return new VHKHari(layoutView);
    }

    @Override
    public void onBindViewHolder(VHKHari holder, final int position) {
        try {
            final JSONObject listData = json_listkajian.getJSONObject(position);

            Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/digital-7.ttf");

            Date today = new Date();
            DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            final String todate = dateFormat1.format(today.getTime());
            SimpleDateFormat format = new SimpleDateFormat("F",Locale.getDefault());
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_WEEK_IN_MONTH, 0);
            Date week1 = c.getTime();
            final String week = format.format(week1.getTime());

            date = getDayOnWeek(listData.getString("setiap_hari"), Integer.parseInt(week));
            Log.i("aduull", "onBindViewHolder: "+date);
            if (listData.getString("pekan").isEmpty() && listData.getString("setiap_hari").isEmpty()) {
                String[] mule = listData.getString("mulai").split(":");
                holder.mulai.setText(mule[0]+":"+mule[1]);
                holder.mulai.setTypeface(custom_font, Typeface.BOLD);

                String[] sampe = listData.getString("sampai").split(":");
                holder.sampai.setText(sampe[0]+":"+sampe[1]);
                holder.sampai.setTypeface(custom_font, Typeface.BOLD);

                holder.tema.setText(listData.getString("tema"));
                holder.pemateri.setText(listData.getString("pemateri"));
                holder.lokasi.setText(listData.getString("lokasi"));
                holder.lat.setText(listData.getString("lat"));
                holder.lng.setText(listData.getString("lng"));
                holder.cp.setText(listData.getString("cp"));
            } else if (date.equals(todate)){
                String[] mule = listData.getString("mulai").split(":");
                holder.mulai.setText(mule[0]+":"+mule[1]);
                holder.mulai.setTypeface(custom_font, Typeface.BOLD);

                String[] sampe = listData.getString("sampai").split(":");
                holder.sampai.setText(sampe[0]+":"+sampe[1]);
                holder.sampai.setTypeface(custom_font, Typeface.BOLD);

                holder.tema.setText(listData.getString("tema"));
                holder.pemateri.setText(listData.getString("pemateri"));
                holder.lokasi.setText(listData.getString("lokasi"));
                holder.lat.setText(listData.getString("lat"));
                holder.lng.setText(listData.getString("lng"));
                holder.cp.setText(listData.getString("cp"));
            }else {
                Toast.makeText(context, "Zonk", Toast.LENGTH_SHORT).show();
            }
            holder.rootViewHari.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final CharSequence[] dialogitem = {"Edit", "Delete"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setCancelable(true);
                    dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            switch (which) {
                                case 0:
                                    if (!session.isLoggedIn()){
                                        Toast.makeText(context, "Maaf hanya admin yang bisa Edit", Toast.LENGTH_SHORT).show();
                                    }else {
                                        try {
                                            //mengirim data ke activity yg lain
                                            Intent intent = new Intent(context, AdminInput.class);
                                            intent.putExtra("id_key", listData.getString("id"));
                                            intent.putExtra("type_key", listData.getString("jenis_kajian"));
                                            intent.putExtra("tgl_key", listData.getString("tanggal"));
                                            intent.putExtra("day_key", listData.getString("setiap_hari"));
                                            intent.putExtra("pekan_key", listData.getString("pekan"));
                                            intent.putExtra("mule_key", listData.getString("mulai"));
                                            intent.putExtra("sampe_key", listData.getString("sampai"));
                                            intent.putExtra("tema_key", listData.getString("tema"));
                                            intent.putExtra("pemteri_key", listData.getString("pemateri"));
                                            intent.putExtra("lokasi_key", listData.getString("lokasi"));
                                            intent.putExtra("cp_key", listData.getString("cp"));
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            context.startActivity(intent);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    break;
                                case 1:
                                    if (!session.isLoggedIn()){
                                        Toast.makeText(context, "Maaf hanya admin yang bisa Hapus", Toast.LENGTH_SHORT).show();

                                    }else {
                                        new AlertDialog.Builder(context)
                                                .setTitle("Delete Kajian")
                                                .setMessage("Are you sure want to delete this?")
                                                .setNegativeButton(android.R.string.no, null)
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface arg0, int arg1) {
                                                     try {
                                                        deleteKajian(api.DEL_JADWAL + listData.getInt("id"));
                                                        Intent intent = new Intent(context, AdminActivity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        context.startActivity(intent);
                                                    }catch (JSONException e){
                                                        e.printStackTrace();
                                                    }

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
                    try {

                        date = getDayOnWeek(listData.getString("setiap_hari"), Integer.parseInt(week));

                        Intent intent = new Intent(context, DetailKHari.class);
                        intent.putExtra("id_key", listData.getInt("id"));
                        intent.putExtra("type_key", listData.getString("jenis_kajian"));
                        intent.putExtra("tgl_key", listData.getString("tanggal"));
                        intent.putExtra("tgl2_key", date);
                        intent.putExtra("day_key", listData.getString("setiap_hari"));
                        intent.putExtra("pekan_key", listData.getString("pekan"));
                        intent.putExtra("mule_key", listData.getString("mulai"));
                        intent.putExtra("sampe_key", listData.getString("sampai"));
                        intent.putExtra("tema_key", listData.getString("tema"));
                        intent.putExtra("pemteri_key", listData.getString("pemateri"));
                        intent.putExtra("lokasi_key", listData.getString("lokasi"));
                        intent.putExtra("cp_key", listData.getString("cp"));
                        intent.putExtra("lat_key", listData.getDouble("lat"));
                        intent.putExtra("lng_key", listData.getDouble("lng"));
                        intent.putExtra("img_key", listData.getString("foto_masjid"));
                        context.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        final JadwalModel index = listJadwal.get(position);
//
//        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/digital-7.ttf");
//
//        Date today = new Date();
//        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        final String todate = dateFormat1.format(today.getTime());
//        SimpleDateFormat format = new SimpleDateFormat("F",Locale.getDefault());
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.DAY_OF_WEEK_IN_MONTH, 0);
//        Date week1 = c.getTime();
//        final String weeks = format.format(week1.getTime());
//
//        date = getDayOnWeek(index.setiap_hari, Integer.parseInt(weeks));
//        if (index.pekan.isEmpty() && index.setiap_hari.isEmpty()) {
//            String[] mule = index.mulai.split(":");
//            holder.mulai.setText(mule[0]+":"+mule[1]);
//            holder.mulai.setTypeface(custom_font, Typeface.BOLD);
//
//            String[] sampe = index.sampai.split(":");
//            holder.sampai.setText(sampe[0]+":"+sampe[1]);
//            holder.sampai.setTypeface(custom_font, Typeface.BOLD);
//
//            holder.tema.setText(index.tema);
//            holder.pemateri.setText(index.pemateri);
//            holder.lokasi.setText(index.lokasi);
//            holder.lat.setText(index.lat);
//            holder.lng.setText(index.lng);
//            holder.cp.setText(index.cp);
//        } else if (date.equals(todate)){
//            String[] mule = index.mulai.split(":");
//            holder.mulai.setText(mule[0]+":"+mule[1]);
//            holder.mulai.setTypeface(custom_font, Typeface.BOLD);
//            String[] sampe = index.sampai.split(":");
//            holder.sampai.setText(sampe[0]+":"+sampe[1]);
//            holder.sampai.setTypeface(custom_font, Typeface.BOLD);
//
//            holder.tema.setText(index.tema);
//            holder.pemateri.setText(index.pemateri);
//            holder.lokasi.setText(index.lokasi);
//            holder.lat.setText(index.lat);
//            holder.lng.setText(index.lng);
//            holder.cp.setText(index.cp);
//        }
//
//
//        holder.rootViewHari.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(final View v) {
//
//                final CharSequence[] dialogitem = {"Edit", "Delete"};
//                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//                dialog.setCancelable(true);
//                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // TODO Auto-generated method stub
//
//                        //mengirim data ke activity yg lain
//                        _id = index.id.toString();
//                        type = index.jenis_kajian.toString();
//                        tgl = index.tanggal.toString();
//                        day = index.setiap_hari.toString();
//                        pekan = index.pekan.toString();
//                        tema = index.tema.toString();
//                        pemateri = index.pemateri.toString();
//                        lokasi = index.lokasi.toString();
//                        cp = index.cp.toString();
//                        mulei = index.mulai.toString();
//                        smpei = index.sampai.toString();
//                        switch (which) {
//                            case 0:
//                                if (!session.isLoggedIn()){
//                                    Toast.makeText(context, "Maaf hanya admin yang bisa Edit", Toast.LENGTH_SHORT).show();
//                                }else {
//                                    Intent intent = new Intent(context, AdminInput.class);
//                                    intent.putExtra("id_key", _id);
//                                    intent.putExtra("type_key", type);
//                                    intent.putExtra("tgl_key", tgl);
//                                    intent.putExtra("day_key", day);
//                                    intent.putExtra("pekan_key", pekan);
//                                    intent.putExtra("mule_key", mulei);
//                                    intent.putExtra("sampe_key", smpei);
//                                    intent.putExtra("tema_key", tema);
//                                    intent.putExtra("pemteri_key", pemateri);
//                                    intent.putExtra("lokasi_key", lokasi);
//                                    intent.putExtra("cp_key", cp);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    context.startActivity(intent);
//                                }
//                                break;
//                            case 1:
//                                if (!session.isLoggedIn()){
//                                    Toast.makeText(context, "Maaf hanya admin yang bisa Hapus", Toast.LENGTH_SHORT).show();
//
//                                }else {
//                                    new AlertDialog.Builder(context)
//                                            .setTitle("Delete Kajian")
//                                            .setMessage("Are you sure want to delete this?")
//                                            .setNegativeButton(android.R.string.no, null)
//                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface arg0, int arg1) {
//                                                    deleteKajian(api.DEL_JADWAL + _id);
//                                                    Intent intent = new Intent(context, AdminActivity.class);
//                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                    context.startActivity(intent);
//
//                                                }
//                                            }).create().show();
//                                }
//                                break;
//                        }
//                    }
//                }).show();
//                return false;
//            }
//        });
//        holder.rootViewHari.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //mengirim data ke activity yg lain
//                _id = index.id.toString();
//                type = index.jenis_kajian.toString();
//                tgl = index.tanggal.toString();
//                day = index.setiap_hari.toString();
//                pekan = index.pekan.toString();
//                mulei = index.mulai.toString();
//                smpei = index.sampai.toString();
//                tema = index.tema.toString();
//                pemateri = index.pemateri.toString();
//                lokasi = index.lokasi.toString();
//                cp = index.cp.toString();
//                lat = index.lat.toString();
//                lng = index.lng.toString();
//                imgUri = index.foto_masjid.toString();
//                date = getDayOnWeek(index.setiap_hari, Integer.parseInt(weeks));
//
//                Intent intent = new Intent(context, DetailKHari.class);
//                intent.putExtra("id_key", _id);
//                intent.putExtra("type_key", type);
//                intent.putExtra("tgl_key", tgl);
//                intent.putExtra("tgl2_key", date);
//                intent.putExtra("day_key", day);
//                intent.putExtra("pekan_key", pekan);
//                intent.putExtra("mule_key", mulei);
//                intent.putExtra("sampe_key", smpei);
//                intent.putExtra("tema_key", tema);
//                intent.putExtra("pemteri_key", pemateri);
//                intent.putExtra("lokasi_key", lokasi);
//                intent.putExtra("cp_key", cp);
//                intent.putExtra("lat_key", lat);
//                intent.putExtra("lng_key", lng);
//                intent.putExtra("img_key", imgUri);
//
//                Toast.makeText(context, "Detail Kajian ", Toast.LENGTH_SHORT).show();
//                context.startActivity(intent);
//            }
//        });
    }

    public String getDayOnWeek(String dayNow,int week){
        String reDate = "";
        int from=0;
        int until=0;
        if(week==1){
            from=1;
            until=7;
        }else if(week==2){
            from=8;
            until=14;
        }else if(week==3){
            from=15;
            until=21;
        }else if(week==4){
            from=22;
            until=28;
        }else if(week==5){
            from=29;
            until=31;
        }
        for(int i=from;i<=until;i++){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            Calendar calendar = new GregorianCalendar(year,month,i);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String day=getDayName(dayOfWeek);

            if(dayNow.equals(day)){
                reDate=sdf.format(calendar.getTime());
                break;
            }
        }
        return reDate;
    }

    public String getDayName(int dayOfWeek){
        String day=null;
        switch(dayOfWeek){
            case 1:
                day="Ahad";
                break;
            case 2:
                day="Senin";
                break;
            case 3:
                day="Selasa";
                break;
            case 4:
                day="Rabu";
                break;
            case 5:
                day="Kamis";
                break;
            case 6:
                day="Jumat";
                break;
            case 7:
                day="Sabtu";
                break;
        }
        return day;
    }

    private void deleteKajian(String url){
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
    private void deleteKajianYesterday(String url){
        StringRequest post = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    adpter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "KajianBefore can't delete", Toast.LENGTH_SHORT).show();
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
//        if (listJadwal!=null){
//            return listJadwal.size();
//        }else {
//            return 0;
//        }
        if (json_listkajian!=null){
            return json_listkajian.length();
        }else {
            return 0;
        }
    }
}
