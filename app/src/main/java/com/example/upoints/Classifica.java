package com.example.upoints;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Classifica extends AppCompatActivity {

    private Giocatore gioc1,gioc2,gioc3,gioc4,primo,secondo,terzo,quarto;
    public Intent part;
    private int n_gioc,lim,mano;
    public boolean pr_se,se_te,te_qu,pr_se_te,pr_se_te_qu,se_te_qu;
    public AlertDialog.Builder bd;
    public AlertDialog alert;
    public Intent intent;

    @Override
    public void onBackPressed() {
        bd=new AlertDialog.Builder(Classifica.this);
        bd.setTitle("EXIT");
        bd.setMessage("Sei sicuro di voler tornare al menu? ");
        bd.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent=new Intent(Classifica.this,MainActivity.class);
                dialog.dismiss();
                startActivity(intent);
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
        setContentView(R.layout.activity_classifica);

        ImageButton btn_info1=findViewById(R.id.info1);
        ImageButton btn_info2=findViewById(R.id.info2);
        ImageButton btn_info3=findViewById(R.id.info3);
        ImageButton btn_info4=findViewById(R.id.info4);
        btn_info2.setVisibility(View.INVISIBLE);
        btn_info3.setVisibility(View.INVISIBLE);
        btn_info4.setVisibility(View.INVISIBLE);


        pr_se=false;
        pr_se_te=false;
        pr_se_te_qu=false;
        se_te=false;
        se_te_qu=false;
        te_qu=false;
        TextView txt_punt_primo=findViewById(R.id.punt_primo);
        TextView txt_punt_secondo=findViewById(R.id.punt_secondo);
        TextView txt_punt_terzo=findViewById(R.id.punt_terzo);
        TextView txt_punt_quarto=findViewById(R.id.punt_quarto);
        TextView txt_vincitore=findViewById(R.id.vincitore);
        txt_punt_secondo.setVisibility(View.INVISIBLE);
        txt_punt_terzo.setVisibility(View.INVISIBLE);
        txt_punt_quarto.setVisibility(View.INVISIBLE);
        TextView txt_primo=findViewById(R.id.primo);
        TextView txt_secondo=findViewById(R.id.secondo);
        TextView txt_terzo=findViewById(R.id.terzo);
        TextView txt_quarto=findViewById(R.id.quarto);
        ImageView img_gold=findViewById(R.id.gold);
        ImageView img_silver=findViewById(R.id.silver);
        ImageView img_bronze=findViewById(R.id.bronze);
        ImageView img_quarto=findViewById(R.id.medal_quarto);
        txt_secondo.setVisibility(View.INVISIBLE);
        txt_terzo.setVisibility(View.INVISIBLE);
        txt_quarto.setVisibility(View.INVISIBLE);
        img_silver.setVisibility(View.INVISIBLE);
        img_bronze.setVisibility(View.INVISIBLE);
        img_quarto.setVisibility(View.INVISIBLE);

        part=getIntent();
        n_gioc=part.getIntExtra("N_gioc",1);
        lim=part.getIntExtra("Limite",0);
        mano=part.getIntExtra("Mano",0);
        gioc1=(Giocatore) part.getParcelableExtra("Gioc1");
        primo=gioc1;
        if(n_gioc>1)
        {
            gioc2=(Giocatore) part.getParcelableExtra("Gioc2");
            btn_info2.setVisibility(View.VISIBLE);
            if(primo.getPunteggio()>gioc2.getPunteggio())
            {
                secondo=gioc2;
            }else if(primo.getPunteggio()<gioc2.getPunteggio())
            {
                secondo=primo;
                primo=gioc2;
            }else
            {
                secondo=gioc2;
                pr_se=true;
            }
            if(n_gioc>2)
            {
                gioc3=(Giocatore) part.getParcelableExtra("Gioc3");
                btn_info3.setVisibility(View.INVISIBLE);
                if(primo.getPunteggio()>gioc3.getPunteggio())
                {
                    if(secondo.getPunteggio()>gioc3.getPunteggio())
                    {
                        terzo=gioc3;
                    }else if(secondo.getPunteggio()<gioc3.getPunteggio())
                    {
                        terzo=secondo;
                        secondo=gioc3;
                        if(pr_se==true)
                        {
                            pr_se=false;
                        }
                    }else
                    {
                        terzo=gioc3;
                        if(pr_se==true)
                        {
                            pr_se_te=true;
                        }else
                        {
                            se_te=true;
                        }
                    }
                }else if(primo.getPunteggio()<gioc3.getPunteggio())
                {
                    terzo=secondo;
                    secondo=primo;
                    primo=gioc3;
                    if(pr_se==true)
                    {
                        pr_se=false;
                        se_te=true;
                    }
                }else
                {
                    if(pr_se==true)
                    {
                        terzo=gioc3;
                        pr_se_te=true;

                    }else
                    {
                        terzo=secondo;
                        secondo=gioc3;

                    }
                }
                if(n_gioc>3)
                {
                    gioc4=(Giocatore) part.getParcelableExtra("Gioc4");
                    btn_info4.setVisibility(View.INVISIBLE);
                    if(primo.getPunteggio()>gioc4.getPunteggio())
                    {
                        if(secondo.getPunteggio()>gioc4.getPunteggio())
                        {
                            if(terzo.getPunteggio()>gioc4.getPunteggio())
                            {
                                quarto=gioc4;
                            }else if(terzo.getPunteggio()<gioc4.getPunteggio())
                            {
                                quarto=terzo;
                                terzo=gioc4;
                            }else
                            {
                                quarto=gioc4;
                                te_qu=true;

                            }
                        }else if(secondo.getPunteggio()<gioc4.getPunteggio())
                        {
                            quarto=terzo;
                            terzo=secondo;
                            secondo=gioc4;
                            if(se_te==true)
                            {
                                se_te=false;
                                te_qu=true;
                            }
                        }else
                        {
                            if(se_te==true)
                            {
                                quarto=gioc4;
                                se_te_qu=true;
                            }
                        }
                    }else if(primo.getPunteggio()<gioc4.getPunteggio())
                    {
                        quarto=terzo;
                        terzo=secondo;
                        secondo=primo;
                        primo=gioc4;
                        if(pr_se==true)
                        {
                            pr_se=false;
                            se_te=true;
                        }else if(pr_se_te==true)
                        {
                            pr_se_te=false;
                            se_te_qu=true;
                        }else if(se_te==true)
                        {
                            se_te=false;
                            te_qu=true;
                        }
                    }else
                    {
                        if(pr_se==true)
                        {
                            quarto=terzo;
                            terzo=gioc4;
                            pr_se=false;
                            pr_se_te=true;
                        }else if(se_te==true)
                        {
                            se_te=false;
                            te_qu=true;
                            pr_se=true;
                            quarto=terzo;
                            terzo=secondo;
                            secondo=gioc4;
                        }else if(pr_se_te)
                        {
                            quarto=gioc4;
                            pr_se_te_qu=true;
                        }
                    }
                    txt_primo.setText(primo.getNome());
                    txt_punt_primo.setText(String.valueOf(primo.getPunteggio()));
                    txt_secondo.setText(secondo.getNome());
                    txt_secondo.setVisibility(View.VISIBLE);
                    txt_punt_secondo.setText(String.valueOf(secondo.getPunteggio()));
                    txt_punt_secondo.setVisibility(View.VISIBLE);
                    img_silver.setVisibility(View.VISIBLE);
                    txt_terzo.setText(terzo.getNome());
                    txt_terzo.setVisibility(View.VISIBLE);
                    txt_punt_terzo.setText(String.valueOf(terzo.getPunteggio()));
                    txt_punt_terzo.setVisibility(View.VISIBLE);
                    img_bronze.setVisibility(View.VISIBLE);
                    txt_quarto.setText(quarto.getNome());
                    txt_quarto.setVisibility(View.VISIBLE);
                    txt_punt_quarto.setText(String.valueOf(quarto.getPunteggio()));
                    txt_punt_quarto.setVisibility(View.VISIBLE);
                    if(pr_se==true)
                    {
                        txt_vincitore.setText("PAREGGIO");
                        img_silver.setImageResource(R.drawable.gold_medal);
                        if(pr_se_te==true)
                        {
                            img_bronze.setImageResource(R.drawable.gold_medal);
                            if(pr_se_te_qu==true)
                            {
                                img_quarto.setVisibility(View.VISIBLE);
                                img_quarto.setImageResource(R.drawable.gold_medal);
                            }
                        }
                    }else if(se_te==true)
                    {
                        txt_vincitore.setText("HA VINTO:"+primo);
                        img_bronze.setImageResource(R.drawable.silver_medal);
                        if(se_te_qu==true)
                        {
                            img_quarto.setVisibility(View.VISIBLE);
                            img_quarto.setImageResource(R.drawable.silver_medal);
                        }
                    }else if(te_qu==true)
                    {
                        txt_vincitore.setText("HA VINTO:"+primo);
                        img_quarto.setVisibility(View.VISIBLE);
                        img_quarto.setImageResource(R.drawable.bronze_medal);
                    }else
                    {
                        txt_vincitore.setText("HA VINTO:"+primo);
                    }


                }else
                {
                    txt_primo.setText(primo.getNome());
                    txt_punt_primo.setText(String.valueOf(primo.getPunteggio()));
                    txt_secondo.setText(secondo.getNome());
                    txt_secondo.setVisibility(View.VISIBLE);
                    txt_punt_secondo.setText(String.valueOf(secondo.getPunteggio()));
                    txt_punt_secondo.setVisibility(View.VISIBLE);
                    img_silver.setVisibility(View.VISIBLE);
                    txt_terzo.setText(terzo.getNome());
                    txt_terzo.setVisibility(View.VISIBLE);
                    txt_punt_terzo.setText(String.valueOf(terzo.getPunteggio()));
                    txt_punt_terzo.setVisibility(View.VISIBLE);
                    img_bronze.setVisibility(View.VISIBLE);
                    if(pr_se==true)
                    {
                        img_silver.setImageResource(R.drawable.gold_medal);
                        if(pr_se_te==true)
                        {
                            img_bronze.setImageResource(R.drawable.gold_trophy);
                        }
                        txt_vincitore.setText("PAREGGIO");
                    }else if(se_te==true)
                    {
                        txt_vincitore.setText("HA VINTO: "+primo.getNome());
                        img_bronze.setImageResource(R.drawable.silver_medal);
                    }
                }
            }else
            {
                txt_primo.setText(primo.getNome());
                txt_punt_primo.setText(String.valueOf(primo.getPunteggio()));
                txt_secondo.setText(secondo.getNome());
                txt_secondo.setVisibility(View.VISIBLE);
                txt_punt_secondo.setText(String.valueOf(secondo.getPunteggio()));
                txt_punt_secondo.setVisibility(View.VISIBLE);
                img_silver.setVisibility(View.VISIBLE);
                if(pr_se==true)
                {
                    txt_vincitore.setText("PAREGGIO");
                    img_gold.setVisibility(View.INVISIBLE);
                    img_silver.setVisibility(View.INVISIBLE);
                }
            }
        }else
        {
            txt_vincitore.setText("TERMINATO");
            img_gold.setVisibility(View.INVISIBLE);
            txt_primo.setText(primo.getNome());
            txt_punt_primo.setText(String.valueOf(primo.getPunteggio()));
        }

        btn_info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bd=new AlertDialog.Builder(Classifica.this);
                bd.setCancelable(true);
                bd.setTitle("STATISTICHE "+primo.getNome());
                String temp="MAX PUNTI:"+String.valueOf(primo.getMaxp());
                bd.setMessage(temp);
                bd.setNeutralButton("CHIUDI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                alert=bd.create();
                alert.show();
            }
        });

        btn_info2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bd=new AlertDialog.Builder(Classifica.this);
                bd.setCancelable(true);
                bd.setTitle("STATISTICHE "+secondo.getNome());
                String temp="";
                bd.setMessage(temp);
                bd.setNeutralButton("CHIUDI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert=bd.create();
                alert.show();
            }
        });

        btn_info3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bd=new AlertDialog.Builder(Classifica.this);
                bd.setCancelable(true);
                bd.setTitle("STATISTICHE "+terzo.getNome());
                String temp="";
                bd.setMessage(temp);
                bd.setNeutralButton("CHIUDI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert=bd.create();
                alert.show();
            }
        });

        btn_info4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bd=new AlertDialog.Builder(Classifica.this);
                bd.setCancelable(true);
                bd.setTitle("STATISTICHE "+quarto.getNome());
                String temp="";
                bd.setMessage(temp);
                bd.setNeutralButton("CHIUDI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert=bd.create();
                alert.show();

            }
        });
    }
}