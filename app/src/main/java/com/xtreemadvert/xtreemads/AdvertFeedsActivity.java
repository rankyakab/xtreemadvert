package com.xtreemadvert.xtreemads;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdvertFeedsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {
    private static final String URL_DATA = "http://xtreemadvert.com/version1/product_op.php";
   private RecyclerView all_advert_post_list;//1 this variable is for the view that needs to be recycled
    private RecyclerView.Adapter adapter; //2 this is for the adapter needed for the recycling
    private List<AdvertListItem> advertListItems; // 3 this is expecting a list items of  object type advertlistitem
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_feeds);
        all_advert_post_list = (RecyclerView)findViewById(R.id.all_advert_post_list); //4 we got the id from recycler itself
        all_advert_post_list.setHasFixedSize(true); //5  we gave all the advertlistitem object that would go into the rcycler same fixed size
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this); //6 create a layout manager for
        linearLayoutManager.setReverseLayout(true);//7 make the content of the layer to display on reverse
        linearLayoutManager.setStackFromEnd(true); //7 stacks from start to end
        all_advert_post_list.setLayoutManager(linearLayoutManager); //8 set the layout manager created from 6 to the recyclerview(all_advert_post_list)

        advertListItems = new ArrayList<>(); //9 instantiatethe advertlistitem object list
        loadRecyclerViewData(); //10 call the loadrecyclerviewdata function that recycles the recycle view


        //navigation for drawer
        //this selects the main drawlayout layout of the activity
        mDrawerLayout = (DrawerLayout)findViewById(R.id.products_drawer);

        Toolbar toolbar =(Toolbar)findViewById(R.id.adverts_toolbar);
        setSupportActionBar(toolbar);
        //this is for the toogling of the navigation toogle button
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //for the onclick of the items in the navigation
        NavigationView navigationView = findViewById(R.id.homeview);
        navigationView.setNavigationItemSelectedListener(this);

    }

    //this is for the loadrecyler (10)
    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);//1 this is for the loading indicator to start up
        progressDialog.setMessage("loading data");// this is the message while loading
        progressDialog.show(); // and this shows it up
        // 2 Instantiate a new StringRequest Object
        //from volley
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //if response sucess full
                        progressDialog.dismiss();
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject o = jsonArray.getJSONObject(i);
                                AdvertListItem advertListItem = new AdvertListItem(
                                    o.getString("cat"),
                                        o.getString("item_name"),
                                        o.getString("details"),
                                        o.getString("price"),
                                        o.getString("other_info"),
                                        o.getString("filename"),
                                        o.getString("owner"),
                                        o.getInt("id"),
                                        o.getInt("status"),
                                        o.getString("businessname"),
                                        o.getString("address"),
                                        o.getString("phonenumber")



                                );

                                advertListItems.add(advertListItem);
                            }
                            // you put the list of created advertlist Item objects to create an an adapter
                            adapter = new MyRecyclerAdapter(advertListItems, getApplicationContext());

                            //set the created adapter to the recycler view
                            all_advert_post_list.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                // if not successfull
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
        );

        //3 instantiate a new volley.newrequestqueue  and pass this as the view
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //4 add the stringRequest created to the volley.newrequestqueue created
        // this guy volleys the request queue
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    //this is the side  menu icon on the tool bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        //this is for the search on the menu_search
        MenuItem menuItem = menu.findItem(R.id.product_search);
        SearchView searchView =(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }
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
                if(SharedPreferrenceManager.getInstance(this).isLoggedIn()){
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

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
//for the search button on the action bar
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
// for the search button on the action bar
    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
       ArrayList<AdvertListItem> newAdvertlist=new ArrayList<>();
       for(AdvertListItem advertListItem : advertListItems){
           String name = advertListItem.getItem_name().toLowerCase();
           if(name.contains(newText)){
               newAdvertlist.add(advertListItem);
           }
       }
        adapter = new MyRecyclerAdapter(newAdvertlist, getApplicationContext());
        all_advert_post_list.setAdapter(adapter);

        return true;
    }
}
