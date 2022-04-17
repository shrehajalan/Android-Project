package com.example.vhsince81;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.CallSuper;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vhsince81.DBPOJOpackage.CARTITEMDTO;
import com.example.vhsince81.DBPOJOpackage.Database_Room;
import com.example.vhsince81.POJOpackage.DataDTO;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SelectedProductItemDisplay extends AppCompatActivity implements RadioButtonListener{
    DataDTO data;
    CARTITEMDTO cartdata;
    String category_name;
    ImageView productItemImage;
    TextView productDesc;
    TextView productValue;

//    TextView productItemColorText;
    TextView productItemSizeText;
    TextView productItemPrice;
    TextView productItemPriceText;
//    TextView product_socks_type;

    ArrayAdapter<String> arrayAdapter;

    RecyclerView rv1;
    RecyclerView rv2;
    RecyclerView rv3;
    RecyclerView rv4;
    RecyclerAdapter adapter_socks1;
    RecyclerAdapter adapter_socks3;
    RecyclerAdapter adapter_solid;
    RecyclerAdapter adapter_print;
    RecyclerAdapter adapter2;

    LinearLayout ll2;
    LinearLayout ll3;
    LinearLayout ll4;
    LinearLayout ll5;

    Button plus;
    Button minus;
    TextView quantity;
    Button add_to_cart;

    int id_value = -1;
    int count = 0;
    int selection;
    int size_socks_types = 2;
    int size_color_types = 2;
    int flag_price = 0;


    String[] SPINNERLIST_SOCKS;
    String[] SPINNERLIST_COLORS;
    String selected_type_socks;
    String selected_type_colors;
    String back_move;
    String selected_radiobtn;
    boolean boolean_cart = true;
    boolean boolean_add_to_cart = true;



    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.selected_product_item_display);

        Toolbar my_toolbar = (Toolbar)findViewById(R.id.toolbar_selectedProductItem);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView image = (ImageView)findViewById(R.id.cartImage_actionBar);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(boolean_cart)
                {
                    Intent intent = new Intent(SelectedProductItemDisplay.this, View_Cart_Items.class);
                    startActivity(intent);
                    boolean_cart = false;
                }

            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        category_name = intent.getStringExtra("SELECTED_CATEGORY_NAME");
        data = (DataDTO) bundle.get("SELECTED_VHITEM");
        cartdata = (CARTITEMDTO) bundle.get("SELCTED_DB_ITEM_FOR_UPDATE");
        back_move = (String) intent.getStringExtra("BACK_MOVE");
       // productItemPrice.setText(null);

        initViews();
        if (category_name.equals("SOCKS")) {
            java_code_for_socks();
        } else if (category_name.equals("VH-WOMEN_LOUNGES")) {
            java_code_for_lounges();
        } else { }

    }

    private void java_code_for_socks()
    {

        if(data.getAvailbleColorsPackOf3().size()==0)
            size_socks_types = 1;

        if(size_socks_types == 1) {
            SPINNERLIST_SOCKS = new String[size_socks_types];
            SPINNERLIST_SOCKS[0] = "PACK OF 1";
        }
        else{
            SPINNERLIST_SOCKS = new String[size_socks_types];
            SPINNERLIST_SOCKS[0] = "PACK OF 1";
            SPINNERLIST_SOCKS[1] = "PACK OF 3";
        }


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SPINNERLIST_SOCKS);
        MaterialBetterSpinner betterSpinner = (MaterialBetterSpinner)findViewById(R.id.android_spinner_socks);
        betterSpinner.setAdapter(arrayAdapter);

        betterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_type_socks = parent.getItemAtPosition(position).toString();
                choice();
            }
        });
        if(back_move.equals("1"))
        {
            if(cartdata.getPackOf1().equals(null))
            {
                selected_type_socks = "PACK of 3";
                selected_radiobtn = cartdata.getPackOf3();
            }
            else
            {
                selected_type_socks = "PACK OF 1";
                selected_radiobtn = cartdata.getPackOf1();
            }
            choice();
        }
        ll2.setVisibility(View.VISIBLE);
        setValues();
    }

    private void java_code_for_lounges()
    {
        if(data.getAvailbleColors().size()!=0 && data.getAvailblePrintColors().size()!=0)
        {
            size_color_types = 2;
            SPINNERLIST_COLORS = new String[size_color_types];
            SPINNERLIST_COLORS[0] = "SOLID COLORS";
            SPINNERLIST_COLORS[1] = "PRINT COLORS";
        }
        else if(data.getAvailbleColors().size() == 0)
        {
            size_color_types = 1;

            SPINNERLIST_COLORS = new String[size_color_types];
            SPINNERLIST_COLORS[0] = "PRINT COLORS";
        }
        else {
            size_color_types = 1;
            SPINNERLIST_COLORS = new String[size_color_types];
            SPINNERLIST_COLORS[0] = "SOLID COLORS";
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SPINNERLIST_COLORS);
        MaterialBetterSpinner betterSpinner = (MaterialBetterSpinner)findViewById(R.id.android_spinner_color);
        betterSpinner.setAdapter(arrayAdapter);
        if(back_move.equals("1"))
        {
            if(cartdata.getSolidColor().equals(null))
            {
                selected_type_colors = "PRINT COLORS";
                selected_radiobtn = cartdata.getPrintColor();
            }
            else
            {
                selected_type_colors = "PRINT COLORS";
                selected_radiobtn = cartdata.getSolidColor();
            }
        choice();
        }
        betterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_type_colors = parent.getItemAtPosition(position).toString();
                choice();
            }
        });
        ll4.setVisibility(View.VISIBLE);
        ll5.setVisibility(View.VISIBLE);
        setValues();
    }

    private void setValues() {

        String uri = data.getSrc();

        /*Placing image Style ID Text along with Style ID*/

        productItemImage.setImageResource(this.getResources().getIdentifier(uri, null, this.getPackageName()));
        productDesc.setText("STYLE ID \n");
        productValue.setText(data.getStyleId());

        /*Recycler view set depending upon the products */


        /*logic for price*/
        productItemPriceText.setText("PRICE PER QUANTITY");
        if(back_move.equals("1"))
        {
            productItemPrice.setText(cartdata.getPricePerQuantity());
        }

        /*Logic for + and - of items*/
        if(back_move.equals("1"))
        {
            quantity.setText(cartdata.getQuantity());
        }
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                quantity.setText(Integer.toString(count));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 0)
                    count--;
                else
                    count = 0;
                quantity.setText(Integer.toString(count));
            }
        });


        /*Logic for add to cart*/
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (boolean_add_to_cart) {

                    //  !((productItemPrice.getText().toString()).equals(null))
                    if (Integer.parseInt(quantity.getText().toString()) != 0) {

                        CARTITEMDTO object = new CARTITEMDTO();

                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        String date = df.format(Calendar.getInstance().getTime());
                        object.setEntryDate(date);
                        object.setProductCategory(category_name);

                        SharedPreferences prfs = getSharedPreferences("login", MODE_PRIVATE);
                        String login_username = prfs.getString("username", "");
                        object.setUsername(login_username);
                        object.setStatus("0");
                        object.setPreviousStatus("0");


                        object.setPricePerQuantity(productItemPrice.getText().toString());
                        object.setQuantity(quantity.getText().toString());
                        object.setStyleId(productValue.getText().toString());

                        if (adapter_print == null)
                            object.setPrintColor("null");
                        else
                            object.setPrintColor(adapter_print.selected_radio_btn);
                        if (adapter_solid == null)
                            object.setSolidColor("null");
                        else
                            object.setSolidColor(adapter_solid.selected_radio_btn);

                        //object.setSolidColor(adapter_solid.selected_radio_btn);
                        if (adapter_socks1 == null)
                            object.setPackOf1("null");
                        else
                            object.setPackOf1(adapter_socks1.selected_radio_btn);

                        if (adapter_socks3 == null)
                            object.setPackOf3("null");
                        else
                            object.setPackOf3(adapter_socks3.selected_radio_btn);

                        if (adapter2 == null)
                            object.setSize("null");
                        else
                            object.setSize(adapter2.selected_radio_btn);

                        //loc to call inser function to insert object intoths db Database_Room

                        Database_Room db = Database_Room.getInstance(getApplicationContext());
                        db.getCartDao().insert(object);
                        boolean_add_to_cart = false;

                        Toast.makeText(getApplicationContext(), "Item added to cart", Snackbar.LENGTH_LONG).show();
                        // Intent intent = new Intent(SelectedProductItemDisplay.this, View_Cart_Items.class);
                        // startActivity(intent);
                    } else
                        Toast.makeText(getApplicationContext(), "Some fiels are still empty.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    private void initViews()
    {
        productItemImage = (ImageView) findViewById(R.id.product_imageview);
        productDesc = (TextView) findViewById(R.id.product_desc);
        productValue = (TextView) findViewById(R.id.product_value);
      //  productItemColorText = (TextView) findViewById(R.id.product_color_text);
        productItemSizeText = (TextView) findViewById(R.id.product_size_text);
        productItemPriceText =(TextView) findViewById(R.id.product_price_heading);
        productItemPrice = (TextView) findViewById(R.id.product_price);
        //product_socks_type = (TextView) findViewById(R.id.product_socks_type);

        plus = (Button)findViewById(R.id.add_btn);
        minus = (Button)findViewById(R.id.minus_btn);
        quantity = (TextView)findViewById(R.id.number_btn);
        add_to_cart = (Button)findViewById(R.id.add_2_cart);
        rv1 = (RecyclerView)findViewById(R.id.product_rv1);
        rv3 = (RecyclerView)findViewById(R.id.product_rv3);
        rv4 = (RecyclerView)findViewById(R.id.product_rv4);
        ll2 = (LinearLayout)findViewById(R.id.product_ll2);
        ll4 = (LinearLayout)findViewById(R.id.product_ll4);
        ll5 = (LinearLayout)findViewById(R.id.product_ll5);
        rv1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv3.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv4.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

    }

    private List<String> objectListToStringList(List<Object> l_object)
    {
        List<String> l_string = new ArrayList<String>();
       for(int i = 0; i < l_object.size(); i++)
           l_string.add(l_object.get(i).toString());

       return l_string;
    }

    private void choice()
    {
        if (category_name.equals("SOCKS"))
        {
            if(selected_type_socks.equals("PACK OF 1"))
            {
                //        product_socks_type.setText("PACK OF 1");
                adapter_socks1 = new RecyclerAdapter(data.getAvailbleColorsPackOf1(),SelectedProductItemDisplay.this, SelectedProductItemDisplay.this,"pack_of_1",back_move, selected_radiobtn);
                rv1.setAdapter(adapter_socks1);

            }
            if(selected_type_socks.equals("PACK OF 3"))
            {
                //        product_socks_type.setText("PACK OF 3");
                adapter_socks3 = new RecyclerAdapter(data.getAvailbleColorsPackOf3(),SelectedProductItemDisplay.this, SelectedProductItemDisplay.this,"pack_of_3",back_move, selected_radiobtn);
                rv1.setAdapter(adapter_socks3);
            }


        }
        else{

            if(selected_type_colors.equals("SOLID COLORS"))
            {
                //           productItemColorText.setText("Colors Available");
                adapter_solid = new RecyclerAdapter(objectListToStringList(data.getAvailbleColors()),SelectedProductItemDisplay.this, SelectedProductItemDisplay.this,"solid_colors",back_move, selected_radiobtn);
                rv3.setAdapter(adapter_solid);

            }
            if(selected_type_colors.equals("PRINT COLORS"))
            {
                //            productItemColorText.setText("Colors Available");
                adapter_print = new RecyclerAdapter(objectListToStringList(data.getAvailbleColors()),SelectedProductItemDisplay.this, SelectedProductItemDisplay.this,"print_colors",back_move,selected_radiobtn);
                rv3.setAdapter(adapter_print);

            }

            productItemSizeText.setText("Sizes Available");
            adapter2 = new RecyclerAdapter(objectListToStringList(data.getAvailbleSizes()),SelectedProductItemDisplay.this, SelectedProductItemDisplay.this,"sizes",back_move,selected_radiobtn);
            rv4.setAdapter(adapter2);


            /*code to be written*/
        }
    }


    void price_display()
    {
        boolean_add_to_cart = true;

        if(category_name.equals(("SOCKS")))
        {
            if(id_value != -1)
            {
                String s  = data.getPrices().get(selection).toString();
                productItemPrice.setText(s);


            }
        }
        else if((category_name.equals("VH_MEN_WEAR"))||category_name.equals("VH_WOMEN_LOUNGE_PANTS_AND_TEES"))
        {
            String s  = data.getPrices().get(selection).toString();
            productItemPrice.setText(s);

        }
        else
        {}

    }

    @Override
    public void onClick(int id, int selection) {
        this.id_value= id;
        this.selection = selection;
        price_display();
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
        boolean_cart = true;
        boolean_add_to_cart = true;
    }

}
