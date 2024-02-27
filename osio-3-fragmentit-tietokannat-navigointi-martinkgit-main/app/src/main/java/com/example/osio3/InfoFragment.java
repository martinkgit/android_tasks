package com.example.osio3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class InfoFragment extends Fragment {



    public InfoFragment() {
       super(R.layout.fragment_blank);
    }



    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView ivPlantImage = view.findViewById(R.id.ivPlantImage2);
        TextView tvPlantName = view.findViewById(R.id.tvPlantName);
        TextView tvPlantInfo = view.findViewById(R.id.tvPlantInfo);

        String plantName = getArguments().getString("plantName");

        Plant plant = MainActivity.database.plantDao().findByName(plantName);

        if(plant != null){
            ivPlantImage.setImageResource(plant.imageId);
            tvPlantName.setText(plant.name);
            tvPlantInfo.setText(plant.mainText);
        }



    }
}