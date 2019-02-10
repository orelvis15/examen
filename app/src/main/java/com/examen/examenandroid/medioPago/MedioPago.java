package com.examen.examenandroid.medioPago;

import android.os.Parcel;
import android.os.Parcelable;

public class MedioPago implements Parcelable {

    private String id;
    private String nombre;
    private String urlBitmap;

    public MedioPago(String id, String nombre, String urlBitmap) {
        this.id = id;
        this.nombre = nombre;
        this.urlBitmap = urlBitmap;
    }

    private MedioPago(Parcel in) {
        id = in.readString();
        nombre = in.readString();
        urlBitmap = in.readString();
    }

    public static final Creator<MedioPago> CREATOR = new Creator<MedioPago>() {
        @Override
        public MedioPago createFromParcel(Parcel in) {
            return new MedioPago(in);
        }

        @Override
        public MedioPago[] newArray(int size) {
            return new MedioPago[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlBitmap() {
        return urlBitmap;
    }

    public void setUrlBitmap(String urlBitmap) {
        this.urlBitmap = urlBitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nombre);
        dest.writeString(urlBitmap);
    }
}
