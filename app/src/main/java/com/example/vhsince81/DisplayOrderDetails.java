package com.example.vhsince81;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.vhsince81.DBPOJOpackage.CARTITEMDTO;

public class DisplayOrderDetails extends AppCompatActivity
{
    CARTITEMDTO order;
    String pack, color, size, styleID, quantity, username, entryDate;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        order = (CARTITEMDTO) bundle.getSerializable("SELECTED_ORDER_DETAILS");
        TextView tview = (TextView)findViewById(R.id.textview_order_details);

        styleID = order.getStyleId();
        quantity = order.getQuantity();
        username = order.getUsername().toUpperCase();
        entryDate = order.getEntryDate();

        while(true) {
            if (!(order.getPackOf1().equals(""))) {
                pack = order.getPackOf1();
                color = order.getSolidColor();
                break;
            }
            if (!(order.getPackOf3().equals(""))) {
                pack = order.getPackOf3();
                color = order.getSolidColor();
                break;
            }
        }

        color = order.getSolidColor();
        size = order.getSize();


        if(!(pack.equals("")))
            tview.setText("Order from "+username+" on "+entryDate+"/nStyleID : "+styleID+"/nType : "+pack+"/nColor : "+color+"/nQuantity : "+quantity);
        else
            tview.setText("Order from "+username+" on "+entryDate+"/nStyleID : "+styleID+"/nColor : "+color+"/nSize : "+size+"/nQuantity : "+quantity);


    }
}
