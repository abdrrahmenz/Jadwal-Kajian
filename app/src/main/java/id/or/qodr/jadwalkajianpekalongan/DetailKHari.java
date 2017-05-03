package id.or.qodr.jadwalkajianpekalongan;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class DetailKHari extends AppCompatActivity implements OnMapReadyCallback {

    private TextView tglDetail,type_kajian,dayDetail, pekanDetail,mulaiDetail, lokasiDetail, temaDetail, pemateriDetail, cpDetail, sampaiDetail;
    private ImageView imgLocDetail;
    private GoogleMap mMap;
    private double lat_req, lng_req;
    private String tgl2_req,tgl_req,type_req,hari_req,pekan_req,mule_req,sampe_req,img_req,tema_req,pemteri_req,lokasi_req,cp_req;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_khari);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        if (toolbar!=null){
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initView();

        CollapsingToolbarLayout collaps = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collaps.setTitle("Detail Kajian Hari");

        Context context = this;
        collaps.setContentScrimColor(ContextCompat.getColor(context, R.color.white));
        collaps.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collaps.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
//        collaps.setCollapsedTitleGravity(Gravity.CENTER);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type_req = extras.getString("type_key");
            hari_req = extras.getString("day_key");
            pekan_req = extras.getString("pekan_key");
            tgl_req = extras.getString("tgl_key");
            tgl2_req = extras.getString("tgl2_key");
            lat_req = extras.getDouble("lat_key");
            lng_req = extras.getDouble("lng_key");
            mule_req = extras.getString("mule_key");
            sampe_req = extras.getString("sampe_key");
            img_req = extras.getString("img_key");
            tema_req = extras.getString("tema_key");
            pemteri_req = extras.getString("pemteri_key");
            lokasi_req = extras.getString("lokasi_key");
            cp_req = extras.getString("cp_key");
        }

        setTextDetailKajian();
        Glide.with(context).load(img_req).into(imgLocDetail);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initView() {
        type_kajian = (TextView) findViewById(R.id.txt_ket);
        dayDetail = (TextView) findViewById(R.id.day_det);
        pekanDetail = (TextView) findViewById(R.id.pkn_det);
        mulaiDetail = (TextView) findViewById(R.id.mulaiDetail);
        sampaiDetail = (TextView) findViewById(R.id.sampaiDetail);
        lokasiDetail = (TextView) findViewById(R.id.lokasiDetail);
        tglDetail = (TextView) findViewById(R.id.tglDetail);
        temaDetail = (TextView) findViewById(R.id.temaDetail);
        pemateriDetail = (TextView) findViewById(R.id.pemateriDetail);
        cpDetail = (TextView) findViewById(R.id.cpDetail);
        imgLocDetail = (ImageView) findViewById(R.id.bgheader);
    }

    public void setTextDetailKajian(){
        String[] mule = mule_req.split(":");
        String[] smpe = sampe_req.split(":");
        if (tgl2_req.isEmpty() && hari_req.isEmpty() && pekan_req.isEmpty()){
            dayDetail.setVisibility(View.GONE);
            pekanDetail.setVisibility(View.GONE);
            type_kajian.setText(type_req+" Khusus Akhwat");
            tglDetail.setText(Html.fromHtml("<b>"+"Hari ini"+"<b>"));
            mulaiDetail.setText("Pukul "+mule[0]+":"+mule[1]);
            sampaiDetail.setText(smpe[0]+":"+smpe[1]+ " WIB");
            temaDetail.setText(Html.fromHtml("Judul : "+"<b>"+tema_req+"<b>"));
            pemateriDetail.setText(Html.fromHtml("Pemateri : "+"<b>"+pemteri_req+"<b>"));
            lokasiDetail.setText("Tempat : "+lokasi_req);
            cpDetail.setText("Cp : "+cp_req);
        }else if (!tgl2_req.isEmpty() && !hari_req.isEmpty() && !pekan_req.isEmpty()){
            tglDetail.setText(Html.fromHtml("<b>"+"Hari ini"+"<b>"));
            type_kajian.setText(type_req+" Terbuka untuk umum");
            dayDetail.setText("Setiap Hari : "+hari_req);
            pekanDetail.setText("Pekan ke : "+pekan_req);
            mulaiDetail.setText("Pukul "+mule[0]+":"+mule[1]);
            sampaiDetail.setText(smpe[0]+":"+smpe[1]+ " WIB");
            temaDetail.setText(Html.fromHtml("Judul : "+"<b>"+tema_req+"<b>"));
            pemateriDetail.setText(Html.fromHtml("Pemateri : "+"<b>"+pemteri_req+"<b>"));
            lokasiDetail.setText("Tempat : "+lokasi_req);
            cpDetail.setText("Cp : "+cp_req);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng masjid = new LatLng(lat_req, lng_req);
        mMap.addMarker(new MarkerOptions().position(masjid).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).title("Lokasi Masjid").snippet("Tempat Kajian"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(masjid, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1500, null);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(masjid));
//        mMap.setMyLocationEnabled(true);
    }
}
