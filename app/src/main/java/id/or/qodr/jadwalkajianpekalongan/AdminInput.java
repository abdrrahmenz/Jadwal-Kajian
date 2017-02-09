package id.or.qodr.jadwalkajianpekalongan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.or.qodr.jadwalkajianpekalongan.classes.DataKajian;
import id.or.qodr.jadwalkajianpekalongan.core.API;
import id.or.qodr.jadwalkajianpekalongan.core.Utils;
import id.or.qodr.jadwalkajianpekalongan.model.Location;

public class AdminInput extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.rutin)
    RadioButton rutin;
    @BindView(R.id.tdkRutin)
    RadioButton tdkRutin;
    @BindView(R.id.spin_stiaphari)
    Spinner spinStiaphari;
    @BindView(R.id.openDialog)
    LinearLayout checkboxLayout;
    @BindView(R.id.text)
    TextView pekan;
    @BindView(R.id.tgl_input)
    Button tglInput;
    @BindView(R.id.time_mulai)
    Button timeMulai;
    @BindView(R.id.time_sampai)
    Button timeSampai;
    @BindView(R.id.edt_pemateri)
    EditText edtPemateri;
    @BindView(R.id.edt_tema)
    EditText edtTema;
    @BindView(R.id.edt_cp)
    EditText edtCp;
    @BindView(R.id.lokasi_input)
    Spinner lokasiInput;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_addkajian)
    Button btnAddkajian;
    @BindView(R.id.tgl_input_layout)
    LinearLayout tglInputLayout;
    @BindView(R.id.opt_rutin)
    LinearLayout opt_rutin;
    private API api;
    private Utils utils;
    private String KEY_LOKASI = "LOKASI";
    String kordinat = null;
    private List<Location> locations;
    private ArrayAdapter<Location> locationArrayAdapter;
    StringBuilder stringBuilder = new StringBuilder();
    List<CharSequence> list = new ArrayList<CharSequence>();
    private DataKajian dataKajian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_input);
        ButterKnife.bind(this);

        utils = new Utils(this);
        api = new API();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            kordinat = extras.getString(KEY_LOKASI);
        }
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


        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,ID_SETIAP_HARI)
        {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                return v;
            }
        };
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStiaphari.setAdapter(categoryAdapter);

        String[] idPekan = {"1","2","3","4","5","Semua"};
//        for (int i = 1; i < 6; i++) {
            list.add(""+idPekan[0]);  // Add the item in the list
            list.add(""+idPekan[1]);
            list.add(""+idPekan[2]);
            list.add(""+idPekan[3]);
            list.add(""+idPekan[4]);
//            list.add(""+idPekan[5]);
//        }
        View openDialog = (View) findViewById(R.id.openDialog);
        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intialize  readable sequence of char values
                final CharSequence[] dialogList = list.toArray(new CharSequence[list.size()]);
                final AlertDialog.Builder builderDialog = new AlertDialog.Builder(AdminInput.this);
                builderDialog.setTitle("Pilih pekan");
                int count = dialogList.length;
                boolean[] is_checked = new boolean[count];

                // Creating multiple selection by using setMutliChoiceItem method
                builderDialog.setMultiChoiceItems(dialogList, is_checked,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton, boolean isChecked) {

                            }
                        });
                builderDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListView list = ((AlertDialog) dialog).getListView();
                        // make selected item in the comma seprated string
//                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < list.getCount(); i++) {

                            boolean checked = list.isItemChecked(i);

                            if (checked ) {
                                if (stringBuilder.length() > 0)
                                stringBuilder.append(",");
                                stringBuilder.append(list.getItemAtPosition(i));

                                Toast.makeText(AdminInput.this, ""+list.getItemAtPosition(i), Toast.LENGTH_SHORT).show();

                            }


                        }
                        Toast.makeText(AdminInput.this, ""+stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                        /*Check string builder is empty or not. If string builder is not empty.
                          It will display on the screen.
                         */
                        if (stringBuilder.toString().trim().equals("")) {

                            ((TextView) findViewById(R.id.text)).setText("Pilih Pekan");
                            stringBuilder.setLength(0);

                        } else {

                            ((TextView) findViewById(R.id.text)).setText(stringBuilder);
                        }
                    }
                });
                builderDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((TextView) findViewById(R.id.text)).setText("Pilih Pekan");
                            }
                        });
                AlertDialog alert = builderDialog.create();
                alert.show();

            }
        });


    }

    private void imgMasjid() {

    }

    public int getDayID(int date){
        int week=0;
        if(date<=7){
            week=1;
        }else if(date>=8 && date<=14){
            week=8;
        }else if(date>=15 && date<=21){
            week=15;
        }else if(date>=22 && date<=28){
            week=22;
        }else if(date>=29){
            week=29;
        }
        return week;
    }
