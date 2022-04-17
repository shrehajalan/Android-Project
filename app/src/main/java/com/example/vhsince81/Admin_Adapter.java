package com.example.vhsince81;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vhsince81.DBPOJOpackage.CARTITEMDTO;

import java.util.List;

public class Admin_Adapter extends RecyclerView.Adapter<Admin_Adapter.OrderViewHolder>
{
    List<CARTITEMDTO> new_orderlist;
    Context context;
    AdminPage_OrderListener listener;
    boolean boolean_orderitemclicked = true;
    boolean boolean_admin_viewholderbtn_clicked = true;
    String type;


    @NonNull
    @Override
    public Admin_Adapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_card_view, null);
        Admin_Adapter.OrderViewHolder oViewHolder = new Admin_Adapter.OrderViewHolder(v);
        return oViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_Adapter.OrderViewHolder orderViewHolder, int i) {

        final int pos = i;

        String username = new_orderlist.get(i).getUsername();
        orderViewHolder.cardview_textview1.setText("Order received from "+username);
        orderViewHolder.cardview_textview2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public
                    void onClick(View view) {

                        if(boolean_orderitemclicked)
                        {

                            listener.orderDetails(pos, type);
                            boolean_orderitemclicked = false;
                        }

                    }
                });
        orderViewHolder.admin_viewholder_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public
                    void onClick(View view) {
                        if(boolean_admin_viewholderbtn_clicked)
                        {
                            if(type.equals("received"))
                                listener.onOrderPlacedClick(pos);
                            else if(type.equals("packed"))
                                listener.onOrderPlacedClick(pos);
                            else
                                listener.onOrderDeletedClick(pos);
                            boolean_admin_viewholderbtn_clicked = false;
                        }

                    }
                });

    }

    @Override
    public int getItemCount() {
        return new_orderlist.size();
    }

    public Admin_Adapter(List<CARTITEMDTO> new_orderlist, Context context, AdminPage_OrderListener listener, String type)
    {
        this.context = context;
        this.new_orderlist = new_orderlist;
        this.listener = listener;
        this.type = type;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView cardview_textview1 ;
        TextView cardview_textview2;
        Button admin_viewholder_btn;

        OrderViewHolder(View itemView) {
            super(itemView);

            cardview_textview1 = (TextView) itemView.findViewById(R.id.cardview_textview1);
            cardview_textview2 = (TextView) itemView.findViewById(R.id.cardview_textview2);
            admin_viewholder_btn = (Button) itemView.findViewById(R.id.admin_orderpacked_btn);

        }


    }
    @CallSuper
    protected void onResume()
    {
        boolean_admin_viewholderbtn_clicked = true;
    }
}
