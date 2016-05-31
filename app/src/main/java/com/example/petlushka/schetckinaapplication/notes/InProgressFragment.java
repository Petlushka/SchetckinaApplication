    package com.example.petlushka.schetckinaapplication.notes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petlushka.schetckinaapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InProgressFragment extends Fragment {

    private int mType;

    public InProgressFragment(){

    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_in_progress, container, false);
        RecyclerView recyclerView = (RecyclerView)fragmentView.findViewById(R.id.recycle_in_progress);
        ListNotesRecycleAdapter adapter = new ListNotesRecycleAdapter(getContext(), mType);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new HorizontalItemDecoration(10));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return fragmentView;
    }

}
