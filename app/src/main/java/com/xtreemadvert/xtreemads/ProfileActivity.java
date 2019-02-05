package com.xtreemadvert.xtreemads;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {


    private TextView textViewUsername, textViewUserEmail;
    //declare recyclerview variable
    private RecyclerView recyclerView;//1 this variable is for the view that needs to be recycled
    private RecyclerView.Adapter adapter;//2 this is for the adapter needed for the recycling
    private List<AdvertListItem> advertListItems; // 3 this is expecting a list items of  object type advertlistitem
    //declasre layout manager
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //select recycle view
        recyclerView = (RecyclerView) findViewById(R.id.my_uploaded);//4 we got the id from recycler itself
        recyclerView.setHasFixedSize(true); //5  we gave all the advertlistitem object that would go into the rcycler same fixed size
        //get a layoutmanager
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);//6 create a layout manager for recycler
         layoutManager.setReverseLayout(true);//7 make the content of the layer to display on reverse
        layoutManager.setStackFromEnd(true);//7 stacks from start to end

        //pass a layout manager to the recycler
        recyclerView.setLayoutManager(layoutManager);
        advertListItems = new ArrayList<>(); //9 instantiatethe advertlistitem object list
        //loadRecyclerViewMyData(); //10 call the loadrecyclerviewdata function that recycles the recycle view
        //i am implemting the loadrecycleviewmydata function from inside the main






       progressDialog = new ProgressDialog(this);//1 this is for the loading indicator to start up
        progressDialog.setMessage("loading data");// this is the message while loading
        progressDialog.show(); // and this shows it up
        // 2 Instantiate a new StringRequest Object
        loadRecyclerViewMyData() ;
        /*
        //from volley
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_MY_DATA,
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
                            adapter = new MyUploadRecyclerAdapter(advertListItems, getApplicationContext());

                            //set the created adapter to the recycler view
                            recyclerView.setAdapter(adapter);
                            Toast.makeText(getApplicationContext(),"3",Toast.LENGTH_SHORT).show();

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
        ){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ids",MEM.toString());

                return params;
            }
        };

        //3 instantiate a new volley.newrequestqueue  and pass this as the view
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //4 add the stringRequest created to the volley.newrequestqueue created
        // this guy volleys the request queue
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);




*/






        //it stops here


        if(!SharedPreferrenceManager.getInstance(this).isLoggedIn()){
            finish();

            startActivity(new Intent (this,SigninActivity.class));
            return;
        }
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewUserEmail = (TextView) findViewById(R.id.textViewUseremail);


        textViewUserEmail.setText(SharedPreferrenceManager.getInstance(this).getUserEmail());
        textViewUsername.setText(SharedPreferrenceManager.getInstance(this).getUsername());
    }


    //this is for the loadrecyler (10)
    private void loadRecyclerViewMyData() {
        final Integer MEM =  SharedPreferrenceManager.getInstance(this).getUserId();
        final String IDPS = String.valueOf(SharedPreferrenceManager.getInstance(this).getUserId());
       // final ProgressDialog progressDialog = new ProgressDialog(this);//1 this is for the loading indicator to start up
       // progressDialog.setMessage("loading data");// this is the message while loading
      //  progressDialog.show(); // and this shows it up
        // 2 Instantiate a new StringRequest Object
        //from volley
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constant.URL_MY_DATA+"?ids="+IDPS,
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
                                        o.getString("phonenumber"),
                                        o.getString("comment"),
                                       o.getString("comment_name"),
                                        o.getString("comment_dating")






                                );

                                advertListItems.add(advertListItem);
                            }
                            // you put the list of created advertlist Item objects to create an an adapter
                           adapter = new MyUploadRecyclerAdapter(advertListItems, getApplicationContext());

                            //set the created adapter to the recycler view
                           recyclerView.setAdapter(adapter);
                           // Toast.makeText(getApplicationContext(),"mmm" ,Toast.LENGTH_SHORT).show();

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




    //for deleting item
    public  void delete_product(int mem_id){
        Toast.makeText(this, "esydd",Toast.LENGTH_SHORT).show();

    }

    //this is the side  menu icon on the tool bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.side_menu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch( item.getItemId()){
            case R.id.menu_logout:
                SharedPreferrenceManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, MainActivity.class ));
                break;
        }

       return true;
    }
}
