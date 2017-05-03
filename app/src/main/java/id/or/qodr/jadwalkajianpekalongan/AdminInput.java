package id.or.qodr.jadwalkajianpekalongan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.or.qodr.jadwalkajianpekalongan.adapter.AdapterKHari;
import id.or.qodr.jadwalkajianpekalongan.classes.DataKajian;
import id.or.qodr.jadwalkajianpekalongan.core.API;
import id.or.qodr.jadwalkajianpekalongan.core.Utils;
import id.or.qodr.jadwalkajianpekalongan.model.JadwalModel;
import id.or.qodr.jadwalkajianpekalongan.model.Location;

public class AdminInput extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private LinearLayout tglInputLayout, opt_rutin;
    private RadioGroup radioGrub;
    private RadioButton rutin, tdkRutin;
    private TextView _id, titleTolbar;
    private Spinner spinStiaphari, lokasiInput;
    private EditText edtCp, edtTema, edtPemateri;
    private Button btnPekan, tglInput, timeMulai, timeSampai, btnCancel, btnAddkajian;
    private API api;
    private List<Location> locations;
    private ArrayAdapter<Location> locationArrayAdapter;
    private ArrayAdapter<String> dailySpinner;
    private String[] listPekan;
    private boolean[] checkedPekan;
    private ArrayList<Integer> mItemsPekan = new ArrayList<>();
    public String id,jenis_rutin,jenis_tdkrutin,setiap, pkan, tggl,mule,sampe,foto,tema,pemteri,lokasi,cp, lat, lng;
    public String tgl_req,type_req,mule_req,sampe_req,tema_req,pemteri_req,lokasi_req,cp_req, id_req,pekan_req,day_req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_input);

        getWindow().getAttributes().windowAnimations = R.style.Fade;
        initView();

        api = new API();

        opt_rutin.setVisibility(View.GONE);
        tglInputLayout.setVisibility(View.GONE);

        locations = new ArrayList<>();
        locations.add(new Location("Pilih Lokasi",-7.030593, 109.609775, api.GET_IMG+"msjid.jpg"));
        locations.add(new Location("Masjid Muadz bin Jabal Karanganyar",-7.030593, 109.609775, api.GET_IMG+"msjid.jpg"));
        locations.add(new Location("Masjid Ibnu Abbas Wiradesa", -6.887971, 109.627553, api.GET_IMG+"ibnuabbas.jpg"));
        locations.add(new Location("Masjid Imam As-Syafi'i Pekalongan", -6.892440, 109.677294, api.GET_IMG+"imamsyafii.jpg"));
        locations.add(new Location("Masjid Al-Hidayah Sepait", -6.890784, 109.589407, api.GET_IMG+"alhidayah.jpg"));
        locations.add(new Location("Masjid Al-Irsyad Pekalongan", -6.884119, 109.682104, api.GET_IMG+"alirsyad.jpg"));
        locations.add(new Location("Masjid RS.Khodijah Pekalongan",-6.886206, 109.676884,api.GET_IMG+"rsu_khodijah.jpg"));
        locations.add(new Location("Masjid Nidaus Salam Wiradesa", -6.874382, 109.667076, api.GET_IMG+"nidausalam.jpg"));
        locations.add(new Location("Mushola As-Salam Comal", -6.890306, 109.660108, api.GET_IMG+"as_salam.jpeg"));
        locations.add(new Location("Masjid Al-Hikmah Tosaran-Kedungwuni", -6.941677, 109.652681, api.GET_IMG+"alhikmah.jpg"));
        locations.add(new Location("Masjid Al-Muttaqin Wiradesa", -6.892344, 109.620116,api.GET_IMG+"muttaqin.jpeg"));
        locations.add(new Location("Mushola Abdul Ghoni Karanganyar", -7.021326, 109.647927,api.GET_IMG+"ghoni.jpg"));

        locationArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, locations);
        locationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lokasiInput.setAdapter(locationArrayAdapter);

        dailySpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,ID_SETIAP_HARI) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                ((TextView)v).setTextSize(14);
                return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                return v;
            }
        };
        dailySpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStiaphari.setAdapter(dailySpinner);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            titleTolbar.setText(R.string.title_edit_kajian);
            id_req = extras.getString("id_key");
            type_req = extras.getString("type_key");
            tgl_req = extras.getString("tgl_key");
            day_req = extras.getString("day_key");
            pekan_req = extras.getString("pekan_key");
            mule_req = extras.getString("mule_key");
            sampe_req = extras.getString("sampe_key");
            tema_req = extras.getString("tema_key");
            pemteri_req = extras.getString("pemteri_key");
            lokasi_req = extras.getString("lokasi_key");
            cp_req = extras.getString("cp_key");
            btnAddkajian.setText("Save Kajian");
            btnAddkajian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = api.PUT_JADWAL;
                    int position = lokasiInput.getSelectedItemPosition();

                    id = _id.getText().toString();
                    jenis_rutin = rutin.getText().toString();
                    jenis_tdkrutin = tdkRutin.getText().toString();
                    foto = locations.get(position).getImg();
                    setiap = spinStiaphari.getSelectedItem().toString();
                    pkan = btnPekan.getText().toString();
                    tggl = tglInput.getText().toString();
                    mule = timeMulai.getText().toString();
                    sampe = timeSampai.getText().toString();
                    tema = edtTema.getText().toString();
                    pemteri = edtPemateri.getText().toString();
                    lokasi = lokasiInput.getSelectedItem().toString();
                    lat = String.valueOf(locations.get(position).getLat());
                    lng = String.valueOf(locations.get(position).getLng());
                    cp = edtCp.getText().toString();
                    editKajian(url);
                    Intent intent = new Intent(AdminInput.this, AdminActivity.class);
                    startActivity(intent);
                    finish();

                }
            });
        }
        else {
            setTitle(getString(R.string.title_input_kajian));
            mule_req = "Hours-Minutes";
            sampe_req = "Hours-Minutes";
            tema_req = "";
            pemteri_req = "";
            lokasi_req = "";
            cp_req = "";
        }
        setKajian();

        listPekan = new String[] {"1","2","3","4","5"};
        checkedPekan = new boolean[listPekan.length];

    }

    private void initView() {
        _id = (TextView) findViewById(R.id._id);
        titleTolbar = (TextView) findViewById(R.id.toolbar_title);
        tglInputLayout = (LinearLayout) findViewById(R.id.tgl_input_layout);
        opt_rutin = (LinearLayout) findViewById(R.id.opt_rutin);
        radioGrub = (RadioGroup) findViewById(R.id.radioGroup);
        rutin = (RadioButton) findViewById(R.id.rutin);
        tdkRutin = (RadioButton) findViewById(R.id.tdkRutin);
        spinStiaphari = (Spinner) findViewById(R.id.spin_stiaphari);
        lokasiInput = (Spinner) findViewById(R.id.lokasi_input);
        edtCp = (EditText) findViewById(R.id.edt_cp);
        edtTema = (EditText) findViewById(R.id.edt_tema);
        edtPemateri = (EditText) findViewById(R.id.edt_pemateri);
        btnPekan = (Button) findViewById(R.id.btn_pekan);
        btnAddkajian = (Button) findViewById(R.id.btn_addkajian);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        tglInput = (Button) findViewById(R.id.tgl_input);
        timeMulai = (Button) findViewById(R.id.time_mulai);
        timeSampai = (Button) findViewById(R.id.time_sampai);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminInput.this,
                AdminActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setKajian(){
        _id.setText(id_req);
        if (rutin.getText().toString().equals("Rutin") == rutin.getText().toString().equals(type_req)){
            rutin.setText(type_req);
            rutin.setChecked(true);
            rutin.setBackgroundResource(R.drawable.clickable_style);
            rutin.setTextColor(getResources().getColor(R.color.white));
            tdkRutin.setBackgroundResource(R.drawable.rounded_style);
            tdkRutin.setTextColor(getResources().getColor(R.color.mdtp_numbers_text_color));
            checkJenisRutin();
            spinStiaphari.setSelection(dailySpinner.getPosition(day_req));
            btnPekan.setText(pekan_req);
        }else if (tdkRutin.getText().toString().equals("Tidak Rutin")==tdkRutin.getText().toString().equals(type_req)){
            tdkRutin.setText(type_req);
            tdkRutin.setChecked(true);
            tdkRutin.setBackgroundResource(R.drawable.clickable_style);
            tdkRutin.setTextColor(getResources().getColor(R.color.white));
            rutin.setBackgroundResource(R.drawable.rounded_style);
            rutin.setTextColor(getResources().getColor(R.color.mdtp_numbers_text_color));
            checkJenisTdkKajian();
            tglInput.setText(tgl_req);
        }
        timeMulai.setText(mule_req);
        timeSampai.setText(sampe_req);
        edtTema.setText(tema_req);
        edtPemateri.setText(pemteri_req);
        int selected_id=0;
        for(int i=0;i<=locations.size()-1;i++){
            if(lokasi_req.equals(locations.get(i).getName().toString())){
                selected_id=i;
            }
        }
        lokasiInput.setSelection(selected_id);
        edtCp.setText(cp_req);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rutin:
                if (rutin.isChecked())
                    rutin.setBackgroundResource(R.drawable.clickable_style);
                rutin.setTextColor(getResources().getColor(R.color.white));
                tdkRutin.setBackgroundResource(R.drawable.rounded_style);
                tdkRutin.setTextColor(getResources().getColor(R.color.mdtp_numbers_text_color));
                checkJenisRutin();
                break;

            case R.id.tdkRutin:
                if (tdkRutin.isChecked())
                    tdkRutin.setBackgroundResource(R.drawable.clickable_style);
                tdkRutin.setTextColor(getResources().getColor(R.color.white));
                rutin.setBackgroundResource(R.drawable.rounded_style);
                rutin.setTextColor(getResources().getColor(R.color.mdtp_numbers_text_color));
                checkJenisTdkKajian();
                break;

            case R.id.btn_pekan:
                btnPekan.setError(null);
                AlertDialog.Builder dialog = new AlertDialog.Builder(AdminInput.this);
                dialog.setTitle("Select Pekan");
                dialog.setMultiChoiceItems(listPekan, checkedPekan, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            if (!mItemsPekan.contains(indexSelected)){
                                mItemsPekan.add(indexSelected);
                            }
                        } else if (mItemsPekan.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            mItemsPekan.remove(Integer.valueOf(indexSelected));
                        }
                    }
                });

                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String item = "";
                        for (int i = 0; i < mItemsPekan.size(); i++) {
                            item = item + listPekan[mItemsPekan.get(i)];
                            if (i != mItemsPekan.size() -1) {
                                item = item + ", ";
                            }
                        }
                        btnPekan.setText(item);
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //  Your code when user clicked on Cancel
                        dialog.dismiss();
                    }
                });

                dialog.setNeutralButton("Clear List", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedPekan.length ; i++) {
                            checkedPekan[i] = false;
                            mItemsPekan.clear();
                            btnPekan.setText("Pilih Pekan");
                        }
                    }
                });
                AlertDialog mDilog = dialog.create();
                mDilog.show();
                break;

            case R.id.tgl_input:
                tglInput.setError(null);
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AdminInput.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd.show(getFragmentManager(), "Tanggal");
                break;

            case R.id.time_mulai:
                timeMulai.setError(null);
                Calendar nowTime = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = hourOfDay + ":" + minute + ":"+"00";
                                timeMulai.setText(time);
                            }
                        },
                        nowTime.get(Calendar.HOUR_OF_DAY),
                        nowTime.get(Calendar.MINUTE),
                        true
                );
                tpd.setVersion(TimePickerDialog.Version.VERSION_2);
                tpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
                tpd.show(getFragmentManager(), "Mulai");
                break;

            case R.id.time_sampai:
                timeSampai.setError(null);
                Calendar nowTimes = Calendar.getInstance();
                TimePickerDialog ttpd = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

                                String time2 = hourOfDay + ":" + minute +":"+"00";

                                timeSampai.setText(time2);
                            }
                        },
                        nowTimes.get(Calendar.HOUR_OF_DAY),
                        nowTimes.get(Calendar.MINUTE),
                        true
                );
                ttpd.setVersion(TimePickerDialog.Version.VERSION_2);
                ttpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
                ttpd.show(getFragmentManager(), "Sampai");
                break;

            case R.id.btn_cancel:
                Intent intent = new Intent(AdminInput.this,
                        AdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

            case R.id.btn_addkajian:
                String url = api.POST_JADWAL;
                HashMap<String, String> params = new HashMap<String, String>();
                int position = lokasiInput.getSelectedItemPosition();

                if (rutin.isChecked()){
                    if (validateInputRutin()){
                        params.put("jenis_kajian", rutin.getText().toString());
                        params.put("foto_masjid", locations.get(position).getImg());
                        params.put("setiap_hari", spinStiaphari.getSelectedItem().toString());
                        params.put("pekan", btnPekan.getText().toString());
                        params.put("tanggal", "");
                        params.put("mulai", timeMulai.getText().toString());
                        params.put("sampai", timeSampai.getText().toString());
                        params.put("tema", edtTema.getText().toString());
                        params.put("pemateri", edtPemateri.getText().toString());
                        params.put("lokasi", lokasiInput.getSelectedItem().toString());
                        params.put("lat", String.valueOf(locations.get(position).getLat()));
                        params.put("lng", String.valueOf(locations.get(position).getLng()));
                        params.put("cp", edtCp.getText().toString());
                        submitKajian(url,params);
                        startActivity(new Intent(AdminInput.this, AdminActivity.class));
                        finish();
                    }else {
                        Snackbar.make(btnAddkajian, "isi dengan benar dong!", Snackbar.LENGTH_SHORT).show();
                    }
                }else if (tdkRutin.isChecked()){
                    if (validateInputPekan()){
                        params.put("jenis_kajian", tdkRutin.getText().toString());
                        params.put("foto_masjid", locations.get(position).getImg());
                        params.put("setiap_hari", "");
                        params.put("pekan", "");
                        params.put("tanggal", tglInput.getText().toString());
                        params.put("mulai", timeMulai.getText().toString());
                        params.put("sampai", timeSampai.getText().toString());
                        params.put("tema", edtTema.getText().toString());
                        params.put("pemateri", edtPemateri.getText().toString());
                        params.put("lokasi", locations.get(position).getName());
                        params.put("lat", String.valueOf(locations.get(position).getLat()));
                        params.put("lng", String.valueOf(locations.get(position).getLng()));
                        params.put("cp", edtCp.getText().toString());
                        submitKajian(url,params);
                        startActivity(new Intent(AdminInput.this, AdminActivity.class));
                        finish();
                    }else{
                        Snackbar.make(btnAddkajian, "isi dengan benar dong!", Snackbar.LENGTH_SHORT).show();
                    }
                }else{
                    Snackbar.make(btnAddkajian, "Pilih Jenis Kajian dulu", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void submitKajian(String url, final HashMap<String, String> params){
        StringRequest post = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(AdminInput.this, "Sucess", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminInput.this, "Gagal Input, Coba Lagi!", Toast.LENGTH_SHORT).show();
                Log.d("error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = params;
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(post);
    }

    private void editKajian(String url){
        StringRequest post = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Toast.makeText(getApplicationContext(), "edit Sukses", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Gagal Edit, Coba Lagi!", Toast.LENGTH_SHORT).show();
                Log.d("error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if (rutin.isChecked()){
                    map.put("id", id);
                    map.put("jenis_kajian", jenis_rutin);
                    map.put("foto_masjid", foto);
                    map.put("setiap_hari", setiap);
                    map.put("pekan", pkan);
                    map.put("tanggal", "");
                    map.put("mulai", mule);
                    map.put("sampai", sampe);
                    map.put("tema", tema);
                    map.put("pemateri", pemteri);
                    map.put("lokasi", lokasi);
                    map.put("lat", lat);
                    map.put("lng", lng);
                    map.put("cp", cp);
                }else if (tdkRutin.isChecked()){
                    map.put("id", id);
                    map.put("jenis_kajian", jenis_tdkrutin);
                    map.put("foto_masjid", foto);
                    map.put("setiap_hari", "");
                    map.put("pekan", "");
                    map.put("tanggal", tggl);
                    map.put("mulai", mule);
                    map.put("sampai", sampe);
                    map.put("tema", tema);
                    map.put("pemateri", pemteri);
                    map.put("lokasi", lokasi);
                    map.put("lat", lat);
                    map.put("lng", lng);
                    map.put("cp", cp);
                }else{
                    Toast.makeText(AdminInput.this, "Not Selected", Toast.LENGTH_SHORT).show();
                }
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(post);
    }

    private boolean validateInputRutin() {
        if (spinStiaphari.getSelectedItemPosition()==0){
            TextView erorText = (TextView)spinStiaphari.getSelectedView();
            erorText.setError("null");
            return false;
        }else if (btnPekan.getText().toString().equals("Pilih Pekan")){
            btnPekan.setError("error");
            return false;
        }else if (timeMulai.getText().toString().equals("Hours-Minutes")){
            timeMulai.setError("error");
            return false;
        }else if (timeSampai.getText().toString().equals("Hours-Minutes")){
            timeSampai.setError("null");
            return false;
        }else if (TextUtils.isEmpty(edtPemateri.getText())){
            edtPemateri.setError("kosong");
            return false;
        }else if (TextUtils.isEmpty(edtTema.getText())){
            edtTema.setError("kosong");
            return false;
        }else if (lokasiInput.getSelectedItemPosition()==0){
            TextView errorText = (TextView)lokasiInput.getSelectedView();
            errorText.setError("null");
            return false;
        }else if (TextUtils.isEmpty(edtCp.getText())){
            edtCp.setError("kosong");
            return false;
        }else {
            Snackbar.make(btnAddkajian, "isi dengan benar", Snackbar.LENGTH_SHORT).show();
        }
        return true;
    }

    private boolean validateInputPekan() {
        if (tglInput.getText().toString().equals("Date-Month-Year")){
            tglInput.setError("error");
            return false;
        }else if (timeMulai.getText().toString().equals("Hours-Minutes")){
            timeMulai.setError("error");
            return false;
        }else if (timeSampai.getText().toString().equals("Hours-Minutes")){
            timeSampai.setError("null");
            return false;
        }else if (TextUtils.isEmpty(edtPemateri.getText())){
            edtPemateri.setError("kosong");
            return false;
        }else if (TextUtils.isEmpty(edtTema.getText())){
            edtTema.setError("kosong");
            return false;
        }else if (lokasiInput.getSelectedItemPosition()==0){
            TextView errorText = (TextView)lokasiInput.getSelectedView();
            errorText.setError("null");
            return false;
        }else if (TextUtils.isEmpty(edtCp.getText())){
            edtCp.setError("kosong");
            return false;
        }
        return true;
    }

    private void checkJenisRutin() {
        if (rutin.isChecked()) {
            tglInputLayout.setVisibility(View.GONE);
            opt_rutin.setVisibility(View.VISIBLE);
        }
    }

    private void checkJenisTdkKajian() {
        if (tdkRutin.isChecked()) {
            opt_rutin.setVisibility(View.GONE);
            tglInputLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =  year + ":" + (monthOfYear + 1) + ":" + dayOfMonth;
        tglInput.setText(date);
        Toast.makeText(this, ""+date, Toast.LENGTH_SHORT).show();
    }

    private static final String[] ID_SETIAP_HARI = new String[]{
            "Pilih Hari :", "Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu", "Ahad"
    };


}
