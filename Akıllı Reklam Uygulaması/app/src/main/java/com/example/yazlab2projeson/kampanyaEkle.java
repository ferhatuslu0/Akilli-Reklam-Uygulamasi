package com.example.yazlab2projeson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class kampanyaEkle extends AppCompatActivity {

    Button btnEkle,btnVazgec;
    EditText firmaAd,xLokasyon,yLokasyon,kampIcerik,kampSuresi,kategori;
    private FirebaseAuth auth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();


    private  void writeNewFirma(String firmaId, String firmaAd,String xLokasyon,String yLokasyon,String kampIcerik,String kampSure,String kategori){
        firma Firma=new firma(firmaId,firmaAd,xLokasyon,yLokasyon,kampIcerik,kampSure,kategori);

        myRef.child("firma").child(firmaId).setValue(Firma);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kampanya_ekle);

        auth=FirebaseAuth.getInstance();


        btnEkle=(Button) findViewById(R.id.Ekle);
        btnVazgec=(Button) findViewById(R.id.vazgec);
        firmaAd=(EditText) findViewById(R.id.firmaAdi);
        xLokasyon=(EditText) findViewById(R.id.firmaxLokasyon);
        yLokasyon=(EditText) findViewById(R.id.firmayLokasyon);
        kampIcerik=(EditText) findViewById(R.id.kampanyaIcerik);
        kampSuresi=(EditText) findViewById(R.id.kampanyaSuresi);
        kategori=(EditText) findViewById(R.id.kategori);


        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=myRef.push().getKey();
                writeNewFirma(id,firmaAd.getText().toString(),xLokasyon.getText().toString(),yLokasyon.getText().toString(),kampIcerik.getText().toString(),kampSuresi.getText().toString(),kategori.getText().toString());
                Toast.makeText(kampanyaEkle.this, "Firma başarıyla Eklendi.", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(kampanyaEkle.this,MainActivity.class));

            }
        });

        btnVazgec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(kampanyaEkle.this, MainActivity.class));
                finish();
            }
        });

    }
}
