package com.xtreemadvert.xtreemads;

import android.app.LauncherActivity;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<AdvertListItem> advertListItems;
    private Context context;

    public MyRecyclerAdapter(List<AdvertListItem> advertListItem, Context context) {
        this.advertListItems = advertListItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_advert_layout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final AdvertListItem advertListItemin = advertListItems.get(i);

      // viewHolder.user_posted_by.setText((CharSequence) advertListItemin.getOwner());
        //viewHolder.post_time.setText((CharSequence) advertListItemin.getCat());
       viewHolder.product_description.setText((CharSequence) advertListItemin.getDetails());
      viewHolder.product_name.setText((CharSequence) advertListItemin.getItem_name());
        viewHolder.product_price.setText((CharSequence) advertListItemin.getPrice());
        viewHolder.businessname.setText((CharSequence)advertListItemin.getBusinessname());
        viewHolder.address.setText((CharSequence)advertListItemin.getAddress());
        viewHolder.phonenumber.setText((CharSequence)advertListItemin.getPhonenumber());
       // Picasso.with(context).load(advertListItemin.getFile_name()).into(viewHolder.post_image);
        Glide.with(context).load(advertListItemin.getFile_name()).into(viewHolder.product_image);
        viewHolder.linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), (CharSequence)advertListItemin.getAddress(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return advertListItems.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        //linear layout is for the onclick
        public LinearLayout linearlayout;
        public CircleImageView  post_profile_image;
        public ImageView product_image;
        public TextView product_name,product_description,product_price,businessname,address,phonenumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_name = (TextView)itemView.findViewById(R.id.product_name);
            product_description =(TextView)itemView.findViewById(R.id.product_description);
            product_price = (TextView)itemView.findViewById(R.id.product_price);
            product_image= (ImageView)itemView.findViewById(R.id.product_image);
            businessname = (TextView)itemView.findViewById(R.id.businessname);
            phonenumber=(TextView)itemView.findViewById(R.id.phonenumber);
            address = (TextView)itemView.findViewById(R.id.address);
            linearlayout = (LinearLayout)itemView.findViewById(R.id.linearlayout);










        }
    }

    //for the search bar
    public void  setFilter(ArrayList<AdvertListItem> newAdvertListItem){
        advertListItems = new  ArrayList<AdvertListItem>();
        advertListItems.addAll(newAdvertListItem );
        notifyDataSetChanged();
    }
    }
