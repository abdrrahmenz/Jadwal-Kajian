package id.or.qodr.jadwalkajianpekalongan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
//import okhttp3.Request;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import id.or.qodr.jadwalkajianpekalongan.adapter.AdapterKHari;
import id.or.qodr.jadwalkajianpekalongan.classes.DataKajian;
import id.or.qodr.jadwalkajianpekalongan.core.API;
import id.or.qodr.jadwalkajianpekalongan.core.Utils;
import id.or.qodr.jadwalkajianpekalongan.model.JadwalModel;
import id.or.qodr.jadwalkajianpekalongan.model.Location;
import id.or.qodr.jadwalkajianpekalongan.service.UserClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.bitmap;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static java.security.AccessController.getContext;

public class AdminInput extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {

    private static final String TAG = AdminInput.class.getSimpleName();
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private static final int PICK_IMAGE_REQUEST = 2;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private LinearLayout tglInputLayout, opt_rutin;
    private RadioGroup radioGrub;
    private RadioButton rutin, tdkRutin;
    private TextView _id, titleTolbar;
    private Spinner spinStiaphari, lokasiInput;
    private EditText edtCp, edtTema, edtPemateri;
    private Button btnPekan, tglInput, timeMulai, timeSampai, btnCancel, btnAddkajian;
    private API api; View view_opt_rutin;
    private FormBody formBody;
    private List<Location> locations;
    private ArrayAdapter<Location> locationArrayAdapter;
    private ArrayAdapter<String> dailySpinner;
    private String[] listPekan;
    private boolean[] checkedPekan;
    private ArrayList<Integer> mItemsPekan = new ArrayList<>();
    public String id,jenis_rutin,jenis_tdkrutin,setiap, pkan, tggl,mule,sampe,foto,tema,pemteri,lokasi,cp, lat, lng;
    public String tgl_req,type_req,mule_req,sampe_req,tema_req,pemteri_req,lokasi_req,cp_req, id_req,pekan_req,day_req;
    private GoogleApiClient mClient;
    private Button btnPlace;
    private TextView txtAdrress,txtLat,txtLng;
    private Bitmap bitmap;
    private ImageView image_masjid;
    // create Okhttp client for loggingInterceptor
//    private static OkHttpClient.Builder okhttpInterceptor = new OkHttpClient.Builder();

//    private static Retrofit.Builder builder = new Retrofit.Builder()
//            .baseUrl("https://adul.000webhostapp.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okhttpInterceptor.build());

//    public static Retrofit retrofit = builder.build();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_input);

        getWindow().getAttributes().windowAnimations = R.style.Fade;
        initView();

        api = new API();

        opt_rutin.setVisibility(View.GONE);
        view_opt_rutin.setVisibility(View.GONE);

//        tglInputLayout.setVisibility(View.GONE);

//        locations = new ArrayList<>();
//        locations.add(new Location("Pilih Lokasi",-7.030593, 109.609775, api.GET_IMG+"msjid.jpg"));
//        locations.add(new Location("Masjid Muadz bin Jabal Karanganyar",-7.030593, 109.609775, api.GET_IMG+"msjid.jpg"));
//        locations.add(new Location("Masjid Ibnu Abbas Wiradesa", -6.887971, 109.627553, api.GET_IMG+"ibnuabbas.jpg"));
//        locations.add(new Location("Masjid Imam As-Syafi'i Pekalongan", -6.892440, 109.677294, api.GET_IMG+"imamsyafii.jpg"));
//        locations.add(new Location("Masjid Al-Hidayah Sepait", -6.890784, 109.589407, api.GET_IMG+"alhidayah.jpg"));
//        locations.add(new Location("Masjid Al-Irsyad Pekalongan", -6.884119, 109.682104, api.GET_IMG+"alirsyad.jpg"));
//        locations.add(new Location("Masjid RS.Khodijah Pekalongan",-6.886206, 109.676884,api.GET_IMG+"rsu_khodijah.jpg"));
//        locations.add(new Location("Masjid Nidaus Salam Wiradesa", -6.874382, 109.667076, api.GET_IMG+"nidausalam.jpg"));
//        locations.add(new Location("Mushola As-Salam Comal", -6.890306, 109.660108, api.GET_IMG+"as_salam.jpeg"));
//        locations.add(new Location("Masjid Al-Hikmah Tosaran-Kedungwuni", -6.941677, 109.652681, api.GET_IMG+"alhikmah.jpg"));
//        locations.add(new Location("Masjid Al-Muttaqin Wiradesa", -6.892344, 109.620116,api.GET_IMG+"muttaqin.jpeg"));
//        locations.add(new Location("Mushola Abdul Ghoni Karanganyar", -7.021326, 109.647927,api.GET_IMG+"ghoni.jpg"));
//
//        locationArrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, locations);
//        locationArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        lokasiInput.setAdapter(locationArrayAdapter);

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
//                    editKajian(url);
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
//        setKajian();

