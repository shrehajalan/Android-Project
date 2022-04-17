package com.example.vhsince81;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.example.vhsince81.DBPOJOpackage.Database_Room;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartItemViewHolder> {

    Context context;
    List<CARTITEMDTO> lst;
    CartListRefresh listener;
    private LayoutInflater inflater;
    boolean boolean_update = true;

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.each_cart_item, null);
        CartItemViewHolder holder = new CartItemViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final CartItemViewHolder cartItemViewHolder, int i)
    {
        final CARTITEMDTO j = lst.get(i);
        String Id = j.getStyleId();
        String price =j.getPricePerQuantity();
        String quantity = j.getQuantity();
        cartItemViewHolder.cart_price.setText(price);
        cartItemViewHolder.cart_style_id.setText(Id);
        cartItemViewHolder.cart_quantity.setText(quantity);
        cartItemViewHolder.cart_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cart_delete(j);
            }
        });
        cartItemViewHolder.cart_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(boolean_update)
                {
                    listener.cart_update(j);
                    boolean_update = false;

                }

            }
        });
        cartItemViewHolder.cart_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cart_plus_quant(j);

            }
        });
        cartItemViewHolder.cart_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cart_minus_quant(j);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    CartListAdapter(List<CARTITEMDTO> list, Context context, CartListRefresh listener)
    {
        this.context = context;
        this.lst = list;
        this.listener = listener;
    }

    public static class CartItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView cart_style_id ;
        TextView cart_price;
        TextView cart_quantity;
        Button cart_update_btn;
        Button cart_delete_btn;
        CardView card_view_display;
        Button cart_plus;
        Button cart_minus;
        CardView card_view_display2;

        CartItemViewHolder(View cartItemView) {
            super(cartItemView);
            card_view_display2 = (CardView)cartItemView.findViewById(R.id.cart_card);
            cart_style_id = (TextView)cartItemView.findViewById(R.id.cart_style_id);
            cart_price = (TextView)cartItemView.findViewById(R.id.cart_price);
            cart_quantity = (TextView)cartItemView.findViewById(R.id.cart_number_btn) ;
            cart_update_btn = (Button)cartItemView.findViewById(R.id.cart_update);
            cart_delete_btn = (Button)cartItemView.findViewById(R.id.cart_delete);
            card_view_display=(CardView)itemView.findViewById(R.id.cart_card);
            cart_plus = (Button)cartItemView.findViewById(R.id.cart_add_btn);
            cart_minus = (Button)cartItemView.findViewById(R.id.cart_minus_btn);

        }


    }
    public void autocart_list_change(List<CARTITEMDTO> list)
    {
        this.lst = list;
        notifyDataSetChanged();
    }

}
