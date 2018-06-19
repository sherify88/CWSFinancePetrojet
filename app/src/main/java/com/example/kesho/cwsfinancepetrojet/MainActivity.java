package com.example.kesho.cwsfinancepetrojet;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    FragmentTransaction ft;
    FragmentManager fm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        DatabaseReference ordersDatabase = FirebaseDatabase.getInstance().getReference().child("responses");


        ordersDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.child("checkNumberForResposeTree").exists()) {
                    SharedPreferences sharedPreferences=MainActivity.this.getSharedPreferences("My Data",MODE_PRIVATE);
                    String name=  sharedPreferences.getString("name","no");
                    if (dataSnapshot.child("vendor").exists()){

                        try {
                            if (dataSnapshot.child("vendor").getValue().equals(name)&&!dataSnapshot.child("checkStatus").getValue().equals("استعجال الادارة")
                                   ) {
if ( dataSnapshot.child("effectOfChange").getValue().equals("vendor")){


                                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                Intent intent = new Intent();
                                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                                Notification notification = new Notification.Builder(MainActivity.this).
                                        setSound(soundUri).
                                        setTicker("TickerTitle").
                                        setContentTitle("رد petro علي الشيكات").
                                        setContentText("ميعاد استلام شيك رقم " + dataSnapshot.child("checkNumberForResposeTree").getValue().toString() + " هو " +
                                                dataSnapshot.child("response").getValue().toString())
                                        .setSmallIcon(R.mipmap.pinterest_petro).setColor(Color.RED).
                                                setContentIntent(pendingIntent).getNotification();
                                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                notificationManager.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE), notification);
                            }}
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //Toast.makeText(MainActivity.this,dataSnapshot.child("checkNumberForResposeTree").getValue().toString(),Toast.LENGTH_SHORT).show();
                }
                    }


            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {



            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SignIn fragment = new SignIn();
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container,fragment );
        ft.commit();
    }

}
