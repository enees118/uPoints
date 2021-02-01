package com.example.upoints;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private int n_gioc, lim,tempo;
    private Giocatore gioc1,gioc2,gioc3,gioc4;
    public AlertDialog.Builder bd;
    public AlertDialog alert;
    public boolean vuoto=false;
    public Intent partita;
    public final String TAG="MainActivity";


    @Override
    public void onBackPressed() {
        bd=new AlertDialog.Builder(MainActivity.this);
        bd.setTitle("EXIT");
        bd.setMessage("Vuoi chiudere l'applicazione?");
        bd.setCancelable(true);
        bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finishAffinity();
            }
        });
        bd.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert=bd.create();
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btn_del1=findViewById(R.id.del1);
        btn_del1.setVisibility(View.INVISIBLE);
        ImageButton btn_del2=findViewById(R.id.del2);
        btn_del2.setVisibility(View.INVISIBLE);
        ImageButton btn_del3=findViewById(R.id.del3);
        btn_del3.setVisibility(View.INVISIBLE);
        ImageButton btn_del4=findViewById(R.id.del4);
        btn_del4.setVisibility(View.INVISIBLE);

        EditText ed_nome1=findViewById(R.id.nome1);
        EditText ed_nome2=findViewById(R.id.nome2);
        EditText ed_nome3=findViewById(R.id.nome3);
        EditText ed_nome4=findViewById(R.id.nome4);
        EditText ed_lim=findViewById(R.id.lim);
        n_gioc=1;
        Switch sw_lim=findViewById(R.id.sw_lim);
        Switch sw_temp=findViewById(R.id.sw_temp);
        TextView lbl_tempo=findViewById(R.id.temp);
        ImageButton btn_prox=findViewById(R.id.prox);
        ImageButton btn_prec=findViewById(R.id.prec);
        btn_prox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(lbl_tempo.getText().toString())
                {
                    case "01:00":
                        lbl_tempo.setText("05:00");
                        break;
                    case "05:00":
                        lbl_tempo.setText("10:00");
                        break;
                    case "10:00":
                        lbl_tempo.setText("15:00");
                        break;
                    case "15:00":
                        lbl_tempo.setText("30:00");
                        break;
                    case "30:00":
                        lbl_tempo.setText("60:00");
                        break;
                    case "60:00":
                        lbl_tempo.setText("120:00");
                        break;
                    case "120:00":
                        lbl_tempo.setText("01:00");
                        break;
                }

            }
        });
        btn_prec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(lbl_tempo.getText().toString())
                {
                    case "01:00":
                        lbl_tempo.setText("120:00");
                        break;
                    case "05:00":
                        lbl_tempo.setText("01:00");
                        break;
                    case "120:00":
                        lbl_tempo.setText("60:00");
                        break;
                    case "60:00":
                        lbl_tempo.setText("30:00");
                        break;
                    case "30:00":
                        lbl_tempo.setText("15:00");
                        break;
                    case "15:00":
                        lbl_tempo.setText("10:00");
                        break;
                    case "10:00":
                        lbl_tempo.setText("05:00");
                        break;
                }
            }
        });

        TextView et_lim=findViewById(R.id.lbl_lim);
        TextView et_temp=findViewById(R.id.lbl_tempo);
        et_temp.setEnabled(false);
        lbl_tempo.setEnabled(false);
        btn_prox.setEnabled(false);
        btn_prec.setEnabled(false);
        sw_temp.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked==true)
            {
                lbl_tempo.setEnabled(true);
                btn_prox.setEnabled(true);
                btn_prec.setEnabled(true);
                et_temp.setEnabled(true);
            }else
            {
                lbl_tempo.setEnabled(false);
                btn_prox.setEnabled(false);
                btn_prec.setEnabled(false);
                et_temp.setEnabled(false);
            }
        });
        sw_lim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true)
                {
                    ed_lim.setEnabled(true);
                    et_lim.setEnabled(true);
                }else
                {
                    ed_lim.setEnabled(false);
                    et_lim.setEnabled(false);
                }
            }
        });


        ed_nome2.setVisibility(View.INVISIBLE);
        ed_nome3.setVisibility(View.INVISIBLE);
        ed_nome4.setVisibility(View.INVISIBLE);



        Button btn_reset=findViewById(R.id.reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_nome1.getText().toString().equals("") && ed_nome2.getText().toString().equals("") && ed_nome3.getText().toString().equals("") && ed_nome4.getText().toString().equals("") )
                {
                    bd=new AlertDialog.Builder(MainActivity.this);
                    bd.setTitle("GIA RESETTATO!");
                    bd.setMessage("Hai gia resettato il nome di tutti i giocatori");
                    bd.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert=bd.create();
                    alert.show();
                }else
                {
                    ed_nome1.setText("");
                    if(n_gioc>1)
                    {
                        ed_nome2.setText("");
                        if(n_gioc>2)
                        {
                            ed_nome3.setText("");
                            if(n_gioc>3)
                            {
                                ed_nome4.setText("");
                            }
                        }
                    }
                }
            }
        });

        btn_del1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_nome1.setText(ed_nome2.getText());
                if(n_gioc>2)
                {
                    ed_nome2.setText(ed_nome3.getText());
                    if(n_gioc>3)
                    {
                        ed_nome3.setText(ed_nome4.getText());
                        ed_nome4.setText("");
                        ed_nome4.setVisibility(View.INVISIBLE);
                        btn_del4.setVisibility(View.INVISIBLE);
                    }else
                    {
                        ed_nome3.setText("");
                        ed_nome3.setVisibility(View.INVISIBLE);
                        btn_del3.setVisibility(View.INVISIBLE);
                    }
                }else
                {
                    ed_nome2.setText("");
                    ed_nome2.setVisibility(View.INVISIBLE);
                    btn_del1.setVisibility(View.INVISIBLE);
                    btn_del2.setVisibility(View.INVISIBLE);
                }
                n_gioc--;
            }
        });

        btn_del2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(n_gioc>2)
                {
                    ed_nome2.setText(ed_nome3.getText());
                    if(n_gioc>3)
                    {
                        ed_nome3.setText(ed_nome4.getText());
                        ed_nome4.setText("");
                        ed_nome4.setVisibility(View.INVISIBLE);
                        btn_del4.setVisibility(View.INVISIBLE);
                    }else
                    {
                        ed_nome3.setText("");
                        ed_nome3.setVisibility(View.INVISIBLE);
                        btn_del3.setVisibility(View.INVISIBLE);
                    }
                }else
                {
                    ed_nome2.setText("");
                    ed_nome2.setVisibility(View.INVISIBLE);
                    btn_del2.setVisibility(View.INVISIBLE);
                    btn_del1.setVisibility(View.INVISIBLE);
                }
                n_gioc--;
            }
        });

        btn_del3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(n_gioc>3)
                {
                    ed_nome3.setText(ed_nome4.getText());
                    ed_nome4.setText("");
                    ed_nome4.setVisibility(View.INVISIBLE);
                    btn_del4.setVisibility(View.INVISIBLE);
                }else
                {
                    ed_nome3.setText("");
                    ed_nome3.setVisibility(View.INVISIBLE);
                    btn_del3.setVisibility(View.INVISIBLE);
                }
                n_gioc--;
            }
        });

        btn_del4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n_gioc--;
                ed_nome4.setVisibility(View.INVISIBLE);
                ed_nome4.setText("");
                btn_del4.setVisibility(View.INVISIBLE);

            }
        });


        Button btn_start=findViewById(R.id.start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vuoto=false;
                partita=new Intent(MainActivity.this, Partita.class);
                partita.putExtra("N_gioc",n_gioc);
                if(sw_lim.isChecked()==true)
                {
                    lim=Integer.parseInt(ed_lim.getText().toString());
                    partita.putExtra("Limite",lim);
                    partita.putExtra("Limite?",true);
                }
                if(sw_temp.isChecked()==true)
                {
                    switch(lbl_tempo.getText().toString())
                    {
                        case "01:00":
                            tempo=60;
                            break;
                        case "05:00":
                            tempo=300;
                            break;
                        case "10:00":
                            tempo=600;
                            break;
                        case "15:00":
                            tempo=900;
                            break;
                        case "30:00":
                            tempo=1800;
                            break;
                        case "60:00":
                            tempo=3600;
                            break;
                        case "120:00":
                            tempo=7200;
                            break;
                    }
                    partita.putExtra("Tempo",tempo);
                    partita.putExtra("Tempo?",true);
                    partita.putExtra("Stringa_tempo",lbl_tempo.getText().toString());

                }

                if(sw_lim.isChecked() && lim==0)
                {
                    bd=new AlertDialog.Builder(MainActivity.this);
                    bd.setTitle("ERRORE LIMITE PUNTI!");
                    bd.setMessage("Inserire un limite di punti maggiore di zero! ");
                    bd.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert=bd.create();
                    alert.show();
                }else
                {
                    if(!ed_nome1.getText().toString().equals(""))
                    {
                        gioc1= new Giocatore(ed_nome1.getText().toString().toUpperCase());
                        partita.putExtra("Gioc1",gioc1);
                        if(n_gioc>1)
                        {
                            if(!ed_nome2.getText().toString().equals(""))
                            {
                                gioc2= new Giocatore(ed_nome2.getText().toString().toUpperCase());
                                partita.putExtra("Gioc2",gioc2);
                                if(n_gioc>2)
                                {
                                    if(!ed_nome3.getText().toString().equals(""))
                                    {
                                        gioc3= new Giocatore(ed_nome3.getText().toString().toUpperCase());
                                        partita.putExtra("Gioc3",gioc3);
                                        if(n_gioc>3)
                                        {
                                            if(!ed_nome4.getText().toString().equals(""))
                                            {
                                                gioc4= new Giocatore(ed_nome4.getText().toString().toUpperCase());
                                                partita.putExtra("Gioc4",gioc4);
                                            }else
                                            {
                                                vuoto=true;
                                            }
                                        }
                                    }else
                                    {
                                        vuoto=true;
                                    }
                                }
                            }else
                            {
                                vuoto=true;
                            }
                        }
                    }else
                    {
                        vuoto=true;
                    }

                    if(vuoto==true)
                    {
                        bd=new AlertDialog.Builder(MainActivity.this);
                        bd.setTitle("CAMPI MANCANTI!");
                        bd.setMessage("Inserisci i nomi di tutti i giocatori!");
                        bd.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alert=bd.create();
                        alert.show();
                    }else
                    {
                        if(n_gioc==1)
                        {
                            bd=new AlertDialog.Builder(MainActivity.this);
                            bd.setTitle("VUOI CONTINUARE?");
                            bd.setMessage("Sei sicuro di voler procedere con un solo giocatore?");
                            bd.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    startActivity(partita);
                                }
                            });
                            bd.setNeutralButton("ANNULLA", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   dialog.dismiss();
                                }
                            });
                            alert=bd.create();
                            alert.show();
                        }else
                        {
                            startActivity(partita);
                        }

                    }
                }
            }
        });
        
        FloatingActionButton fab_new=findViewById(R.id.new_gioc);
        fab_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n_gioc++;
                if(n_gioc>1)
                {
                    ed_nome2.setVisibility(View.VISIBLE);
                    btn_del1.setVisibility(View.VISIBLE);
                    btn_del2.setVisibility(View.VISIBLE);
                    if(n_gioc>2)
                    {
                        ed_nome3.setVisibility(View.VISIBLE);
                        btn_del3.setVisibility(View.VISIBLE);
                        if(n_gioc>3)
                        {
                            ed_nome4.setVisibility(View.VISIBLE);
                            btn_del4.setVisibility(View.VISIBLE);
                            if(n_gioc>4)
                            {
                                n_gioc=4;
                                bd=new AlertDialog.Builder(MainActivity.this);
                                bd.setTitle("GIOCATORI MAX:4");
                                bd.setMessage("Numero massimo di giocatori raggiunto");
                                bd.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert=bd.create();
                                alert.show();
                            }
                        }
                    }
                }
            }
        });


    }

    private BroadcastReceiver mioBroadcastService=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


        }
    };

    protected void onResume() {
        super.onResume();
        registerReceiver(mioBroadcastService,new IntentFilter(mioService.COUNTDOWN_BR));
        Log.d(TAG,"Broadcast receiver registrato");

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mioBroadcastService);
        Log.d(TAG,"Broadcast non registrato");
    }

    @Override
    protected void onStop() {
        try
        {
            unregisterReceiver(mioBroadcastService);

        }catch(Exception e)
        {
            //Receiver gia pronto

        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,mioService.class));
        Log.d(TAG,"Service stopped");
        super.onDestroy();
    }
}