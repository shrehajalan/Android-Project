package com.example.vhsince81;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vhsince81.POJOpackage.DataDTO;
import com.example.vhsince81.POJOpackage.PRODUCTSDTO;
import com.example.vhsince81.POJOpackage.PojoDTO;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class CategoryListDisplay extends AppCompatActivity implements VHItemListener{

    List<DataDTO> category_list;
    DataDTO single_item;
    List<DataDTO> single_item_list = new ArrayList<DataDTO>();
    String selected_cat;
    boolean clicked = true;
    boolean boolean_itemclicked = true;
    int flag = 0;


    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.categorylistdisplay);

        Toolbar my_toolbar = (Toolbar)findViewById(R.id.toolbar_categoryListDisplay);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView image = (ImageView)findViewById(R.id.cartImage_actionBar);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clicked)
                {
                    Intent intent = new Intent(CategoryListDisplay.this, View_Cart_Items.class);
                    startActivity(intent);
                    clicked = false;
                }

            }
        });

        Intent intent = getIntent();
        selected_cat = intent.getStringExtra("SELECTED_CAT");


        final RecyclerView recyclerView = findViewById(R.id.recyclerView_display);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        VHViewModel viewModel = null;
        viewModel = ViewModelProviders.of(this).get(VHViewModel.class);
        LiveData<PojoDTO> dtoObject = viewModel.getCategoryList_VM();
        List<PRODUCTSDTO> list_products= dtoObject.getValue().getPRODUCTS();
        for(int i = 0; i < list_products.size();i++)
        {
            if(list_products.get(i).getTitle().equals(selected_cat))
            {
                category_list = list_products.get(i).getData();
                break;

            }
        }

        final VHListAdapter adapter = new VHListAdapter(category_list,CategoryListDisplay.this,  this, boolean_itemclicked, flag);
        recyclerView.setAdapter(adapter);


        final AutoCompleteTextView auto_text_view = findViewById(R.id.auto_complete_text_view);
        List<String> style_id = autoCompleteTextViewFunc();
        ArrayAdapter<String> adapter_auto = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,style_id);
        auto_text_view.setAdapter(adapter_auto);
        auto_text_view.setDropDownBackgroundDrawable(new ColorDrawable(this.getResources().getColor(R.color.dark_grey)));

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = auto_text_view.getText().toString();
                for(int j = 0; j < category_list.size(); j++)
                {
                    if(str.equals(category_list.get(j).getStyleId()))
                    {
                        single_item = category_list.get(j);
                        single_item_list.clear();
                        single_item_list.add(single_item);
                        VHListAdapter adapter_new = new VHListAdapter(single_item_list,CategoryListDisplay.this,CategoryListDisplay.this, boolean_itemclicked, flag);
                        recyclerView.setAdapter(adapter_new);
                        break;
                    }

                }
            }
        };
        auto_text_view.setOnItemClickListener(onItemClickListener);
        auto_text_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable){}
        });


      //  observeViewModel(viewModel);
    }


        private void observeViewModel(VHViewModel viewModel)
        {
            viewModel.getCategoryList_VM().observe(this, new Observer<PojoDTO>() {
                @Override
                public void onChanged(@Nullable PojoDTO pojoDTO) {
                    if(pojoDTO != null)
                    {
                      //  VHListAdapter.setVHList(pojoDTO);
                    }

                }
            });

        }

        public List<String> autoCompleteTextViewFunc()
        {
            List<String> style_id =new ArrayList<>();
            for(int i = 0; i < category_list.size(); i++)
            {
                style_id.add(category_list.get(i).getStyleId());
            }
            return style_id;

        }

    @Override
    public void onVHItemClick(int position) {

        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, SelectedProductItemDisplay.class);

        bundle.putSerializable("SELECTED_VHITEM", category_list.get(position));
        bundle.putSerializable("SELCTED_DB_ITEM_FOR_UPDATE",null);
        intent.putExtra("SELECTED_CATEGORY_NAME",selected_cat);
        intent.putExtra("BACK_MOVE","0");
        intent.putExtras(bundle);
        startActivity(intent);
        flag = 1;

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @CallSuper
    protected void onResume()
    {
        super.onResume();
        clicked = true;
        boolean_itemclicked = true;
    }


}

