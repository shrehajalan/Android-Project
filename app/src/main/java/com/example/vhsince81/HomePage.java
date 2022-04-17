package com.example.vhsince81;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.vhsince81.POJOpackage.PojoDTO;
import com.google.firebase.auth.FirebaseAuth;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class HomePage extends AppCompatActivity {

    private ViewPager mViewPager;
    private LinearLayout mDotsLayout;
    private SliderViewPagerAdapter mViewPagerAdapter;
    private int[] mLayouts;
    private TextView[] mDots;
    String[] SPINNERLIST = {"SOCKS","VH_WOMEN_ATHLEISURE","VH_ACTIVE_WEAR_MEN","VH_MEN_ATHLEISURE"};
    Button nextButton;
    String selected_category;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    TextView login_Email;
    TextView login_username;
    SharedPreferences prfs;
    SharedPreferences.Editor editor;
    boolean boolean_cart = true;
    boolean boolean_btn = true;

    ViewPager.OnPageChangeListener  viewPagerPageChangeListener = new ViewPager.OnPageChangeListener()
    {

       @Override
       public void onPageSelected(int position){
           addBottomDots(position);
       }

       public void onPageScrolled(int arg1, float arg2, int arg3)
       {}


       @Override
       public void onPageScrollStateChanged(int arg)
       {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_homepage);
        setSupportActionBar(toolbar);


        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView image = (ImageView)findViewById(R.id.cartImage_actionBar);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(boolean_cart)
                {
                    Intent intent = new Intent(HomePage.this, View_Cart_Items.class);
                    startActivity(intent);
                    boolean_cart = false;

                }
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        MaterialBetterSpinner betterSpinner = (MaterialBetterSpinner)findViewById(R.id.android_spinner);
        betterSpinner.setAdapter(arrayAdapter);
        betterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                 @Override
                                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                     selected_category = parent.getItemAtPosition(position).toString();
                                                 }
                                             });

        mViewPager = (ViewPager)findViewById(R.id.view_pager_homepage);
        mDotsLayout = (LinearLayout)findViewById(R.id.layout_dots_homepage);
        mLayouts = new int[]{R.drawable.final_athleisure_low_image2, R.drawable.final_athleisure_low_image3, R.drawable.final_athleisure_low_image4};
        addBottomDots(0);
        mViewPagerAdapter = new SliderViewPagerAdapter(this, mLayouts);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        nextButton  = findViewById(R.id.homepageButton);


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View hView =  navigationView.getHeaderView(0);
        //TextView nav_user = (TextView)hView.findViewById(R.id.navigation_view);
        login_Email = (TextView)hView.findViewById(R.id.e_homepage);
        login_username = (TextView)hView.findViewById(R.id.u_homepage);

        prfs = getSharedPreferences("login", MODE_PRIVATE);
        String l_username = prfs.getString("username", "");
        String l_email = prfs.getString("Email","");

        login_Email.setText(l_email);
        login_username.setText(l_username);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.order_history:
                        return true;

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

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected_category != null && boolean_btn)
                {
                    Intent intent = new Intent(getApplicationContext(), CategoryListDisplay.class);
                    intent.putExtra("SELECTED_CAT",selected_category);
                    startActivity(intent);
                    boolean_btn = false;
                }
                else
                    Toast.makeText(getApplicationContext(),"Select Category",Toast.LENGTH_LONG).show();

            }
        });


    }
    private void addBottomDots(int currentPage) {
        mDots = new TextView[mLayouts.length];
        mDotsLayout.removeAllViews();
        for (int i= 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.dot_slider_inactive));
            mDotsLayout.addView(mDots[i]);
        }

        if (mDots.length > 0)
        mDots[currentPage].setTextColor(getResources().getColor(R.color.dot_slider_active));
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
    @CallSuper
    protected void onResume()
    {
        super.onResume();
        boolean_cart = true;
        boolean_btn =true;
    }
}
