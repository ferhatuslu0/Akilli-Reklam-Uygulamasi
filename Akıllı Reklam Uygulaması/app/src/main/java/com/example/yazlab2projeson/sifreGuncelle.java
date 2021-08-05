package com.example.yazlab2projeson;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sifreGuncelle extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextView textView;
    private Button  sifreGuncelle,geri;
    private FirebaseUser user;
    private EditText sifre,eskiSifre,mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.kullanici_guncelle);

        auth = FirebaseAuth.getInstance();
       textView= findViewById(R.id.guncelle);
        mail= findViewById(R.id.girisEmail);
        sifre= (EditText) findViewById(R.id.guncelleParola);
        eskiSifre=findViewById(R.id.girisParola);
       sifreGuncelle=(Button) findViewById(R.id.guncelleButton);
        geri=(Button) findViewById(R.id.geri);

        user = FirebaseAuth.getInstance().getCurrentUser();

        textView.setText("Yeni Şifre Girin, " + user.getEmail());

       // mail.setText(auth.getCurrentUser().getEmail());

        sifreGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    sifreDegis();
                    Toast.makeText(getApplicationContext(),"sifre Başarılı şekilde güncellendi",Toast.LENGTH_LONG).show();

            }


        });

        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(sifreGuncelle.this, MainActivity.class));
                finish();
            }
        });
    }

    private void sifreDegis() {user.updatePassword(sifre.getText().toString())
           .addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   auth.signOut();
                   Intent i=new Intent(getBaseContext(),MainActivity.class);
                   startActivity(i);
                   finish();
               }
           });


    }


}

