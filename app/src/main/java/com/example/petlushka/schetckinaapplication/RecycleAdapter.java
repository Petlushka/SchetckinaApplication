package com.example.petlushka.schetckinaapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Adapter for RecycleView
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleViewHolder> {

    private int [] mRecycle_images;
    private Context mContext;

    public class RecycleViewHolder extends RecyclerView.ViewHolder{

        ImageView picture;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            picture = (ImageView)itemView;
        }
    }

    public RecycleAdapter(Context context, int[] recycle_images) {
        this.mRecycle_images = recycle_images;
        this.mContext = context;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = new ImageView(mContext);
        int padding = (int)mContext.getResources().getDimension(R.dimen.image_padding);
        v.setPadding(0, 0, padding, 0);
        RecycleViewHolder viewHolder = new RecycleViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, v.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        Picasso.with(mContext).load(mRecycle_images[position]).into(holder.picture);
    }

    @Override
    public int getItemCount() {
        return mRecycle_images.length;
    }
}
