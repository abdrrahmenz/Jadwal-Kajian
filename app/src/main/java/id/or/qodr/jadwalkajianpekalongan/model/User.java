package id.or.qodr.jadwalkajianpekalongan.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by adul on 21/04/17.
 */

public class User implements Parcelable{

//    public ArrayList<Users> users;
//
//
//    public ArrayList<Users> getUsers() {
//        return users;
//    }
//
//    public class Users {
//        @SerializedName("id")
//        @Expose
//        private String id;
//        @SerializedName("full_name")
//        @Expose
//        public String fullName;
////        @SerializedName("username")
////        @Expose
//        public String username;
////        @SerializedName("password")
////        @Expose
//        public String password;
//        @SerializedName("created_at")
//        @Expose
//        private String createdAt;
//        @SerializedName("updated_at")
//        @Expose
//        private String updatedAt;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getFullName() {
//            return fullName;
//        }
//
//        public void setFullName(String fullName) {
//            this.fullName = fullName;
//        }
//
//        public String getUsername() {
//            return username;
//        }
//
//        public void setUsername(String username) {
//            this.username = username;
//        }
//
//        public String getPassword() {
//            return password;
//        }
//
//        public void setPassword(String password) {
//            this.password = password;
//        }
//
//        public String getCreatedAt() {
//            return createdAt;
//        }
//
//        public void setCreatedAt(String createdAt) {
//            this.createdAt = createdAt;
//        }
//
//        public String getUpdatedAt() {
//            return updatedAt;
//        }
//
//        public void setUpdatedAt(String updatedAt) {
//            this.updatedAt = updatedAt;
//        }
//    }
    public String id, fullname, username, password, created_at, updated_at;

    protected User(Parcel in) {
        id = in.readString();
        fullname = in.readString();
        username = in.readString();
        password = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(fullname);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }
}
