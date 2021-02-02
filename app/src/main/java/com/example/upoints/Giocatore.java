package com.example.upoints;

import android.os.Parcel;
import android.os.Parcelable;

public class Giocatore implements Parcelable {
    private String nome;
    private int punteggio;


    public Giocatore(String n)
    {
        nome=n;
        punteggio=0;
    }

    public Giocatore(Parcel source)
    {
        nome=source.readString();
        punteggio=source.readInt();
    }






    public String getNome()
    {
        return nome;
    }

    public int getPunteggio()
    {
        return punteggio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeInt(punteggio);


    }

    public static final Creator<Giocatore> CREATOR= new Creator<Giocatore>() {
        @Override
        public Giocatore createFromParcel(Parcel source) {
            return new Giocatore(source);
        }

        @Override
        public Giocatore[] newArray(int size) {
            return new Giocatore[size];
        }
    };

    public void IncrementaPunt(int p)
    {
        punteggio=punteggio+p;
    }
}
