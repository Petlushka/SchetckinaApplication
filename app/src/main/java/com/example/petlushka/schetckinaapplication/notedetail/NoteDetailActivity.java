package com.example.petlushka.schetckinaapplication.notedetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petlushka.schetckinaapplication.R;
import com.example.petlushka.schetckinaapplication.data.FileName;
import com.example.petlushka.schetckinaapplication.data.Issue;
import com.example.petlushka.schetckinaapplication.data.Performer;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

public class NoteDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_pretender)     TextView mTvPretender;
    @BindView(R.id.tv_status)  TextView mTvStatus;
    @BindView(R.id.tv_created_date)  TextView mTvCreatedDate;
    @BindView(R.id.tv_registration_date)  TextView mRegistrationDate;
    @BindView(R.id.tv_deadline)  TextView mDeadline;
    @BindView(R.id.tv_performer)  TextView mPerformer;
    @BindView(R.id.tv_description)  TextView mDescription;
    @BindView(R.id.recycleView)  RecyclerView mRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        int id_item = intent.getIntExtra("id", 0);
        RealmConfiguration config = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<Issue> query = realm.where(Issue.class).equalTo("id", id_item);
        Issue result = query.findFirst();
        mTvPretender.setText(result.getTitle());
        mTvStatus.setText(result.getState().getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        if(result.getCreatedDate() == null){
            mRegistrationDate.setText("");
        } else {
            mRegistrationDate.setText(dateFormat.format(result.getCreatedDate()));
        }
        if(result.getDeadline() == null){
            mRegistrationDate.setText("");
        } else {
            mRegistrationDate.setText(dateFormat.format(result.getDeadline()));
        }
        StringBuilder builder = new StringBuilder();
        for(Performer performer: result.getPerformers()){
            builder.append(performer.getOrganization());
        }
        mPerformer.setText(builder.toString());
        mDescription.setText(result.getBody());
        if(result.getStartDate()==null){
            mRegistrationDate.setText("");
        } else {
            mRegistrationDate.setText(dateFormat.format(result.getStartDate()));
        }

        List<FileName> listFiles = result.getFiles();





        RecyclerView mRecycleView = (RecyclerView)findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.addItemDecoration(new VerticalItemDecoration((int) this.getResources().getDimension(R.dimen.image_padding)));
        NoteRecycleAdapter adapter = new NoteRecycleAdapter(this, listFiles);
        mRecycleView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, v.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();
    }
}
