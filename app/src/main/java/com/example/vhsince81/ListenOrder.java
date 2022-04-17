package com.example.vhsince81;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;


import com.example.vhsince81.DBPOJOpackage.CARTITEMDTO;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Random;


import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class ListenOrder extends Service implements ChildEventListener {

    FirebaseDatabase db;
    DatabaseReference orders;

    public void onCreate() {
        super.onCreate();
        db = FirebaseDatabase.getInstance();
        orders = db.getReference("Final Orders");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        orders.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        String username = null;

        String st = dataSnapshot.child("Status").getValue().toString();
        if (st.equals("0")) {
            DataSnapshot listsnapshot = dataSnapshot.child("List");
            for (DataSnapshot dt : listsnapshot.getChildren()) {
                username = dt.child("username").getValue().toString();
                break;
            }
            shownotification(username);
            CARTITEMDTO cartobject = listsnapshot.getValue(CARTITEMDTO.class);
            AdminHomePage.list_status0.add(cartobject);
            AdminHomePage.uuidlist_status0.add(dataSnapshot.getRef().getKey());
            orders.child(dataSnapshot.getKey()).child("Status").setValue("1");

        }


    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        change_in_adapter_list(dataSnapshot);
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    private void shownotification(String username) {

        int flag = 0;
        NotificationCompat.Builder builder;
        Intent intent = new Intent(getBaseContext(), AdminHomePage.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MyNotification", "Notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.enableLights(true);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            flag = 1;
        }
        if (flag == 0)
            builder = new NotificationCompat.Builder(getBaseContext());
        else
            builder = new NotificationCompat.Builder(getBaseContext(), "MyNotification");

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setAutoCancel(true)
                .setSmallIcon(R.drawable.icon_for_notification)
                .setContentTitle("New Order")
                .setSound(uri)
                .setContentText("Order from " + username)
                .setContentIntent(contentIntent);


        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        ;
        int randInt = new Random().nextInt(999 - 1) + 1;
        manager.notify(randInt, builder.build());

    }

    void change_in_adapter_list(DataSnapshot dataSnapshot) {
        String status,prev_status;

        DataSnapshot listsnapshot = dataSnapshot.child("List");

        for (DataSnapshot dt : listsnapshot.getChildren())
        {

            status = dt.child("status").getValue().toString();
            prev_status = dt.child("previousStatus").getValue().toString();

            if (status.equals("1"))
            {
                AdminHomePage.list_status1.add((CARTITEMDTO) listsnapshot.getValue());
                AdminHomePage.uuidlist_status1.add(dataSnapshot.getRef().getKey());
            }

            if (status.equals("2"))
            {
                AdminHomePage.list_status2.add((CARTITEMDTO) listsnapshot.getValue());
                AdminHomePage.uuidlist_status2.add(dataSnapshot.getRef().getKey());

            }

            if (prev_status.equals("0"))
            {
                AdminHomePage.list_status0.remove(listsnapshot.getValue());
                AdminHomePage.uuidlist_status0.remove(dataSnapshot.getRef().getKey());
            }

            if (prev_status.equals("1"))
            {
                AdminHomePage.list_status1.remove(listsnapshot.getValue());
                AdminHomePage.uuidlist_status1.remove(dataSnapshot.getRef().getKey());
            }

            if (prev_status.equals("2"))
            {
                AdminHomePage.list_status2.remove(listsnapshot.getValue());
                AdminHomePage.uuidlist_status2.remove(dataSnapshot.getRef().getKey());
            }

            break;


        }
    }

}















