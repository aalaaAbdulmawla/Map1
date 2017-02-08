package com.example.dell.map1;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final ArrayList<com.firebase.client.DataSnapshot> points =  new ArrayList<com.firebase.client.DataSnapshot>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //   Firebase.setandroid
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://map1-ab0da.firebaseio.com/points");

        ref.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                int i = 0;
                for (com.firebase.client.DataSnapshot data: dataSnapshot.getChildren()) {
                    //Point name =  data.getValue(Point.class);
                    //System.out.println("enaaas" + data);

                    //points.add(data);
                    addToArrayList(data);
                    //System.out.println("i = " + i++ + " size = " + points.size());
                }
                if(points.size()==111){
                    com.firebase.client.DataSnapshot point= findSrc(31.20722670, 29.92458723);
                    System.out.println("Nearest point" + point.child("id").getValue());
                }
                else System.out.println("zeroooo");


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void addToArrayList(com.firebase.client.DataSnapshot data){
        points.add(data);
    }


    private com.firebase.client.DataSnapshot findSrc(double longitute, double latitute){
        double min = 0.0;
       ;
        com.firebase.client.DataSnapshot point = null;
        for (int i = 0; i < points.size(); i++){
            double x = 0.0;
            double y = 0.0;
            System.out.println("lAt num " +i+" "+ points.get(i).child("lat").getValue());
             x = (double)points.get(i).child("long").getValue();
             y=  (double)points.get(i).child("lat").getValue();
            if(x!=0.0 && y!= 0.0){
                  double dist = getDistance(longitute, latitute, x, y);

                 if (dist < min || min == 0.0) {
                    min = dist;
                point = points.get(i);
                }

            }

        }
        return point;
    }

    private double getDistance(double long1,double lat1,double long2,double lat2){
        Location locationA = new Location("point A");
        locationA.setLatitude(long1);
        locationA.setLongitude(lat1);
        Location locationB = new Location("point B");
        locationB.setLatitude(long2);
        locationB.setLongitude(lat2);
        return locationA.distanceTo(locationB) ;
    }
}