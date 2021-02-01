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
import android.widget.EditText;
import android.widget.TextView;

public class Partita extends AppCompatActivity {
    public Intent menu,t;
    private int mano=1;
    private int n_gioc,lim;
    private Giocatore gioc1,gioc2,gioc3,gioc4;
    public AlertDialog.Builder bd;
    public AlertDialog alert;
    public boolean end,there_lim,there_time;
    public static final String TAG="Partita";
    public TextView lbl_tempo;
    Intent classifica;
    public String time_left;
    int ss,mm;
    private Button btn_next;

    @Override
    public void onBackPressed() {
        if(there_time)
        {
            stopService(t);
        }
        bd=new AlertDialog.Builder(this);
        bd.setTitle("Exit");
        bd.setMessage("Sei sicuro di voler tornare alla selezione dei giocatori?");
        bd.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        bd.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(there_time)
                {
                    t=new Intent(Partita.this,mioService.class);
                    t.putExtra("Input",(mm*60)+ss);
                    startService(t);
                }
                dialog.dismiss();
            }
        });
        alert=bd.create();
        alert.show();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classifica=new Intent(Partita.this,Classifica.class);
        setContentView(R.layout.activity_partita);
        TextView txt_tot1=findViewById(R.id.tot1);
        TextView txt_tot2=findViewById(R.id.tot2);
        TextView txt_tot3=findViewById(R.id.tot3);
        TextView txt_tot4=findViewById(R.id.tot4);
        txt_tot1.setText("0");
        txt_tot2.setText("0");
        txt_tot3.setText("0");
        txt_tot4.setText("0");

        TextView txt_mano=findViewById(R.id.mano);

        TextView lbl_gioc1=findViewById(R.id.gioc1);
        TextView lbl_gioc2=findViewById(R.id.gioc2);
        TextView lbl_gioc3=findViewById(R.id.gioc3);
        TextView lbl_gioc4=findViewById(R.id.gioc4);

        TextView lbl_lim=findViewById(R.id.limite);

        EditText ed_punt1=findViewById(R.id.punt1);
        EditText ed_punt2=findViewById(R.id.punt2);
        ed_punt2.setVisibility(View.INVISIBLE);
        EditText ed_punt3=findViewById(R.id.punt3);
        ed_punt3.setVisibility(View.INVISIBLE);
        EditText ed_punt4=findViewById(R.id.punt4);
        ed_punt4.setVisibility(View.INVISIBLE);
        lbl_tempo=findViewById(R.id.time_left);



        menu=getIntent();
        n_gioc=menu.getIntExtra("N_gioc",1);
        classifica.putExtra("N_gioc",n_gioc);
        there_lim=menu.getBooleanExtra("Limite?",false);
        there_time=menu.getBooleanExtra("Tempo?",false);
        if(there_lim)
        {
            lim=menu.getIntExtra("Limite",0);
            lbl_lim.setText("GOAL:"+String.valueOf(lim));
            lbl_lim.setVisibility(View.VISIBLE);
            classifica.putExtra("Limite?",true);
            classifica.putExtra("Limite",lim);

        }else
        {
            classifica.putExtra("Limite?",false);
            lbl_lim.setVisibility(View.INVISIBLE);
        }
        if(there_time)
        {
            classifica.putExtra("Tempo?",true);
            t=new Intent(Partita.this,mioService.class);
            t.putExtra("Input",menu.getIntExtra("Tempo",120));
            lbl_tempo.setText(menu.getStringExtra("String_tempo"));
            lbl_tempo.setVisibility(View.VISIBLE);
            startService(t);
            Log.d(TAG,"Started Service");

        }else
        {
            classifica.putExtra("Tempo?",false);
            lbl_tempo.setVisibility(View.INVISIBLE);
        }
        lbl_gioc2.setVisibility(View.INVISIBLE);
        lbl_gioc3.setVisibility(View.INVISIBLE);
        lbl_gioc4.setVisibility(View.INVISIBLE);

        gioc1=(Giocatore) menu.getParcelableExtra("Gioc1");
        lbl_gioc1.setText(gioc1.getNome());
        if(n_gioc>1)
        {
            gioc2=(Giocatore) menu.getParcelableExtra("Gioc2");
            lbl_gioc2.setText(gioc2.getNome());
            lbl_gioc2.setVisibility(View.VISIBLE);
            ed_punt2.setVisibility(View.VISIBLE);
            if(n_gioc>2)
            {
                gioc3=(Giocatore) menu.getParcelableExtra("Gioc3");
                lbl_gioc3.setText(gioc3.getNome());
                lbl_gioc3.setVisibility(View.VISIBLE);
                ed_punt3.setVisibility(View.VISIBLE);
                if(n_gioc>3)
                {
                    gioc4=(Giocatore) menu.getParcelableExtra("Gioc4");
                    lbl_gioc4.setText(gioc4.getNome());
                    lbl_gioc4.setVisibility(View.VISIBLE);
                    ed_punt4.setVisibility(View.VISIBLE);

                }else
                {
                    txt_tot4.setVisibility(View.INVISIBLE);
                }
            }else
            {
                txt_tot3.setVisibility(View.INVISIBLE);
                txt_tot4.setVisibility(View.INVISIBLE);
            }
        }else
        {
            txt_tot2.setVisibility(View.INVISIBLE);
            txt_tot3.setVisibility(View.INVISIBLE);
            txt_tot4.setVisibility(View.INVISIBLE);
        }

        btn_next=findViewById(R.id.next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            //FERMARE E RIPRENDERE IL TIMER
            @Override
            public void onClick(View v) {
                if(btn_next.getText().toString().equals("FINISH"))
                {
                    classifica=new Intent(Partita.this,Classifica.class);
                    classifica.putExtra("N_gioc",n_gioc);
                    classifica.putExtra("Gioc1",gioc1);
                    //String str;
                    if(there_lim)
                    {
                        classifica.putExtra("Limite",lim);
                        classifica.putExtra("Limite?",true);
                    }else
                    {
                        classifica.putExtra("Limite?",false);
                    }
                    classifica.putExtra("Tempo?",true);
                    if(n_gioc>1)
                    {
                        classifica.putExtra("Gioc2",gioc2);
                        if(n_gioc>2)
                        {
                            classifica.putExtra("Gioc3",gioc3);
                            if(n_gioc>3)
                            {
                                classifica.putExtra("Gioc4",gioc4);
                            }
                        }
                    }
                    startActivity(classifica);
                }else
                {
                    if(there_time)
                    {
                        stopService(t);
                    }

                    if(ed_punt1.getText().toString().equals(""))
                    {
                        ed_punt1.setText("0");
                    }
                    if(n_gioc>1)
                    {
                        if(ed_punt2.getText().toString().equals(""))
                        {
                            ed_punt2.setText("0");
                        }
                        if(n_gioc>2)
                        {
                            if(ed_punt3.getText().toString().equals(""))
                            {
                                ed_punt3.setText("0");
                            }
                            if(n_gioc>3)
                            {
                                if(ed_punt4.getText().toString().equals(""))
                                {
                                    ed_punt4.setText("0");
                                }
                            }
                        }
                    }
                    bd=new AlertDialog.Builder(Partita.this);
                    bd.setTitle("CONFERMA");
                    String str="Sei sicuro di voler procedere?\n\n"+gioc1.getNome()+": "+ed_punt1.getText();
                    if(n_gioc>1)
                    {
                        str=str.concat("\n\n"+gioc2.getNome()+": "+ed_punt2.getText());
                        if(n_gioc>2)
                        {
                            str=str.concat("\n\n"+gioc3.getNome()+": "+ed_punt3.getText());
                            if(n_gioc>3)
                            {
                                str=str.concat("\n\n"+gioc4.getNome()+": "+ed_punt4.getText());
                            }
                        }
                    }
                    bd.setMessage(str);
                    bd.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(there_time)
                            {
                                t=new Intent(Partita.this,mioService.class);
                                t.putExtra("Input",(mm*60)+ss);
                                startService(t);
                            }

                            dialog.dismiss();
                            if(there_lim)
                            {
                                end=false;
                                if(ed_punt1.getText().toString().equals(""))
                                {
                                    gioc1.IncrementaPunt(0);
                                    if(gioc1.getMaxp()<0)
                                    {
                                        gioc1.setMaxp(0);
                                    }
                                }else
                                {
                                    gioc1.IncrementaPunt(Integer.parseInt(ed_punt1.getText().toString()));
                                    ed_punt1.setText("");
                                    txt_tot1.setText(String.valueOf(gioc1.getPunteggio()));
                                    if(gioc1.getMaxp()<Integer.parseInt(ed_punt1.getText().toString()))
                                    {
                                        gioc1.setMaxp(Integer.parseInt(ed_punt1.getText().toString()));
                                    }
                                }
                                if(n_gioc>1)
                                {
                                    if(ed_punt2.getText().toString().equals(""))
                                    {
                                        gioc2.IncrementaPunt(0);
                                        if(gioc2.getMaxp()<0)
                                        {
                                            gioc2.setMaxp(0);
                                        }
                                    }else
                                    {
                                        gioc2.IncrementaPunt(Integer.parseInt(ed_punt2.getText().toString()));
                                        ed_punt2.setText("");
                                        if(gioc2.getMaxp()<Integer.parseInt(ed_punt2.getText().toString()))
                                        {
                                            gioc2.setMaxp(Integer.parseInt(ed_punt2.getText().toString()));
                                        }
                                    }
                                    if(n_gioc>2)
                                    {
                                        if(ed_punt3.getText().toString().equals(""))
                                        {
                                            gioc3.IncrementaPunt(0);
                                            if(gioc3.getMaxp()<0)
                                            {
                                                gioc3.setMaxp(0);
                                            }

                                        }else
                                        {
                                            gioc3.IncrementaPunt(Integer.parseInt(ed_punt3.getText().toString()));
                                            ed_punt3.setText("");
                                            if(gioc3.getMaxp()<Integer.parseInt(ed_punt3.getText().toString()))
                                            {
                                                gioc3.setMaxp(Integer.parseInt(ed_punt3.getText().toString()));
                                            }
                                        }
                                        if(n_gioc>3)
                                        {
                                            if(ed_punt4.getText().toString().equals(""))
                                            {
                                                gioc4.IncrementaPunt(0);
                                                if(gioc4.getMaxp()<0)
                                                {
                                                    gioc4.setMaxp(0);
                                                }
                                            }else
                                            {
                                                gioc4.IncrementaPunt(Integer.parseInt(ed_punt4.getText().toString()));
                                                ed_punt4.setText("");
                                                if(gioc4.getMaxp()<Integer.parseInt(ed_punt4.getText().toString()))
                                                {
                                                    gioc4.setMaxp(Integer.parseInt(ed_punt4.getText().toString()));
                                                }
                                                if(gioc1.getPunteggio()>=lim || gioc2.getPunteggio()>=lim || gioc3.getPunteggio()>=lim || gioc4.getPunteggio()>=lim)
                                                {
                                                    classifica.putExtra("Gioc1",gioc1);
                                                    classifica.putExtra("Gioc2",gioc2);
                                                    classifica.putExtra("Gioc3",gioc3);
                                                    classifica.putExtra("Gioc4",gioc4);
                                                    end=true;
                                                    classifica.putExtra("Mano",mano);
                                                    startActivity(classifica);
                                                }
                                            }

                                        }else if(gioc1.getPunteggio()>=lim || gioc2.getPunteggio()>=lim || gioc3.getPunteggio()>=lim)
                                        {
                                            classifica.putExtra("Gioc1",gioc1);
                                            classifica.putExtra("Gioc2",gioc2);
                                            classifica.putExtra("Gioc3",gioc3);
                                            end=true;
                                            classifica.putExtra("Mano",mano);
                                            startActivity(classifica);
                                        }
                                    }else if(gioc1.getPunteggio()>=lim || gioc2.getPunteggio()>=lim)
                                    {
                                        classifica.putExtra("Gioc1",gioc1);
                                        classifica.putExtra("Gioc2",gioc2);
                                        end=true;
                                        classifica.putExtra("Mano",mano);
                                        startActivity(classifica);

                                    }
                                }else if(gioc1.getPunteggio()>=lim)
                                {
                                    classifica.putExtra("Gioc1",gioc1);
                                    end=true;
                                    classifica.putExtra("Mano",mano);
                                    startActivity(classifica);
                                }
                                if(end!=true)
                                {
                                    mano++;
                                    txt_tot1.setText(String.valueOf(gioc1.getPunteggio()));
                                    if(n_gioc>1)
                                    {
                                        txt_tot2.setText(String.valueOf(gioc2.getPunteggio()));
                                        if(n_gioc>2)
                                        {
                                            txt_tot3.setText(String.valueOf(gioc3.getPunteggio()));
                                            if(n_gioc>3)
                                            {
                                                txt_tot4.setText(String.valueOf(gioc4.getPunteggio()));
                                            }
                                        }
                                    }
                                    txt_mano.setText("MANO "+(mano));



                                }
                            }else
                            {
                                if(ed_punt1.getText().toString().equals(""))
                                {
                                    gioc1.IncrementaPunt(0);
                                }else
                                {
                                    gioc1.IncrementaPunt(Integer.parseInt(ed_punt1.getText().toString()));
                                    ed_punt1.setText("");
                                    txt_tot1.setText(String.valueOf(gioc1.getPunteggio()));
                                }
                                if(n_gioc>1) {
                                    if (ed_punt2.getText().toString().equals("")) {
                                        gioc2.IncrementaPunt(0);
                                    } else {
                                        gioc2.IncrementaPunt(Integer.parseInt(ed_punt2.getText().toString()));
                                        ed_punt2.setText("");
                                    }
                                    if (n_gioc > 2) {
                                        if (ed_punt3.getText().toString().equals("")) {
                                            gioc3.IncrementaPunt(0);
                                        } else {
                                            gioc3.IncrementaPunt(Integer.parseInt(ed_punt3.getText().toString()));
                                            ed_punt3.setText("");
                                        }
                                        if (n_gioc > 3) {
                                            if (ed_punt4.getText().toString().equals("")) {
                                                gioc4.IncrementaPunt(0);
                                            }
                                        }
                                    }
                                }
                                mano++;
                                txt_tot1.setText(String.valueOf(gioc1.getPunteggio()));
                                if(n_gioc>1)
                                {
                                    txt_tot2.setText(String.valueOf(gioc2.getPunteggio()));
                                    if(n_gioc>2)
                                    {
                                        txt_tot3.setText(String.valueOf(gioc3.getPunteggio()));
                                        if(n_gioc>3)
                                        {
                                            txt_tot4.setText(String.valueOf(gioc4.getPunteggio()));
                                        }
                                    }
                                }
                                txt_mano.setText("MANO "+(mano));
                            }
                        }
                    });
                    bd.setNegativeButton("ANNULLA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if(there_time)
                            {
                                t=new Intent(Partita.this,mioService.class);
                                t.putExtra("Input",(mm*60)+ss);
                                startService(t);
                            }

                            dialog.dismiss();
                        }
                    });
                    alert=bd.create();

                    alert.show();


                }
                }

        });




    }

    private BroadcastReceiver mioBroadcastService=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent);


        }
    };

    private void updateGUI(Intent intent) {

        if(intent.getExtras()!=null)
        {
            long millisUntilFinished = intent.getLongExtra("Count",30000);
            boolean end=intent.getBooleanExtra("End?",false);
            Log.d(TAG,"Countdown rimanente:"+millisUntilFinished/1000);
            if(end)
            {
                bd=new AlertDialog.Builder(Partita.this);
                bd.setTitle("Timer Scaduto!");
                bd.setMessage("Il tempo della partita è terminato\nVuoi finire la mano in corso?");
                bd.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        btn_next.setText("FINISH");

                    }
                });
                bd.setNegativeButton("NO, VAI A CLASSIFICA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        classifica=new Intent(Partita.this,Classifica.class);
                        classifica.putExtra("N_gioc",n_gioc);
                        classifica.putExtra("Gioc1",gioc1);
                        //String str;
                        if(there_lim)
                        {
                            classifica.putExtra("Limite",lim);
                            classifica.putExtra("Limite?",true);
                        }else
                        {
                            classifica.putExtra("Limite?",false);
                        }
                        classifica.putExtra("Tempo?",true);
                        if(n_gioc>1)
                        {
                            classifica.putExtra("Gioc2",gioc2);
                            if(n_gioc>2)
                            {
                                classifica.putExtra("Gioc3",gioc3);
                                if(n_gioc>3)
                                {
                                    classifica.putExtra("Gioc4",gioc4);
                                }
                            }
                        }
                        startActivity(classifica);
                    }
                });
                alert=bd.create();
                alert.show();
            }else
            {
                mm=(int)millisUntilFinished/60000;
                ss=(int)(millisUntilFinished%60000)/1000;
                Log.d(TAG,String.valueOf(mm));
                if(mm<10)
                {
                    if(ss<10)
                    {
                        time_left="0"+mm+":0"+ss;
                        lbl_tempo.setText(time_left);
                    }else
                    {
                        time_left="0"+ mm +":"+ss;
                        lbl_tempo.setText(time_left);
                    }
                }else
                {
                    if(ss<10)
                    {
                        time_left=mm+":0"+ss;
                        lbl_tempo.setText(time_left);
                    }else
                    {
                        time_left= mm +":"+ss;
                        lbl_tempo.setText(time_left);
                    }
                }

            }

        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,mioService.class));
        Log.d(TAG,"Service stopped");
        super.onDestroy();
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
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mioBroadcastService);
        Log.d(TAG,"Broadcast non registrato");
    }

    protected void onResume() {
        super.onResume();
        registerReceiver(mioBroadcastService,new IntentFilter(mioService.COUNTDOWN_BR));
        Log.d(TAG,"Broadcast receiver registrato");

    }

}