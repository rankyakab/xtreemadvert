package com.xtreemadvert.xtreemads;

import android.companion.CompanionDeviceManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UploadDetail extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
  //  private DrawerLayout mDrawerLayout;
 //   private ActionBarDrawerToggle mToggle;
 //   Intent i;
  private RecyclerView commentRecycler;
  private RecyclerView.Adapter recAdapter;
  private List<Comment> comList;
  private String flow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_detail);
   /*   commentRecycler = findViewById(R.id.product_viewer_comments);
       int proId =(int) getIntent().getExtras().getInt("proId");
      flow = Constant.FETCH_COMMENT_URL+"?comment_id="+proId;
      comList = new ArrayList<>();
      //loadComments();

      TextView businessname = findViewById(R.id.businessname_detail);
      String buss_name = getIntent().getExtras().getString("business_name");
      businessname.setText(buss_name);
      TextView businessaddress = findViewById(R.id.address_detail);
      String buss_address = getIntent().getExtras().getString("product_detail");
      businessaddress.setText(buss_address);
      TextView phonenumber_detail = findViewById(R.id.phonenumber_detail);
      String pho_detail = getIntent().getExtras().getString("business_number");
      phonenumber_detail.setText(pho_detail);
      TextView product_name_detail = findViewById(R.id.product_description_detail);
      String product_name = getIntent().getExtras().getString("product_name");
      product_name_detail.setText(product_name);
      TextView product_price_detail = findViewById(R.id.product_price_detail);
      String pro_price = getIntent().getExtras().getString("product_price");
      product_price_detail.setText(pro_price);
      // Picasso.with(context).load(advertListItemin.getFile_name()).into(viewHolder.post_image);
      ImageView pro_image = findViewById(R.id.product_image_detail);

      String business_image = getIntent().getExtras().getString("business_image");
      Glide.with(this).load(business_image).into(pro_image);
      */
      //  Toolbar toolbar =(Toolbar)findViewById(R.id.details_toolbar);
     //   setSupportActionBar(toolbar);
        //navigation for drawer
     //   mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        //this is for the toogling of the navigation toogle button
      //  mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
     //   mDrawerLayout.addDrawerListener(mToggle);
     //   mToggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //for the onclick of the items in the navigation
     //   NavigationView navigationView = findViewById(R.id.homeview);
      //  navigationView.setNavigationItemSelectedListener(this);
        //if(savedInstanceState==null){
            //should incase we open our app for the first time or we rotate our app or make background changes

       //     navigationView.setCheckedItem(R.id.home);
     //   }

    }

 /* private void loadComments() {
    StringRequest stringRequest = new StringRequest(Request.Method.GET, flow,
            new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
          try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i=0; i<jsonArray.length(); i++){
              JSONObject o = jsonArray.getJSONObject(i);
              Comment commente = new Comment(
                      o.getString("name"),
                      o.getString("message"),
                      o.getString("calender")




              );

              comList.add(commente);
          }
            recAdapter = new CommentRecyclerAdapter(comList,getApplicationContext());
            commentRecycler.setAdapter(recAdapter );
      }catch (JSONException e) {
          e.printStackTrace();
        }
    }},
                      new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
      }
    });

    }*/

  @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
