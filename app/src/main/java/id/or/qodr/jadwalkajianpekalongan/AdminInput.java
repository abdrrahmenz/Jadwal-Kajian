package id.or.qodr.jadwalkajianpekalongan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.or.qodr.jadwalkajianpekalongan.core.Utils;

public class AdminInput extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.rutin)
    RadioButton rutin;
    @BindView(R.id.tdkRutin)
    RadioButton tdkRutin;
    @BindView(R.id.spin_stiaphari)
    Spinner spinStiaphari;
    @BindView(R.id.pekanSatu)
    AppCompatCheckBox pekanSatu;
    @BindView(R.id.pekanDua)
    AppCompatCheckBox pekanDua;
    @BindView(R.id.pekanTiga)
    AppCompatCheckBox pekanTiga;
    @BindView(R.id.pekanEmpat)
    AppCompatCheckBox pekanEmpat;
    @BindView(R.id.pekanLima)
    AppCompatCheckBox pekanLima;
    @BindView(R.id.pekanAll)
    AppCompatCheckBox pekanAll;
    @BindView(R.id.tgl_input)
    Button tglInput;
    @BindView(R.id.time_input)
    Button timeInput;
    @BindView(R.id.edt_pemateri)
    EditText edtPemateri;
    @BindView(R.id.edt_tema)
    EditText edtTema;
    @BindView(R.id.lokasi_input)
    Button lokasiInput;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_addkajian)
    Button btnAddkajian;
    @BindView(R.id.tgl_input_layout)
    LinearLayout tglInputLayout;
    @BindView(R.id.opt_rutin)
    LinearLayout opt_rutin;
    private Utils utils;
    private String KEY_LOKASI = "LOKASI";
    String kordinat = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_input);
        ButterKnife.bind(this);

        utils = new Utils(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            kordinat = extras.getString(KEY_LOKASI);
        }
        lokasiInput.setText(kordinat);

        opt_rutin.setVisibility(View.GONE);
        tglInputLayout.setVisibility(View.GONE);

        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ID_SETIAP_HARI);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinStiaphari.setAdapter(categoryAdapter);
        spinStiaphari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch ((int) categoryAdapter.getItemId(position)) {
                    case 0:
                        Toast.makeText(AdminInput.this, "pilih hari dengan benar", Toast.LENGTH_SHORT).show();
                    break;
                    case 1:
                        Toast.makeText(AdminInput.this, "pilih hari senin", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(AdminInput.this, "pilih hari selasa", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(AdminInput.this, "pilih hari 3", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(AdminInput.this, "pilih hari 4", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(AdminInput.this, "pilih hari 5", Toast.LENGTH_SHORT).show();
                        break;
                }
//                Toast.makeText(getApplicationContext(), ""+categoryAdapter.getItemId(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @OnClick({R.id.pekanSatu, R.id.pekanDua, R.id.pekanTiga, R.id.pekanEmpat, R.id.pekanLima, R.id.pekanAll})
    public void onCheckBox(View view) {
        switch (view.getId()) {
            case R.id.pekanSatu:
                if (pekanSatu.isClickable()){
                    pekanAll.setChecked(false);
//                    Calendar cal = Calendar.getInstance();
//                    cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
//
//                    Date lastDayOfMonth = cal.getTime();
                    String date="dd/MM/yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    try {
                        c.setTime(sdf.parse(date));
                    } catch (ParseException ex) {
//                        Logger.getLogger(DateIterator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                    for (int co = 0; co < maxDay; co++) {
                        System.out.println(sdf.format(c.getTime()));
                        c.add(Calendar.DATE, 7);
                    }
                    Toast.makeText(this, ""+maxDay, Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.pekanDua:
                if (pekanDua.isClickable()){
                    pekanAll.setChecked(false);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Calendar cal = Calendar.getInstance();
                    System.out.println("time => " + dateFormat.format(cal.getTime()));

                    String time_str = dateFormat.format(cal.getTime());
                    Date date = null;
                    cal.add(Calendar.DATE, 7);
                    Date end = cal.getTime();
                    System.out.println("end Date > "+end);

                    String[] s = time_str.split(" ");

                    for (int i = 0; i < s.length; i++) {
                        System.out.println("date  => " + s[i]);
                    }

                    int year_sys = Integer.parseInt(s[0].split("/")[0]);
                    int month_sys = Integer.parseInt(s[0].split("/")[1]);
                    int day_sys = Integer.parseInt(s[0].split("/")[2]);

                    int hour_sys = Integer.parseInt(s[1].split(":")[0]);
                    int min_sys = Integer.parseInt(s[1].split(":")[1]);

                    System.out.println("year_sys  => " + year_sys);
                    System.out.println("month_sys  => " + month_sys);
                    System.out.println("day_sys  => " + day_sys);

                    System.out.println("hour_sys  => " + hour_sys);
                    System.out.println("min_sys  => " + min_sys);
                    Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.pekanTiga:
                if (pekanTiga.isClickable()){
                    pekanAll.setChecked(false);
                    DateFormat df = new SimpleDateFormat("dd/mm/yyyy");
                    Date dt = new Date();
                    try {
                        dt = df.parse("31/01/2017");
                    } catch (ParseException e) {
                        System.out.println("Error");
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dt);
                    cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, cal.getFirstDayOfWeek());
                    Date startDate = cal.getTime();
                    cal.add(Calendar.DATE, 7);
                    Date endDate = cal.getTime();
//                    Toast.makeText(this, ""+startDate+" lnjute "+endDate, Toast.LENGTH_LONG).show();
                    Toast.makeText(this, ""+df.format(dt), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.pekanEmpat:
                if (pekanEmpat.isClickable()){
                    pekanAll.setChecked(false);
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.FEBRUARY);
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                    String outputDate = simpleDateFormat.format(calendar.getTime());
//                    Toast.makeText(this, ""+outputDate, Toast.LENGTH_SHORT).show();
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
                    cal.clear(Calendar.MINUTE);
                    cal.clear(Calendar.SECOND);
                    cal.clear(Calendar.MILLISECOND);

// get start of the month
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    System.out.println("Start of the month:       " + cal.getTime());
                    System.out.println("... in milliseconds:      " + cal.getTimeInMillis());

// get start of the next month
                    cal.add(Calendar.MONTH, 1);
                    System.out.println("Start of the next month:  " + cal.getTime());
                }
                break;
            case R.id.pekanLima:
                if (pekanLima.isClickable()){
                    pekanAll.setChecked(false);
                    Calendar now = Calendar.getInstance();

                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

                    String[] days = new String[7];
                    int delta = now.get(GregorianCalendar.WEEK_OF_MONTH); //add 2 if your week start on monday
                    now.add(Calendar.DAY_OF_MONTH, delta );
                    for (int i = 0; i < now.getActualMaximum(Calendar.DATE); i++)
                    {
                        days[i] = format.format(now.getTime());
                        now.add(Calendar.DAY_OF_MONTH, 7);
                    }
                    System.out.println(Arrays.toString(days));

                }
                break;
            case R.id.pekanAll:
                if (pekanAll.isChecked()) {
                    pekanSatu.setChecked(true);
                    pekanDua.setChecked(true);
                    pekanTiga.setChecked(true);
                    pekanEmpat.setChecked(true);
                    pekanLima.setChecked(true);
                }else {
                    pekanSatu.setChecked(false);
                    pekanDua.setChecked(false);
                    pekanTiga.setChecked(false);
                    pekanEmpat.setChecked(false);
                    pekanLima.setChecked(false);
                }
                break;
        }
    }


    @OnClick({R.id.rutin, R.id.tdkRutin, R.id.tgl_input, R.id.time_input, R.id.lokasi_input, R.id.btn_cancel, R.id.btn_addkajian})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rutin:
                Toast.makeText(this, "rutin", Toast.LENGTH_SHORT).show();
                if (rutin.isClickable())
                    rutin.setBackgroundResource(R.drawable.clickable_style);
                rutin.setTextColor(getResources().getColor(R.color.white));
                tdkRutin.setBackgroundResource(R.drawable.rounded_style);
                tdkRutin.setTextColor(getResources().getColor(R.color.mdtp_numbers_text_color));
                checkJenisRutin();
                break;
            case R.id.tdkRutin:
                Toast.makeText(this, "tdk rutin", Toast.LENGTH_SHORT).show();
                if (tdkRutin.isClickable())
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
            case R.id.time_input:
                Toast.makeText(this, "time", Toast.LENGTH_SHORT).show();
                Calendar nowTime = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AdminInput.this,
                        nowTime.get(Calendar.HOUR_OF_DAY),
                        nowTime.get(Calendar.MINUTE),
                        true
                );
                tpd.setVersion(TimePickerDialog.Version.VERSION_2);
                tpd.show(getFragmentManager(), "Datepickerdialog");
                break;
            case R.id.lokasi_input:
                Toast.makeText(this, "lokasi", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminInput.this, MapsActivity.class));
                finish();

                break;
            case R.id.btn_cancel:
                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminInput.this, AdminActivity.class));
                break;
            case R.id.btn_addkajian:
                DateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("hari", spinStiaphari.getSelectedItem().toString());
                params.put("pekan", pekanSatu.getText().toString());
                params.put("waktu", timeInput.getText().toString());
                params.put("pemateri", edtPemateri.getText().toString());
                params.put("tema", edtTema.getText().toString());
                params.put("lokasi", lokasiInput.getText().toString());
                params.put("created_at", currentDate.format(date));
                params.put("updated_at", currentDate.format(date));
                Toast.makeText(this, ""+params, Toast.LENGTH_LONG).show();
                break;
        }
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

        String date = dayOfMonth + " " + utils.selectMonth((monthOfYear + 1)) + " " + year;
        tglInput.setText(date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = "Pukul " + hourOfDay + "." + minute + " WIB";
        timeInput.setText(time);
    }

    private static final String[] ID_SETIAP_HARI = new String[]{
            "Pilih Hari", "Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu", "Ahad"
    };


}
