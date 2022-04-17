package com.example.vhsince81;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.vhsince81.POJOpackage.DataDTO;

import java.util.List;

public class VHListAdapter extends RecyclerView.Adapter<VHListAdapter.CategoryListViewHolder> {

    private Context context;
    List<DataDTO> category_list;

    private VHItemListener listener = null;
    boolean boolean_itemclicked;
    int flag;



    @NonNull
    @Override
    public CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_vh, null);
        CategoryListViewHolder clViewHolder = new CategoryListViewHolder(v);
        return clViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListViewHolder categoryListViewHolder,final int i) {

        String uri = category_list.get(i).getSrc().trim();  // where myresource (without the extension) is the file
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());


        Drawable res = context.getResources().getDrawable(imageResource);
        categoryListViewHolder.cat_list_pic.setImageDrawable(res);
        categoryListViewHolder.card_view_display.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public
            void onClick(View view) {
                if (flag == 1)
                    boolean_itemclicked = true;
                if(true)
                {
                    listener.onVHItemClick(i);
                    boolean_itemclicked = false;
                }

            }
        });

  /*  Glide.with(context)
                .load(R.drawable.img1)
                .into(categoryListViewHolder.cat_list_pic);*/

    }

    private int getImage(String s) {
        int drawableresourceid = context.getResources().getIdentifier(s, "drawable", context.getPackageName());
        return drawableresourceid;
    }


    @Override
    public int getItemCount() {

        return category_list.size();
    }

    public VHListAdapter(List<DataDTO> category_list, Context context, VHItemListener listener, boolean boolean_itemclicked, int flag) {
        this.context = context;
        this.category_list = category_list;
        this.listener = listener;
        this.boolean_itemclicked = boolean_itemclicked;
        this.flag = flag;
    }



    public static class CategoryListViewHolder extends RecyclerView.ViewHolder {

        ImageView cat_list_pic;
        CardView card_view_display;

        CategoryListViewHolder(View itemView) {
             super(itemView);
            //cv = (CardView)itemView.findViewById(R.id.card_view_display);
            cat_list_pic = (ImageView) itemView.findViewById(R.id.image_view_display);
            card_view_display=(CardView)itemView.findViewById(R.id.card_view_display);

        }


    }
    @CallSuper
    protected void onResume()
    {
        boolean_itemclicked = true;
    }
}