/*
* input data
* hari : Senin
* pekan ke 1
* */
    public String checkPekan(int date, int a){
        int year = 2017;
        int month = 2;
        int dayOfM = a;
        Calendar myCalendar = new GregorianCalendar(year, month, dayOfM);
        String week=null;
        if(date<=7){

                final int dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK);
                if(spinStiaphari.getSelectedItem().toString().equals(utils.showDayByID(dayOfWeek))){
                    week=spinStiaphari.getSelectedItem().toString()+" = "+utils.showDayByID(dayOfWeek);
                }else{
                    week=String.valueOf(a);
                }
        }else if(date>=8 && date<=14){

            final int dayOfWeek = myCalendar.get(Calendar.DAY_OF_WEEK);
            if(spinStiaphari.getSelectedItem().toString().equals(utils.showDayByID(dayOfWeek))){
                week=spinStiaphari.getSelectedItem().toString()+" = "+utils.showDayByID(dayOfWeek);
            }

        }else if(date>=15 && date<=21){
            week="minggu 3 ";
        }else if(date>=22 && date<=28){
            week="minggu 4 ";
        }else if(date>=29){
            week="minggu 5 ";
        }
        return week;
    }
    @OnClick({R.id.rutin, R.id.tdkRutin, R.id.tgl_input, R.id.time_mulai, R.id.time_sampai, R.id.btn_cancel, R.id.btn_addkajian})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rutin:
                Toast.makeText(this, "rutin", Toast.LENGTH_SHORT).show();
                if (rutin.isChecked())
                    rutin.setBackgroundResource(R.drawable.clickable_style);
                rutin.setTextColor(getResources().getColor(R.color.white));
                tdkRutin.setBackgroundResource(R.drawable.rounded_style);
                tdkRutin.setTextColor(getResources().getColor(R.color.mdtp_numbers_text_color));
                checkJenisRutin();

                break;
            case R.id.tdkRutin:
                Toast.makeText(this, "tdk rutin", Toast.LENGTH_SHORT).show();
                if (tdkRutin.isChecked())
                    tdkRutin.setBackgroundResource(R.drawable.clickable_style);
                tdkRutin.setTextColor(getResources().getColor(R.color.white));
                rutin.setBackgroundResource(R.drawable.rounded_style);
                rutin.setTextColor(getResources().getColor(R.color.mdtp_numbers_text_color));
                checkJenisTdkKajian();
                break;
            case R.id.tgl_input:
                Toast.makeText(this, "date bro", Toast.LENGTH_SHORT).show();
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        AdminInput.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.time_mulai:
                Toast.makeText(this, "time_mulai", Toast.LENGTH_SHORT).show();
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
                tpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.time_sampai:
                Toast.makeText(this, "time_sampai", Toast.LENGTH_SHORT).show();
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
                ttpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.btn_cancel:
                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminInput.this, AdminActivity.class));

                break;
            case R.id.btn_addkajian:
                String url = api.POST_JADWAL; RecyclerView recylerVw = null;
                HashMap<String, String> params = new HashMap<String, String>();
                int position = lokasiInput.getSelectedItemPosition();


                if (rutin.isChecked()){
                    params.put("foto_masjid", locations.get(position).getImg());
                    params.put("setiap_hari", spinStiaphari.getSelectedItem().toString());
                    params.put("pekan", stringBuilder.toString());
                    params.put("tanggal", "");
                    params.put("mulai", timeMulai.getText().toString());
                    params.put("sampai", timeSampai.getText().toString());
                    params.put("tema", edtTema.getText().toString());
                    params.put("pemateri", edtPemateri.getText().toString());
                    params.put("lokasi", lokasiInput.getSelectedItem().toString());
                    params.put("lat", String.valueOf(locations.get(position).getLat()));
                    params.put("lng", String.valueOf(locations.get(position).getLng()));
                    params.put("cp", edtCp.getText().toString());
                    submitKajian(url,params,recylerVw);
                    startActivity(new Intent(this, AdminActivity.class));
                    Toast.makeText(this, "AZ"+params, Toast.LENGTH_LONG).show();
                }else if (tdkRutin.isChecked()){
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
                    submitKajian(url,params,recylerVw);
                    startActivity(new Intent(this, AdminActivity.class));
                    Toast.makeText(this, "BZ"+params, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "Fail.....", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    private void submitKajian(String url, final HashMap<String, String> params, final RecyclerView recyclerVw){
        StringRequest post = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (rutin.isChecked()) {
                        Toast.makeText(AdminInput.this, "Rutin Menginput", Toast.LENGTH_SHORT).show();
                        dataKajian.getJadwalKHari(recyclerVw, api.POST_JADWAL);
                    }else if (tdkRutin.isChecked()){
                        dataKajian.getJadwalKPekan(recyclerVw, api.POST_JADWAL);
                        Toast.makeText(AdminInput.this, "Tidak Rutin Menginput", Toast.LENGTH_SHORT).show();
                    }
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

    private void generateLocationDummy() {
        int[] masjid = new int[]{
                R.drawable.msjid,
                R.drawable.ibnuabbas,
                R.drawable.imamsyafii,
                R.drawable.alhidayah,
                R.drawable.rsu_khodijah,
                R.drawable.nidausalam,
                R.drawable.as_salam,
                R.drawable.alhikmah,
                R.drawable.muttaqin,
                R.drawable.ghoni,
        };

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String date =  year + ":" + (monthOfYear + 1) + ":" + dayOfMonth;
        tglInput.setText(date);
    }


    private static final String[] ID_SETIAP_HARI = new String[]{
            "Pilih Hari :", "Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu", "Ahad"
    };


}
