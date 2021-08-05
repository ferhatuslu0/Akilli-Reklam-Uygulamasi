package com.example.yazlab2projeson;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Listele extends AppCompatActivity implements LocationListener {

    ArrayList<firma> firmalar;
    ArrayList<firma> mesafeFirmalar;
    RecyclerView recyView;
    Button geri;


    FirebaseDatabase database3 = FirebaseDatabase.getInstance();
    DatabaseReference data5 = database3.getReference();
    DatabaseReference myRef;
    RecyclerView.LayoutManager mLay;
    SearchView searc;
    EditText xEnlem, yBoylam,mesafeGir;
    LocationManager locationManager;
    String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kampanya_listele);

        geri = (Button) findViewById(R.id.btnGeri);
        recyView = findViewById(R.id.gridDoldur);
        firmalar = new ArrayList<>();
        mesafeFirmalar = new ArrayList<>();

        mesafeGir=(EditText) findViewById(R.id.mesafeGir);
        myRef = FirebaseDatabase.getInstance().getReference().child("firma");



        mLay = new LinearLayoutManager(this);
        recyView.setLayoutManager(mLay);
        searc = (SearchView) findViewById(R.id.kategori);
        xEnlem = (EditText) findViewById(R.id.xLok);
        yBoylam = (EditText) findViewById(R.id.yLok);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);



        data5.child("firma").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    firmalar.add(postSnapshot.getValue(firma.class));
                }
                RccAdapter rc = new RccAdapter(firmalar);
                recyView.setAdapter(rc);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Listele.this, MainActivity.class));
                finish();
            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            onLocationChanged(location);
        } else {
            xEnlem.setText("Not Avaliable");
            yBoylam.setText("Not Avaliable");
        }


    }

    double mesafeGir2=0;
    @Override
    protected void onStart() {
        super.onStart();

        if (myRef != null) {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        firmalar = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            firmalar.add(ds.getValue(firma.class));
                        }
                        RccAdapter rccAdapter = new RccAdapter(firmalar);
                        recyView.setAdapter(rccAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //  Toast.makeText(MainActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (searc != null) {

            searc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    arama(s);
                    return true;
                }
            });
        }
    }

    private void arama(String str) {
        mesafeFirmalar.clear();
        ArrayList<firma> myList = new ArrayList<>();

        if (mesafeGir == null || mesafeGir.getText().toString().equals("")) mesafeGir2 = 99999999;
        else mesafeGir2 = Double.parseDouble(mesafeGir.getText().toString());

        for(int i=0; i<firmalar.size();i++)
        {

            if (firmalar.get(i).getKategori().toLowerCase().contains(str.toLowerCase()) || firmalar.get(i).getFirmaAdi().toLowerCase().contains (str.toLowerCase ()) )
            {
                myList.add(firmalar.get(i));

                double dist = distance(Double.parseDouble(xEnlem.getText().toString()),Double.parseDouble(yBoylam.getText().toString()),Double.parseDouble(firmalar.get(i).getxLokasyon()),Double.parseDouble(firmalar.get(i).getyLokasyon()));

                System.out.println(dist);

                if(mesafeGir2<dist)
                {
                    myList.remove(firmalar.get(i));

                }
                if (mesafeGir2 > dist)
                {
                    mesafeFirmalar.add(firmalar.get(i));
                }


            }

        }


        RccAdapter rccAdapter = new RccAdapter(myList);
        recyView.setAdapter(rccAdapter);
        Natification();

    }


    @Override
    public void onLocationChanged(Location location) {

        double  lat =  Math.floor(location.getLatitude()*1000)/1000;
        double lon =  Math.floor(location.getLongitude()*1000)/1000;

        xEnlem.setText(String.valueOf(lat));
        yBoylam.setText(String.valueOf(lon));


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enable Provider", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disable Provider", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(provider, 100, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist=dist/1000;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    private void  Natification(){

        Notification.InboxStyle style= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            style = new Notification.InboxStyle();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            style.setBigContentTitle("Yak?nlarda Gitmek istedi?iniz yerler var.");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            for (int i=0;i<mesafeFirmalar.size();i++)
            {

                style.addLine(mesafeFirmalar.get(i).getFirmaAdi());
            }

        }

        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Notification.Builder builder=new Notification.Builder(Listele.this);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setStyle(style);
        }

        Intent intent=new Intent(Listele.this,RccAdapter.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(Listele.this,1,intent,0);
        builder.setContentIntent(pendingIntent);

        Notification notification=builder.getNotification();
        manager.notify(1,notification);
    }

}
