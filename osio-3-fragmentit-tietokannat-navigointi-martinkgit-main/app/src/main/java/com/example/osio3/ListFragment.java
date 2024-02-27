package com.example.osio3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ListFragment extends Fragment {


    public ListFragment() {
        super(R.layout.fragment_list);
    }


    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        RecyclerView rvItemList = view.findViewById(R.id.rvPlantList);

        Plant[] dataset = MainActivity.database.plantDao().getAllPlants();
        System.out.println("Listan pituus ListFragmentissa: " + dataset.length);
        rvItemList.setAdapter(new PlantListAdapter(dataset));
        rvItemList.setLayoutManager(new LinearLayoutManager(getContext()));

        super.onViewCreated(view, savedInstanceState);

    }


}