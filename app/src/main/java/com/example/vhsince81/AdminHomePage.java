package com.example.vhsince81;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vhsince81.DBPOJOpackage.CARTITEMDTO;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdminHomePage extends AppCompatActivity implements AdminPage_OrderListener {

    Button received_btn;
    Button packed_btn;
    Button placed_btn;
    Admin_Adapter admin_adapter;
    SharedPreferences prfs;
    SharedPreferences.Editor editor;
    private DrawerLayout adrawerLayout;
    private ActionBarDrawerToggle toggle;

    TextView login_Email,login_username;

    static List<CARTITEMDTO> list_status0 = new ArrayList<>();
    static List<CARTITEMDTO> list_status1 = new ArrayList<>();
    static List<CARTITEMDTO> list_status2 = new ArrayList<>();
    static List<String> uuidlist_status0 = new ArrayList<>();
    static List<String> uuidlist_status1 = new ArrayList<>();;
    static List<String> uuidlist_status2 = new ArrayList<>();;

   public void onCreate(Bundle savedInstance) {

       super.onCreate(savedInstance);
       setContentView(R.layout.admin_home_page);

       Intent intentService = new Intent(AdminHomePage.this, ListenOrder.class);
       startService(intentService);


       final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView_adminPage);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

       received_btn = (Button)findViewById(R.id.order_received_btn);
       packed_btn = (Button)findViewById(R.id.order_packed_btn);
       placed_btn = (Button)findViewById(R.id.order_placed_btn);


       final ListenOrder lorder = new ListenOrder();
       CARTITEMDTO item = new CARTITEMDTO();
       item.setUsername("shreha");
       item.setSize("L");
       item.setQuantity("6");
       item.setPackOf1("black");
       list_status0.add(item);

       received_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

            admin_adapter = new Admin_Adapter(list_status0,AdminHomePage.this,AdminHomePage.this, "received");
            recyclerView.setAdapter(admin_adapter);
           }
       });

       packed_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               admin_adapter = new Admin_Adapter(list_status1,AdminHomePage.this,AdminHomePage.this, "packed");
               recyclerView.setAdapter(admin_adapter);
           }
       });

       placed_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               admin_adapter = new Admin_Adapter(list_status2,AdminHomePage.this,AdminHomePage.this, "placed");
               recyclerView.setAdapter(admin_adapter);

           }
       });

       Toolbar my_toolbar = (Toolbar)findViewById(R.id.toolbar_view_cart_items);
       setSupportActionBar(my_toolbar);


       adrawerLayout = (DrawerLayout)findViewById(R.id.admin_drawer_layout);
       toggle = new ActionBarDrawerToggle(this,adrawerLayout,R.string.Open,R.string.Close);
       adrawerLayout.addDrawerListener(toggle);
       toggle.syncState();
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);




       NavigationView navigationView = (NavigationView) findViewById(R.id.admin_navigation_view);
       View hView =  navigationView.getHeaderView(0);

       login_Email = (TextView)hView.findViewById(R.id.e_homepage);
       login_username = (TextView)hView.findViewById(R.id.u_homepage);

       prfs = getSharedPreferences("login", MODE_PRIVATE);
       String l_username = prfs.getString("username", "");
       String l_email = prfs.getString("Email","");


       login_Email.setText(l_email);
       login_username.setText(l_username);

       Menu admin_menu = navigationView.getMenu();
       admin_menu.findItem(R.id.order_history).setVisible(false);

       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
               switch(menuItem.getItemId())
               {
                   case R.id.logout:
                       prfs = getSharedPreferences("login", MODE_PRIVATE);
                       editor = prfs.edit();
                       editor.clear();
                       editor.commit();

                       FirebaseAuth.getInstance().signOut();
                       Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                       startActivity(intent);
                       finish();

                       Toast.makeText(getApplicationContext(),"Successfully logged out",Toast.LENGTH_SHORT).show();
                       return true;
               }
               return true;
           }
       });

   }

    @Override
    public void onOrderPackedClick(int pos) {

        Query query = FirebaseDatabase.getInstance().getReference().child("Final Orders").orderByChild(uuidlist_status0.get(pos));
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void onOrderPlacedClick(int pos) {
        Query query = FirebaseDatabase.getInstance().getReference().child("Final Orders").orderByChild(uuidlist_status1.get(pos));
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void onOrderDeletedClick(int pos) {

        Query query = FirebaseDatabase.getInstance().getReference().child("Final Orders").orderByChild(uuidlist_status2.get(pos));
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public void orderDetails(int pos, String type) {

       Intent intent = new Intent(AdminHomePage.this, DisplayOrderDetails.class);
       Bundle bundle = new Bundle();
       if(type.equals("received"))
           bundle.putSerializable("SELECTED_ORDER_DETAILS",list_status0.get(pos));
       else if(type.equals("packed"))
           bundle.putSerializable("SELECTED_ORDER_DETAILS",list_status1.get(pos) );
       else
           bundle.putSerializable("SELECTED_ORDER_DETAILS",list_status2.get(pos));
       startActivity(intent);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot snapshot : dataSnapshot.getChildren())
            {
                DataSnapshot listsnapshot = snapshot.child("List");
                for (DataSnapshot dt : listsnapshot.getChildren())
                {
                    String status_val = dt.child("status").getValue().toString();
                    String prev_status_val = dt.child("previous state").getValue().toString();

                }
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}

