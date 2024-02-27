package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private TextView tervTeksti;
    private boolean vaihdettu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tervTeksti = findViewById(R.id.tvTervehdys);
    }

    public void vaihdaTeksti(View view){
        if(!vaihdettu) {
            tervTeksti.setText("Hei maailma!");
            vaihdettu = true;
        }
        else{
            tervTeksti.setText("Hello world!");
            vaihdettu = false;
        }
    }
}