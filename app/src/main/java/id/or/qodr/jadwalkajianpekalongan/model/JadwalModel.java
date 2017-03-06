package id.or.qodr.jadwalkajianpekalongan.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adul on 21/01/17.
 */

public class JadwalModel implements Parcelable {

    public String id, jenis_kajian, foto_masjid, setiap_hari, pekan, tanggal, mulai, sampai, tema, pemateri, lokasi, lat, lng, cp;

    protected JadwalModel(Parcel in) {
        id = in.readString();
        jenis_kajian = in.readString();
        foto_masjid = in.readString();
        setiap_hari = in.readString();
        pekan = in.readString();
        tanggal = in.readString();
        mulai = in.readString();
        sampai = in.readString();
        tema = in.readString();
        pemateri = in.readString();
        lokasi = in.readString();
        lat = in.readString();
        lng = in.readString();
        cp = in.readString();
    }

    public static final Creator<JadwalModel> CREATOR = new Creator<JadwalModel>() {
        @Override
        public JadwalModel createFromParcel(Parcel in) {
            return new JadwalModel(in);
        }

        @Override
        public JadwalModel[] newArray(int size) {
            return new JadwalModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(jenis_kajian);
        dest.writeString(foto_masjid);
        dest.writeString(setiap_hari);
        dest.writeString(pekan);
        dest.writeString(tanggal);
        dest.writeString(mulai);
        dest.writeString(sampai);
        dest.writeString(tema);
        dest.writeString(pemateri);
        dest.writeString(lokasi);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(cp);
    }
}
