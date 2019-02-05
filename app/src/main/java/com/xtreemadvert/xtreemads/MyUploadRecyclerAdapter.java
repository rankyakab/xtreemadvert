package com.xtreemadvert.xtreemads;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
//implemeent a new interface

import java.util.List;

import static android.content.Intent.getIntent;
import static com.xtreemadvert.xtreemads.MyRecyclerAdapter.*;

public class MyUploadRecyclerAdapter extends RecyclerView.Adapter<MyUploadRecyclerAdapter.MyViewHolder> {
    private List<String> myUploadlist;
    private List<AdvertListItem> advertListItems;
    private Context context;
    MyUploadRecyclerAdapter radap;
    public MyUploadRecyclerAdapter(List<AdvertListItem> advertListItem, Context context) {
        this.advertListItems = advertListItem;
        this.context = context;
        this.radap = this;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_upload_product,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder( MyViewHolder viewHolder, int i) {
        final AdvertListItem advertListItemin = advertListItems.get(i);

        // viewHolder.user_posted_by.setText((CharSequence) advertListItemin.getOwner());
        //viewHolder.post_time.setText((CharSequence) advertListItemin.getCat());
        viewHolder.product_description.setText((CharSequence) advertListItemin.getDetails());
        viewHolder.product_name.setText((CharSequence) advertListItemin.getItem_name());
        viewHolder.product_price.setText((CharSequence) advertListItemin.getPrice());
       // viewHolder.text_member_id.setText(advertListItemin.getId());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mem_id = advertListItemin.getId();
               // Intent intent = new Intent(context, ProfileActivity.class);
               // context.startActivity(intent);
               delete_product(mem_id);
               // Intent intent =v;
               // finish();
               // context.startActivity(v.getIntent());
               // finish();
              //  startActivity(intent);
                Toast.makeText(context.getApplicationContext(),"e dan happen",Toast.LENGTH_SHORT).show();
                radap.notifyDataSetChanged();



            }


        });
        // Picasso.with(context).load(advertListItemin.getFile_name()).into(viewHolder.post_image);
        Glide.with(context).load(advertListItemin.getFile_name()).into(viewHolder.product_image);

    }



    @Override
    public int getItemCount() {
        return advertListItems.size();
    }
    public void delete_product(int prod){
        StringRequest deleteRequest = new StringRequest(Request.Method.GET, Constant.DELETE_PRODUCT_URL+prod, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              // Intent intent = getIntent();
              //  context.startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Toast.makeText(context, "Empty comment can't be sent "+prod, Toast.LENGTH_LONG).show();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button delete;

        public ImageView product_image;
        public TextView product_name,product_description,product_price,text_member_id;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name = (TextView)itemView.findViewById(R.id.product_name);
            product_description =(TextView)itemView.findViewById(R.id.product_description);
            product_price = (TextView)itemView.findViewById(R.id.product_price);
            product_image= (ImageView)itemView.findViewById(R.id.product_image);
            text_member_id = (TextView)itemView.findViewById(R.id.text_member_id);
            delete = (Button)itemView.findViewById(R.id.delete);
        }
    }
}
