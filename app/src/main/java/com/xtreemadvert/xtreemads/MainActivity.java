package com.xtreemadvert.xtreemads;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    CarouselView carouselView;
    CardView loginCards,timelineCard,signupCard;

   int[] imageSamples={R.drawable.fifth,R.drawable.second,R.drawable.third,R.drawable.fourth,R.drawable.fifth,R.drawable.sixth};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginCards = (CardView)findViewById(R.id.loginCard);
        timelineCard = (CardView)findViewById(R.id.timelineCard);
        signupCard = (CardView)findViewById(R.id.signupCard);
        loginCards.setOnClickListener(this);
        timelineCard.setOnClickListener(this);
        signupCard.setOnClickListener(this );
        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //navigation for drawer
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        //this is for the toogling of the navigation toogle button
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //for the onclick of the items in the navigation
        NavigationView navigationView = findViewById(R.id.homeview);
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState==null){
            //should incase we open our app for the first time or we rotate our app or make background changes

            navigationView.setCheckedItem(R.id.home);
        }

        //this is for the carousel images
        carouselView = (CarouselView) findViewById(R.id.caro);
        carouselView.setPageCount(imageSamples.length);

        carouselView.setImageListener(imageListener);
    }
  // for the backbutton of the navigation icon
    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

    //for the caorousel images

    ImageListener imageListener = new ImageListener() {
        @Override
      public void setImageForPosition(int position, ImageView imageView) {
          imageView.setImageResource(imageSamples[position]);
        }
    };

    //for the menu button toggle
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
   // this is for the onclick of the items in the navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Intent i;
        switch (id) {
            case R.id.home:
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;

            case R.id.adverts:
                i = new Intent(this, AdvertFeedsActivity.class);
                startActivity(i);
                break;

            case R.id.upload_Ads:
                if( SharedPreferrenceManager.getInstance(this).isLoggedIn()){
                    startActivity(new Intent (this,UploadActivity.class));
                    break;


                }else{

                    Toast.makeText(getApplicationContext(),"You have to be a registered member to upload Adverts",Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.profile:
               i = new Intent(this, ProfileActivity.class);
              startActivity(i);
              break;
            case R.id.register:
                 i = new Intent(this, RegisterActivity.class);
                startActivity(i);
                break;
            case R.id.login:
                i = new Intent(this, SigninActivity.class);
                startActivity(i);
                break;
            case R.id.logout:

                SharedPreferrenceManager.getInstance(this).logout();
                i = new Intent(this, SigninActivity.class);
                startActivity(i);
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onClick(View v) {
        if(v==signupCard){
            if(SharedPreferrenceManager.getInstance(this).isLoggedIn()){
                final String URL_MY_DATA = "http://xtreemadvert.com/version1/my_product_op.php?id="+SharedPreferrenceManager.getInstance(this).getUserId()+" ";
               // Toast.makeText(getApplicationContext(),"You are Logged in already",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),URL_MY_DATA,Toast.LENGTH_SHORT).show();

            }else{
                startActivity(new Intent (this,RegisterActivity.class));

                return;
            }

        } else if(v==timelineCard){
            startActivity(new Intent (this,AdvertFeedsActivity.class));
            return;

        }else if(v==loginCards){
            if(SharedPreferrenceManager.getInstance(this).isLoggedIn()){
                Toast.makeText(getApplicationContext(),"You are Logged in already",Toast.LENGTH_SHORT).show();
                return;

            }else{
                startActivity(new Intent (this,SigninActivity.class));
                return;
            }

        }

    }
}
