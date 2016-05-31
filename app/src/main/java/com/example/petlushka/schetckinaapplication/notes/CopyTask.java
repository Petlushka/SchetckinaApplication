package com.example.petlushka.schetckinaapplication.notes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.petlushka.schetckinaapplication.data.Issue;
import com.example.petlushka.schetckinaapplication.util.ServiceApi;

import java.io.IOException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Irina on 26.05.2016.
 */

public class CopyTask extends AsyncTask<Context, Void, Void>
{
    Context mContext;
    @Override
    protected Void doInBackground(Context... params) {
        Log.d("MyLogs", "doInBackground");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dev-contact.yalantis.com/rest/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceApi service = retrofit.create(ServiceApi.class);
        mContext = params[0];
        Call<List<Issue>> call = service.getInProgress();
        call.enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                List<Issue> temp = response.body();
                //RealmConfiguration config = new RealmConfiguration.Builder(mContext).deleteRealmIfMigrationNeeded().build();
                //Realm.setDefaultConfiguration(config);
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(temp);
                realm.cancelTransaction();
            }

            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {

            }
        });
        /*realm.beginTransaction();

        try {
            realm.copyToRealmOrUpdate(service.getAllIssues().execute().body());
            Log.d("MyLogs", "realm copy ok");
        } catch (IOException e) {
            Log.d("MyLogs", "realm copy exception");
        }
        realm.cancelTransaction();*/
        return null;
    }
}
