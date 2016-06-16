package com.example.petlushka.schetckinaapplication.notes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petlushka.schetckinaapplication.R;
import com.example.petlushka.schetckinaapplication.util.ServiceApi;
import com.example.petlushka.schetckinaapplication.data.Address;
import com.example.petlushka.schetckinaapplication.data.Issue;
import com.example.petlushka.schetckinaapplication.notedetail.NoteDetailActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Irina on 20.05.2016.
 */
public class ListNotesRecycleAdapter extends RecyclerView.Adapter<ListNotesRecycleAdapter.IssueViewHolder>{

    private RealmResults<Issue> mListIssues;
    private static ItemClickListener itemClickListener;
    private Context mContext;
    private Realm mRealm;

    public ListNotesRecycleAdapter(Context context, int type){
        copyToRealM(type, context);

        mContext = context;
        RealmConfiguration config = new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        mRealm = Realm.getDefaultInstance();

        RealmQuery<Issue> query = null;
        switch(type){
            case 0:
                Log.d("MyLogs", "type 0");
                query = mRealm.where(Issue.class).equalTo("state.id", 0)
                        .or().equalTo("state.id", 9)
                        .or().equalTo("state.id", 5)
                        .or().equalTo("state.id", 7)
                        .or().equalTo("state.id", 8);
                break;
            case 1:
                Log.d("MyLogs", "type 1");
                query = mRealm.where(Issue.class).equalTo("state.id", 10)
                        .or().equalTo("state.id", 6);
                break;
            case 2:
                Log.d("MyLogs", "type 2");
                query = mRealm.where(Issue.class).equalTo("state.id", 1)
                        .or().equalTo("state.id", 3)
                        .or().equalTo("state.id", 4);
                break;

        }

        mListIssues = query.findAll();


    }

    public class IssueViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        ImageView mIcon, mLikeIcon;
        TextView mCountLike, mDescription, mAddress, mDate, mDayCount;

        public IssueViewHolder(View itemView) {
            super(itemView);
            mIcon = (ImageView)itemView.findViewById(R.id.icon_item);
            mLikeIcon = (ImageView)itemView.findViewById(R.id.icon_like);
            mCountLike = (TextView)itemView.findViewById(R.id.count_like);
            mDescription = (TextView)itemView.findViewById(R.id.description_item);
            mAddress = (TextView)itemView.findViewById(R.id.address);
            mDate = (TextView)itemView.findViewById(R.id.date);
            mDayCount = (TextView)itemView.findViewById(R.id.count_day);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Issue temp = mListIssues.get(position);
            int id = temp.getId();
            Intent intent = new Intent(mContext, NoteDetailActivity.class);
            intent.putExtra("id", id);
            mContext.startActivity(intent);

        }
    }

    @Override
    public IssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item, parent, false);
        IssueViewHolder holder = new IssueViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(IssueViewHolder holder, int position) {
        Issue temp = mListIssues.get(position);
        holder.mIcon.setImageResource(R.drawable.ic_issues);
        holder.mCountLike.setText(temp.getLikesCounter().toString());
        holder.mDescription.setText(temp.getTitle());
        Address address = temp.getUser().getAddress();

        String addressToString = "";
        try {
            if (address.getStreet().isValid())
                addressToString += address.getStreet().getStreetType().getShortName() + " " + address.getStreet().getName();
            if (address.getHouse().isValid())
                addressToString += ", " + address.getHouse().getName();
        } catch(NullPointerException e){
            Log.d("MyLogs", e.toString());
        }
        if (!address.getFlat().equals("") || !address.getFlat().equals(null)) {
            addressToString += ", " + address.getFlat();
        }
        holder.mAddress.setText(addressToString);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        holder.mDate.setText(dateFormat.format(new Date((long)temp.getCreatedDate())));
        int countDay = temp.getDeadline()/86400000;
        holder.mDayCount.setText(countDay + "");
    }

    @Override
    public int getItemCount() {
        return mListIssues.size();
    }

    public static void copyToRealM(int type, Context context){
       Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dev-contact.yalantis.com/rest/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi service = retrofit.create(ServiceApi.class);
        Call<List<Issue>> call = null;
        switch (type){
            case 0:
                call = service.getInProgress();
                break;
            case 1:
                call = service.getDone();
                break;
            case 2:
                call = service.getPending();
                break;
        }
        call.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                Realm realm = Realm.getDefaultInstance();
                List<Issue> list = response.body();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(list);
                realm.commitTransaction();
            }


            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {

            }
        });

    }
    public interface ItemClickListener{
        public void onClick(int position, View view);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
