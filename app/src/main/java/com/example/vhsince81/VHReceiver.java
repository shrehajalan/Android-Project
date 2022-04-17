package com.example.vhsince81;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class VHReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null)
        {
            if(netInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                Toast.makeText(context," Internet Connection through mobile", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context," Internet Connection through wifi", Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show();
        }


    }
}
