package com.xtreemadvert.xtreemads;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {
    private List<Comment> productCommentList;
    private Context context;

    public CommentRecyclerAdapter(List<Comment> comList, Context applicationContext){
        this.productCommentList = comList;
        this.context=context;

    }

    @NonNull
    @Override
    public CommentRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_details,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentRecyclerAdapter.ViewHolder viewHolder, int i) {
        final Comment proComments = productCommentList.get(i);
        viewHolder.comments.setText((CharSequence) proComments.getMessage());
        viewHolder.commenter_name.setText((CharSequence)proComments.getName());
        //viewHolder.comment_detail_dating.setText((CharSequence)proComments.getCommentCalender());
    }

    @Override
    public int getItemCount() {
        return productCommentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView comments,comment_detail_dating,commenter_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comments = itemView.findViewById(R.id.all_comment);
          //  comment_detail_dating = itemView.findViewById(R.id.comment_detail_dating);
            commenter_name = itemView.findViewById(R.id.commenter_name);
        }
    }
/*
    public CommentRecyclerAdapter(List<Comment> commentsList, Context context){
        this.productCommentList = commentsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_details,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Comment proComments = productCommentList.get(i);
        viewHolder.comments.setText(proComments.getMessage());

    }

    @Override
    public int getItemCount() {
        return productCommentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView comments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            comments = itemView.findViewById(R.id.all_comment);
        }
    }
    */
}
