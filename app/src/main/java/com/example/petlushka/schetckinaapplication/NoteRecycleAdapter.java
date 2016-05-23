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

public class NoteRecycleAdapter extends RecyclerView.Adapter<NoteRecycleAdapter.RecycleViewHolder> {

    private int [] mRecycleImages;
    private Context mContext;

    public class RecycleViewHolder extends RecyclerView.ViewHolder{

        private ImageView mViewHolderImage;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            mViewHolderImage = (ImageView)itemView;
        }
    }

    public NoteRecycleAdapter(Context context, int[] recycle_images) {
        this.mRecycleImages = recycle_images;
        this.mContext = context;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = new ImageView(mContext);
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
        Picasso.with(mContext).load(mRecycleImages[position]).into(holder.mViewHolderImage);
    }

    @Override
    public int getItemCount() {
        return mRecycleImages.length;
    }
}
