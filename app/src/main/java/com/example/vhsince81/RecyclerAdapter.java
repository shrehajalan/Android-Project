package com.example.vhsince81;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ListViewHolder> {


    private String rv_selection;
    private RadioButtonListener listener = null;
    private List<String> lst;
    private Context context;
    String selected_radio_btn;
    String back_move;
    String passed_radiobtn;

    @NonNull
    @Override
    public RecyclerAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.radiogroup_layout, null);
        ListViewHolder lvh = new ListViewHolder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ListViewHolder listViewHolder, int i) {
        int flag = 0;
        for(int k = 0; k < lst.size(); k++)
        {
            LayoutInflater inflate_obj = LayoutInflater.from(context);
            View v1 = inflate_obj.inflate(R.layout.radio_layout,null);

            final RadioButton rb = (RadioButton)v1.findViewById(R.id.radioButton_btn);
            rb.setId(k);
            rb.setText(lst.get(k));
            final int val_id = rb.getId();

            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selected_radio_btn = rb.getText().toString();
                    if(rv_selection.equals("pack_of_1"))
                        listener.onClick(val_id,0);
                    else if(rv_selection.equals("pack_of_3"))
                            listener.onClick(val_id,1);
                    else if(rv_selection.equals("solid_colors"))
                        listener.onClick(val_id,1);
                    else if(rv_selection.equals("print_colors"))
                        listener.onClick(val_id,1);
                    else{}

                }
            });

            if(rb.getParent() != null) {
                ((ViewGroup)rb.getParent()).removeView(rb);
            }
            listViewHolder.rg.addView(rb);
            if(back_move.equals("1") && flag == 0)
            {
                if(rb.getText().equals(passed_radiobtn))
                {
                    rb.setChecked(true);
                    flag = 1;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
       return 1;
    }

    RecyclerAdapter(List<String> lst, Context context, RadioButtonListener listener, String rv_selection, String back_move, String passed_radiobtn)
    {
        this.lst = lst;
        this.context = context;
        this.listener = listener;
        this.rv_selection = rv_selection;
        this.back_move = back_move;
        this.passed_radiobtn = passed_radiobtn;
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder
    {

        RadioGroup rg;
        ListViewHolder(View radiogroup_view)
        {
            super(radiogroup_view);
            rg = (RadioGroup)radiogroup_view.findViewById(R.id.radiogroup_btn);
        }
    }

}

