package com.example.vhsince81;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vhsince81.DBPOJOpackage.CARTITEMDTO;
import com.example.vhsince81.DBPOJOpackage.Database_Room;
import com.example.vhsince81.POJOpackage.DataDTO;
import com.example.vhsince81.POJOpackage.PRODUCTSDTO;
import com.example.vhsince81.POJOpackage.PojoDTO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.UUID;

public class View_Cart_Items extends AppCompatActivity implements CartListRefresh{



    RecyclerView rv;
    Button btn;
    CartListAdapter adapter;
    List<CARTITEMDTO> cart_items;
    String move_back;
    DatabaseReference fbCartItems,fbOrderId;
    String uniqueID;
    boolean boolean_back_homepage = true;

    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.view_cart_items_layout);

        Toolbar my_toolbar = (Toolbar)findViewById(R.id.toolbar_view_cart_items);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rv = (RecyclerView)findViewById(R.id.cart_rv);
        btn = (Button)findViewById(R.id.place_order_btn);


        Database_Room db = Database_Room.getInstance(getApplicationContext());
        cart_items = db.getCartDao().fetchAll();

        adapter = new CartListAdapter(cart_items,View_Cart_Items.this, View_Cart_Items.this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertbox();
            }
        });
    }

    @Override
    public void cart_delete(CARTITEMDTO item) {

        Database_Room db = Database_Room.getInstance(getApplicationContext());
        db.getCartDao().delete(item);
        cart_items = db.getCartDao().fetchAll();
        adapter.autocart_list_change(cart_items);
    }

    @Override
    public void cart_update(CARTITEMDTO item) {
        move_back = "1";
        Database_Room db = Database_Room.getInstance(getApplicationContext());
        CARTITEMDTO selected_item_for_update = db.getCartDao().getCartItemBy(item.getStyleId());
        db.getCartDao().delete(selected_item_for_update);
        DataDTO data = getList(selected_item_for_update);

        Intent intent = new Intent(this, SelectedProductItemDisplay.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("SELECTED_VHITEM", data);
        bundle.putSerializable("SELCTED_DB_ITEM_FOR_UPDATE",selected_item_for_update);
        intent.putExtras(bundle);
        intent.putExtra("BACK_MOVE",move_back);
        intent.putExtra("SELECTED_CATEGORY_NAME",selected_item_for_update.getProductCategory());
        startActivity(intent);
    }

    @Override
    public void cart_plus_quant(CARTITEMDTO item) {

        int count = Integer.parseInt(item.getQuantity()) + 1;
        item.setQuantity(String.valueOf(count));
        Database_Room db = Database_Room.getInstance(getApplicationContext());
        db.getCartDao().update(item);
        cart_items = db.getCartDao().fetchAll();
        adapter.autocart_list_change(cart_items);
        //Snackbar.make(btn, "Successfully changed the quantity", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void cart_minus_quant(CARTITEMDTO item) {

            int count = Integer.parseInt(item.getQuantity()) - 1;
            if(count == 0) {
                cart_delete(item);
                Toast.makeText(getApplicationContext(),"Item removed from cart",Toast.LENGTH_LONG).show();
            }
            else
            {
                item.setQuantity(String.valueOf(count));
                Database_Room db = Database_Room.getInstance(getApplicationContext());
                db.getCartDao().update(item);
                cart_items = db.getCartDao().fetchAll();
                adapter.autocart_list_change(cart_items);
               // Snackbar.make(btn, "Successfully changed the quantity", Snackbar.LENGTH_SHORT).show();
            }
    }

    public DataDTO getList(CARTITEMDTO selected_item_for_update)
    {
        int j;
        List<DataDTO> category_list = null;
        VHViewModel viewModel = null;
        viewModel = ViewModelProviders.of(this).get(VHViewModel.class);
        LiveData<PojoDTO> dtoObject = viewModel.getCategoryList_VM();
        List<PRODUCTSDTO> list_products= dtoObject.getValue().getPRODUCTS();
        for(int i = 0; i < list_products.size();i++)
        {
            if(list_products.get(i).getTitle().equals(selected_item_for_update.getProductCategory()))
            {
                category_list = list_products.get(i).getData();
                break;

            }
        }

        for( j = 0; j < category_list.size(); j++)
        {
            if((category_list.get(j).getStyleId()).equals(selected_item_for_update.getStyleId()))
                break;

        }
        return(category_list.get(j));
    }

    public void addCartItemsToFirebase()
    {
        Database_Room db = Database_Room.getInstance(getApplicationContext());
        List<CARTITEMDTO> finalCartItems;
        finalCartItems = db.getCartDao().fetchAll();

        if(finalCartItems.size() > 0 )
        {
            uniqueID = UUID.randomUUID().toString();

            fbOrderId = FirebaseDatabase.getInstance().getReference().child("Final Orders").child(uniqueID);
            fbOrderId.child("Status").setValue("0");
            fbCartItems = fbOrderId.child("List");
            for(CARTITEMDTO d : finalCartItems)
            {
                fbCartItems.push().setValue(d);
            }
        }
        db.getCartDao().deleteAll();
      //  adapter = null;
       // rv.setAdapter(adapter);

        setContentView(R.layout.empty_cart_page);
        Button bt = (Button)findViewById(R.id.cart_to_home_page);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(boolean_back_homepage)
                {
                    Intent intent = new Intent(View_Cart_Items.this,HomePage.class);
                    startActivity(intent);
                    finish();
                    boolean_back_homepage = false;

                }

            }
        });


    }
    public void alertbox()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm dialog Box !");
        builder.setMessage("Confirm to proceed for final order");
        builder.setCancelable(true);
        builder.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addCartItemsToFirebase();
            }
        });
        builder.show();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

