package com.example.petlushka.schetckinaapplication.util;

import com.example.petlushka.schetckinaapplication.data.Issue;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Irina on 20.05.2016.
 */
public interface ServiceApi {

    @GET ("tickets?state=0,9,5,7,8")
    Call<List<Issue>> getInProgress();

    @GET ("tickets?state=10,6")
    Call<List<Issue>> getDone();

    @GET ("tickets?state=1,3,4")
    Call<List<Issue>> getPending();

    @GET ("tickets")
    Call<List<Issue>> getAllIssues();

}
