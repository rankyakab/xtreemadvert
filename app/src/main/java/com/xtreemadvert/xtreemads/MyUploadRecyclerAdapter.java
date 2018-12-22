package com.xtreemadvert.xtreemads;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.bumptech.glide.Glide;
//implemeent a new interface

import java.util.List;

import static com.xtreemadvert.xtreemads.MyRecyclerAdapter.*;

public class MyUploadRecyclerAdapter extends RecyclerView.Adapter<MyUploadRecyclerAdapter.MyViewHolder> {
    private List<String> myUploadlist;
    private List<AdvertListItem> advertListItems;
    private Context context;

    public MyUploadRecyclerAdapter(List<AdvertListItem> advertListItem, Context context) {
        this.advertListItems = advertListItem;
        this.context = context;
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
        viewHolder.text_member_id.setText(advertListItemin.getFile_name());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mem_id = advertListItemin.getId();
                ProfileActivity.delete_product(mem_id);

                Toast.makeText(context.getApplicationContext(), advertListItemin.getFile_name(),Toast.LENGTH_SHORT).show();

            }


        });
        // Picasso.with(context).load(advertListItemin.getFile_name()).into(viewHolder.post_image);
        Glide.with(context).load(advertListItemin.getFile_name()).into(viewHolder.product_image);

    }



    @Override
    public int getItemCount() {
        return advertListItems.size();
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
