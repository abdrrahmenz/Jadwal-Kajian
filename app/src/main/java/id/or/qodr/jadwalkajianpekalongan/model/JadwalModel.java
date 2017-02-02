package id.or.qodr.jadwalkajianpekalongan.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adul on 21/01/17.
 */

public class JadwalModel implements Parcelable {

    public String id, foto_masjid, tanggal, mulai, sampai, tema, pemateri, lokasi, cp;

    protected JadwalModel(Parcel in) {
        id = in.readString();
        foto_masjid = in.readString();
        tanggal = in.readString();
        mulai = in.readString();
        sampai = in.readString();
        tema = in.readString();
        pemateri = in.readString();
        lokasi = in.readString();
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
        dest.writeString(foto_masjid);
        dest.writeString(tanggal);
        dest.writeString(mulai);
        dest.writeString(sampai);
        dest.writeString(tema);
        dest.writeString(pemateri);
        dest.writeString(lokasi);
        dest.writeString(cp);
    }
}
