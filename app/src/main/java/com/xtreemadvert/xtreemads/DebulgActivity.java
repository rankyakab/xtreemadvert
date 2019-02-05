package com.xtreemadvert.xtreemads;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DebulgActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView commentRecycler;
    private RecyclerView.Adapter recAdapter;
    private List<Comment> comList;
    private String flow;
    private String flower;
    private EditText commenter_name,user_comment;
    private Button send_comment;
    private ProgressDialog progressDialog;
    private  String proId;
    private static final String URL_DATAS = "http://xtreemadvert.com/version1/fetch_comments.php?comment_id=9";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debulg);
        commenter_name= (EditText)findViewById(R.id.user_name);
        user_comment= (EditText)findViewById(R.id.user_comment);
        send_comment = (Button)findViewById(R.id.sendComment);
        send_comment.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        flower = getIntent().getExtras().getString("prodId");
        flow = "http://xtreemadvert.com/version1/fetch_comments.php?comment_id="+flower;
        commentRecycler =  (RecyclerView)findViewById(R.id.product_viewer_comment_debulg);
        commentRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this); //6 create a layout manager for
        linearLayoutManager.setReverseLayout(true);//7 make the content of the layer to display on reverse
        linearLayoutManager.setStackFromEnd(true);
        commentRecycler.setLayoutManager(linearLayoutManager);
        comList = new ArrayList<>();
        loadComments();

        TextView businessname = findViewById(R.id.businessname_detail);
        String buss_name = getIntent().getExtras().getString("business_name");
        businessname.setText(buss_name);
        TextView businessaddress = findViewById(R.id.address_detail);
        String buss_address = getIntent().getExtras().getString("product_detail");
        businessaddress.setText(buss_address);
        TextView phonenumber_detail = findViewById(R.id.phonenumber_detail);
        String pho_detail = getIntent().getExtras().getString("business_number");
        phonenumber_detail.setText(pho_detail);
        TextView product_name_detail = findViewById(R.id.product_name_detail);
        String product_name = getIntent().getExtras().getString("product_name");
        product_name_detail.setText(product_name);
        TextView product_description_detail = findViewById(R.id.product_description_detail);
        String product_description = getIntent().getExtras().getString("product_description");
        product_description_detail.setText(product_description);
        TextView product_price_detail = findViewById(R.id.product_price_detail);
        String pro_price = getIntent().getExtras().getString("product_price");
        product_price_detail.setText(pro_price);
        // Picasso.with(context).load(advertListItemin.getFile_name()).into(viewHolder.post_image);
        ImageView pro_image = findViewById(R.id.product_image_detail);

         proId = getIntent().getExtras().getString("prodId");

        String business_image = getIntent().getExtras().getString("business_image");
        Glide.with(this).load(business_image).into(pro_image);
    }
    private void loadComments() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, flow,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray( response);
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject o = jsonArray.getJSONObject(i);
                                Comment commente = new Comment(
                                        o.getString("name"),
                                        o.getString("message"),
                                        o.getString("message")




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
        //3 instantiate a new volley.newrequestqueue  and pass this as the view
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //4 add the stringRequest created to the volley.newrequestqueue created
        // this guy volleys the request queue
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }

    public void uploadComment(){
        progressDialog.setMessage("uploading...");
        progressDialog.show();
       String commenter =  commenter_name.getText().toString().trim();
        final String comment = user_comment.getText().toString().trim();
        final String product_id = proId;
       if(comment!=""){
           if(commenter==""){
               commenter="Anonymous";

           }
           final String post_commenter = commenter;
                   StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.UPLOAD_COMMENT_URL, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   progressDialog.dismiss();
                   try {
                       JSONObject jsonObject = new JSONObject(response);
                       Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }

               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {

               }
           }){
               @Override
               protected Map<String, String> getParams() throws AuthFailureError {
                   Map<String,String>params = new HashMap<>();
                   params.put("commentName", post_commenter);
                   params.put("commentMessage", comment);
                   params.put("commentProductId", product_id);
                   return params;
               }
           };
           RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
           commenter_name.setText("");
           user_comment.setText("");
           finish();
           startActivity(getIntent());

       }else{
           Toast.makeText(getApplicationContext(), "Empty comment can't be sent", Toast.LENGTH_LONG).show();
           return ;
       }
    }

    @Override
    public void onClick(View v) {
        uploadComment();
    }
}
