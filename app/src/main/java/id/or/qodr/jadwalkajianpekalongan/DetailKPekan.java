package id.or.qodr.jadwalkajianpekalongan;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import id.or.qodr.jadwalkajianpekalongan.core.Utils;

public class DetailKPekan extends AppCompatActivity implements OnMapReadyCallback {

    @BindView(R.id.bgheader)
    ImageView imgLocDetail;
    @BindView(R.id.tglDetail)
    TextView tglDetail;
    @BindView(R.id.mulaiDetail)
    TextView mulaiDetail;
    @BindView(R.id.sampaiDetail)
    TextView sampaiDetail;
    @BindView(R.id.cpDetail)
    TextView cpDetail;
    @BindView(R.id.pemateriDetail)
    TextView pemateriDetail;
    @BindView(R.id.temaDetail)
    TextView temaDetail;
    @BindView(R.id.lokasiDetail)
    TextView lokasiDetail;
    private GoogleMap mMap;
    private Utils utils;
    private String lat_req, lng_req,mule_req,sampe_req,tgl_req,img_req,tema_req,pemteri_req,lokasi_req,cp_req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kpekan);
        ButterKnife.bind(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        utils = new Utils(this);

        CollapsingToolbarLayout collaps = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collaps.setTitle("Detail Kajian Pekan");

        Context context = this;
        collaps.setContentScrimColor(ContextCompat.getColor(context, R.color.white));
        collaps.setExpandedTitleTextAppearance(R.style.expandedappbar);
        collaps.setCollapsedTitleTextAppearance(R.style.collapsedappbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lat_req = extras.getString("lat_key");
            lng_req = extras.getString("lng_key");
            mule_req = extras.getString("mule_key");
            sampe_req = extras.getString("sampe_key");
            tgl_req = extras.getString("tgl_key");
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

    public void setTextDetailKajian(){
        String[] mule = mule_req.split(":");
        String[] smpe = sampe_req.split(":");
        String[] pisah = tgl_req.split("-");
        tglDetail.setText(pisah[2]+" "+utils.selectPekan(pisah[1]) + " " +pisah[0]);
        mulaiDetail.setText("Pukul "+mule[0]+":"+mule[1]);
        sampaiDetail.setText(smpe[0]+":"+smpe[1]+ " WIB");
        temaDetail.setText(tema_req);
        pemateriDetail.setText(pemteri_req);
        lokasiDetail.setText(lokasi_req);
        cpDetail.setText(cp_req);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng masjid = new LatLng(Double.parseDouble(lat_req), Double.parseDouble(lng_req));
        mMap.addMarker(new MarkerOptions().position(masjid).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).title("Lokasi Masjid").snippet("Tempat Kajian"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(masjid, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1500, null);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(masjid));
//        mMap.setMyLocationEnabled(true);
    }
}