        listPekan = new String[] {"1","2","3","4","5"};
        checkedPekan = new boolean[listPekan.length];


    }

    private void initView() {
        titleTolbar = (TextView) findViewById(R.id.toolbar_title);
        radioGrub = (RadioGroup) findViewById(R.id.radioGroup);
//        tglInputLayout = (LinearLayout) findViewById(R.id.tgl_input_layout);
//        lokasiInput = (Spinner) findViewById(R.id.lokasi_input);
        _id = (TextView) findViewById(R.id._id);
        opt_rutin = (LinearLayout) findViewById(R.id.opt_rutin);
        view_opt_rutin = (View) findViewById(R.id.view_opt_rutin);
        rutin = (RadioButton) findViewById(R.id.rutin);
        tdkRutin = (RadioButton) findViewById(R.id.tdkRutin);
        spinStiaphari = (Spinner) findViewById(R.id.spin_stiaphari);
        edtCp = (EditText) findViewById(R.id.edt_cp);
        edtTema = (EditText) findViewById(R.id.edt_tema);
        edtPemateri = (EditText) findViewById(R.id.edt_pemateri);
        btnPekan = (Button) findViewById(R.id.btn_pekan);
        tglInput = (Button) findViewById(R.id.tgl_input);
        timeMulai = (Button) findViewById(R.id.time_mulai);
        timeSampai = (Button) findViewById(R.id.time_sampai);
        btnPlace = (Button) findViewById(R.id.place);
        txtAdrress = (TextView) findViewById(R.id.place_address);
        txtLat= (TextView) findViewById(R.id.place_lat);
        txtLng= (TextView) findViewById(R.id.place_lng);
        btnPlace = (Button) findViewById(R.id.place);
        image_masjid = (ImageView) findViewById(R.id.image_masjid);
        btnAddkajian = (Button) findViewById(R.id.btn_addkajian);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
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
                                String time = hourOfDay + ":" + minute;
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

                                String time2 = hourOfDay + ":" + minute;

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

            case R.id.image_masjid:
                final CharSequence[] dialogitem = {"Galery", "Camera"};
                AlertDialog.Builder imgDialog = new AlertDialog.Builder(AdminInput.this);
                imgDialog.setCancelable(true);
                imgDialog.setItems(dialogitem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        switch (which) {
                            case 0:
                                showFileChooser();
                                break;
                            case 1:
                                captureImage();
                                break;
                        }
                    }
                }).show();
                break;

            case R.id.btn_cancel:
                Intent intent = new Intent(AdminInput.this,
                        AdminActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

            case R.id.btn_addkajian:

//                addKajian();
                HashMap<String, String> params = new HashMap<String, String>();
                String poster = getStringImage(bitmap);

                if (rutin.isChecked()){
                    if (validateInputRutin()){
                        params.put("jenis_kajian", rutin.getText().toString());
                        params.put("foto_masjid", poster);
                        params.put("setiap_hari", spinStiaphari.getSelectedItem().toString());
                        params.put("pekan", btnPekan.getText().toString());
                        params.put("tanggal", "");
                        params.put("mulai", timeMulai.getText().toString());
                        params.put("sampai", timeSampai.getText().toString());
                        params.put("tema", edtTema.getText().toString());
                        params.put("pemateri", edtPemateri.getText().toString());
                        params.put("lokasi", btnPlace.getText().toString());
                        params.put("alamat", txtAdrress.getText().toString());
                        params.put("lat", txtLat.getText().toString());
                        params.put("lng", txtLng.getText().toString());
                        params.put("cp", edtCp.getText().toString());
                        sendNewKajian(params);
//                        startActivity(new Intent(AdminInput.this, AdminActivity.class));
//                        finish();
                    }else {
                        Snackbar.make(btnAddkajian, "isi dengan benar !", Snackbar.LENGTH_SHORT).show();
                    }
                }else if (tdkRutin.isChecked()){
                    if (validateInputPekan()){
                        params.put("jenis_kajian", tdkRutin.getText().toString());
                        params.put("foto_masjid", poster);
                        params.put("setiap_hari", "");
                        params.put("pekan", "");
                        params.put("tanggal", tglInput.getText().toString());
                        params.put("mulai", timeMulai.getText().toString());
                        params.put("sampai", timeSampai.getText().toString());
                        params.put("tema", edtTema.getText().toString());
                        params.put("pemateri", edtPemateri.getText().toString());
                        params.put("lokasi", btnPlace.getText().toString());
                        params.put("alamat", txtAdrress.getText().toString());
                        params.put("lat", txtLat.getText().toString());
                        params.put("lng", txtLng.getText().toString());
                        params.put("cp", edtCp.getText().toString());
                        sendNewKajian(params);
//                        startActivity(new Intent(AdminInput.this, AdminActivity.class));
//                        finish();
                    }else{
                        Snackbar.make(btnAddkajian, "isi dengan benar !", Snackbar.LENGTH_SHORT).show();
                    }
                }else{
                    Snackbar.make(btnAddkajian, "Pilih Jenis Kajian dulu", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }

    }
    private void sendNewKajian(Map<String, String> params) {
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);

        OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();

        //LoggingInterceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpBuilder.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://adul.000webhostapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpBuilder.build());

        Retrofit retrofit = builder.build();
        UserClient user = retrofit.create(UserClient.class);

        retrofit2.Call<ResponseBody> call = user.submitNewKajian(
                params
        );
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                loading.dismiss();
                Toast.makeText(AdminInput.this, "sukses", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(AdminInput.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void addKajian() {
//        String image = getStringImage(bitmap);
////        final ProgressDialog progress = ProgressDialog.show(getApplicationContext(), "Loading","Please wait ...");
//        if (rutin.isChecked()){
//            if (validateInputRutin()){
//                formBody = new FormBody.Builder()
//                        .add("jenis_kajian", tdkRutin.getText().toString())
////                        params.put("foto_masjid", locations.get(position).getImg());
//                        .add("foto_masjid", image)
//                        .add("setiap_hari", spinStiaphari.getSelectedItem().toString())
//                        .add("pekan", btnPekan.getText().toString())
//                        .add("tanggal", tglInput.getText().toString())
//                        .add("mulai", timeMulai.getText().toString())
//                        .add("sampai", timeSampai.getText().toString())
////                        params.put("place", btnPlace.getText().toString());
//                        .add("tema", edtTema.getText().toString())
//                        .add("pemateri", edtPemateri.getText().toString())
////                        params.put("lokasi", locations.get(position).getName());
//                        .add("lokasi", btnPlace.getText().toString())
//                        .add("alamat", txtAdrress.getText().toString())
////                        params.put("lat", String.valueOf(locations.get(position).getLat()));
////                        params.put("lng", String.valueOf(locations.get(position).getLng()));
//                        .add("lat", txtLat.getText().toString())
//                        .add("lng", txtLng.getText().toString())
//                        .add("cp", edtCp.getText().toString()).build();
//                Request request = new Request.Builder().url(SplashScreen.BASE_URL+"/jadwal/insert").post(formBody).build();
//                SplashScreen.okHttpClient.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Log.i(TAG, "addKajian: "+"internet error");
////                        runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
//////                                progress.dismiss();
//////                                Snackbar.make(btnAddkajian, "Internet Error", Snackbar.LENGTH_SHORT).show();
////
////                            }
////                        });
//                    }
//
//                    @Override
//                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                        final String strResponse = response.body().string();
//                        try {
//                            JSONObject jsonResponse = new JSONObject(strResponse);
//                            if (jsonResponse.getBoolean("result")){
////                                        Snackbar.make(btnAddkajian, "Success",Snackbar.LENGTH_SHORT).show();
//                                Log.i(TAG, "addKajian: "+"Sukses");
//                            }else {
////                                        Snackbar.make(btnAddkajian, jsonResponse.getString("message"),Snackbar.LENGTH_SHORT).show();
//                                Log.i(TAG, "addKajian: "+"error");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
////                        runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
//////                                progress.dismiss();
////
////                            }
////                        });
//                    }
//                });
//
//            }else {
////                Snackbar.make(btnAddkajian, "isi dengan benar dong!", Snackbar.LENGTH_SHORT).show();
//                Log.i(TAG, "addKajian: "+"isi yg benar");
//            }
//        }else if (tdkRutin.isChecked()) {
//            if (validateInputPekan()) {
//                formBody = new FormBody.Builder()
//                        .add("jenis_kajian", tdkRutin.getText().toString())
////                        params.put("foto_masjid", locations.get(position).getImg());
//                        .add("foto_masjid", image)
//                        .add("setiap_hari", "")
//                        .add("pekan", "")
//                        .add("tanggal", tglInput.getText().toString())
//                        .add("mulai", timeMulai.getText().toString())
//                        .add("sampai", timeSampai.getText().toString())
////                        params.put("place", btnPlace.getText().toString());
//                        .add("tema", edtTema.getText().toString())
//                        .add("pemateri", edtPemateri.getText().toString())
////                        params.put("lokasi", locations.get(position).getName());
//                        .add("lokasi", btnPlace.getText().toString())
//                        .add("alamat", txtAdrress.getText().toString())
////                        params.put("lat", String.valueOf(locations.get(position).getLat()));
////                        params.put("lng", String.valueOf(locations.get(position).getLng()));
//                        .add("lat", txtLat.getText().toString())
//                        .add("lng", txtLng.getText().toString())
//                        .add("cp", edtCp.getText().toString()).build();
//                Request request = new Request.Builder().url(SplashScreen.BASE_URL+"/jadwal/insert").post(formBody).build();
//                SplashScreen.okHttpClient.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
////                        runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
//////                                progress.dismiss();
//////                                Snackbar.make(btnAddkajian, "Internet Error", Snackbar.LENGTH_SHORT).show();
////
////                            }
////                        });
//                        Log.i(TAG, "run: "+"Internet Error");
//                    }
//
//                    @Override
//                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                        final String strResponse = response.body().string();
//                        try {
//                            JSONObject jsonResponse = new JSONObject(strResponse);
//                            if (jsonResponse.getBoolean("result")){
////                                        Snackbar.make(btnAddkajian, "Success",Snackbar.LENGTH_SHORT).show();
//                                Log.i(TAG, "run: "+"Sukses");
//                            }else {
////                                        Snackbar.make(btnAddkajian, jsonResponse.getString("message"),Snackbar.LENGTH_SHORT).show();
//                                Log.i(TAG, "run: "+"Error");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
////                        runOnUiThread(new Runnable() {
////                            @Override
////                            public void run() {
//////                                progress.dismiss();
////
////                            }
////                        });
//                    }
//                });
//            }else{
////                Snackbar.make(btnAddkajian, "isi dengan benar dong!", Snackbar.LENGTH_SHORT).show();
//                Log.i(TAG, "addKajian: "+"isi yg benar");
//            }
//        }else{
////            Snackbar.make(btnAddkajian, "Pilih Jenis Kajian dulu", Snackbar.LENGTH_SHORT).show();
//            Log.i(TAG, "addKajian: "+"pilih kajian");
//        }
//    }

//    private void submitKajian(final HashMap<String, String> parameter){
//        String url = api.POST_JADWAL;
//        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
//        StringRequest post = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    //Disimissing the progress dialog
//                    loading.dismiss();
//                    Toast.makeText(AdminInput.this, "Sucess", Toast.LENGTH_SHORT).show();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                Log.i("TAG", "onResponse: "+response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //Dismissing the progress dialog
//                loading.dismiss();
//                Toast.makeText(AdminInput.this, "Gagal !", Toast.LENGTH_SHORT).show();
//                Log.d("error", error.toString());
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = parameter;
//
//                return map;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(post);
//    }

//    private void editKajian(String url){
//        StringRequest post = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//
//                    Toast.makeText(getApplicationContext(), "edit Sukses", Toast.LENGTH_SHORT).show();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Gagal Edit, Coba Lagi!", Toast.LENGTH_SHORT).show();
//                Log.d("error", error.toString());
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                if (rutin.isChecked()){
//                    map.put("id", id);
//                    map.put("jenis_kajian", jenis_rutin);
//                    map.put("foto_masjid", foto);
//                    map.put("setiap_hari", setiap);
//                    map.put("pekan", pkan);
//                    map.put("tanggal", "");
//                    map.put("mulai", mule);
//                    map.put("sampai", sampe);
//                    map.put("tema", tema);
//                    map.put("pemateri", pemteri);
//                    map.put("lokasi", lokasi);
//                    map.put("lat", lat);
//                    map.put("lng", lng);
//                    map.put("cp", cp);
//                }else if (tdkRutin.isChecked()){
//                    map.put("id", id);
//                    map.put("jenis_kajian", jenis_tdkrutin);
//                    map.put("foto_masjid", foto);
//                    map.put("setiap_hari", "");
//                    map.put("pekan", "");
//                    map.put("tanggal", tggl);
//                    map.put("mulai", mule);
//                    map.put("sampai", sampe);
//                    map.put("tema", tema);
//                    map.put("pemateri", pemteri);
//                    map.put("lokasi", lokasi);
//                    map.put("lat", lat);
//                    map.put("lng", lng);
//                    map.put("cp", cp);
//                }else{
//                    Toast.makeText(AdminInput.this, "Not Selected", Toast.LENGTH_SHORT).show();
//                }
//                return map;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(post);
//    }

    private boolean validateInputRutin() {
        if (spinStiaphari.getSelectedItemPosition()==0){
            TextView erorText = (TextView)spinStiaphari.getSelectedView();
            erorText.setError("null");
            return false;
        }else if (btnPekan.getText().toString().equals("Pilih Pekan")){
            btnPekan.setError("error");
            return false;
        }else if (timeMulai.getText().toString().equals("From")){
            timeMulai.setError("error");
            return false;
        }else if (timeSampai.getText().toString().equals("To")){
            timeSampai.setError("null");
            return false;
        }else if (TextUtils.isEmpty(edtPemateri.getText())){
            edtPemateri.setError("kosong");
            return false;
        }else if (TextUtils.isEmpty(edtTema.getText())){
            edtTema.setError("kosong");
            return false;
        }else if (btnPlace.getText().toString().equals("Place")){
            btnPlace.setError("kosong");
            return false;
        }else if (bitmap == null){
            image_masjid.setBackgroundColor(getResources().getColor(R.color.alert));
            Toast.makeText(this, "Tambahkan gambar", Toast.LENGTH_SHORT).show();
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
        if (tglInput.getText().toString().equals("Date")){
            tglInput.setError("error");
            return false;
        }else if (timeMulai.getText().toString().equals("From")){
            timeMulai.setError("error");
            return false;
        }else if (timeSampai.getText().toString().equals("To")){
            timeSampai.setError("null");
            return false;
        }else if (btnPlace.getText().toString().equals("Place")){
            btnPlace.setError("kosong");
            return false;
        }else if (TextUtils.isEmpty(edtPemateri.getText())){
            edtPemateri.setError("kosong");
            return false;
        }else if (TextUtils.isEmpty(edtTema.getText())){
            edtTema.setError("kosong");
            return false;
        }else if (bitmap == null){
            image_masjid.setBackgroundColor(getResources().getColor(R.color.alert));
            Toast.makeText(this, "Tambahkan gambar", Toast.LENGTH_SHORT).show();
            return false;
        }else if (TextUtils.isEmpty(edtCp.getText())){
            edtCp.setError("kosong");
            return false;
        }
        return true;
    }

    private void checkJenisRutin() {
        if (rutin.isChecked()) {
            tglInput.setEnabled(false);
            view_opt_rutin.setVisibility(View.VISIBLE);
            opt_rutin.setVisibility(View.VISIBLE);
        }
    }

    private void checkJenisTdkKajian() {
        if (tdkRutin.isChecked()) {
            opt_rutin.setVisibility(View.GONE);
            tglInput.setEnabled(true);
            view_opt_rutin.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        String date =  year + ":" + (monthOfYear + 1) + ":" + dayOfMonth;
        String date =  (monthOfYear + 1) + ":" + dayOfMonth;
        tglInput.setText(date);
        Toast.makeText(this, ""+date, Toast.LENGTH_SHORT).show();
    }

    private static final String[] ID_SETIAP_HARI = new String[]{
            "Pilih Hari :", "Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu", "Ahad"
    };

    public void onAddPlaceButtonClicked(View view) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AdminInput.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_FINE_LOCATION);

            Toast.makeText(this, getString(R.string.need_location_permission_message), Toast.LENGTH_LONG).show();

            return;
        }
        try {
            // Start a new Activity for the Place Picker API, this will trigger {@code #onActivityResult}
            // when a place is selected or with the user cancels.
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent i = builder.build(this);
            startActivityForResult(i, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
        } catch (Exception e) {
            Log.e(TAG, String.format("PlacePicker Exception: %s", e.getMessage()));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(this, data);
            if (place == null) {
                Log.i(TAG, "No place selected");
                return;
            }

            // Extract the place information from the API
            String placeName = place.getName().toString();
            String placeAddress = place.getAddress().toString();
            String placeLatLng = place.getLatLng().toString();
            String removeStr1 = placeLatLng.replace("lat/lng: (","");
            String removeStr2 = removeStr1.replace(")","");
            String splitWithComa = ",";
            String strLatLng[] = removeStr2.split(splitWithComa);

            btnPlace.setText(placeName);
            txtAdrress.setText(placeAddress);
            txtLat.setText(strLatLng[0]);
            txtLng.setText(strLatLng[1]);

        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                image_masjid.setVisibility(View.VISIBLE);
                //Setting the Bitmap to ImageView
                Glide.with(this).load(filePath).into(image_masjid);
                image_masjid.setBackgroundColor(getResources().getColor(R.color.white));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri fileuri = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileuri);
                image_masjid.setVisibility(View.VISIBLE);
                //Setting the Bitmap to ImageView
                Glide.with(this).load(fileuri).into(image_masjid);
                image_masjid.setBackgroundColor(getResources().getColor(R.color.white));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Launching camera app to capture image
     */
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    public String getStringImage(Bitmap bmp){
        if (bitmap == null) {
            return null;
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 75, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

}
