package com.example.osio2_bmi_laskuri;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etColorR = findViewById(R.id.etColorR);
        EditText etColorG = findViewById(R.id.etColorG);
        EditText etColorB= findViewById(R.id.etColorB);
        SeekBar sbAlpha = findViewById(R.id.sbAlpha);

        SeekBarProgressWatcher sbChanged = new SeekBarProgressWatcher();
        TextChangesWatcher textChanged = new TextChangesWatcher();

        sbAlpha.setOnSeekBarChangeListener(sbChanged);
        etColorR.addTextChangedListener(textChanged);
        etColorG.addTextChangedListener(textChanged);
        etColorB.addTextChangedListener(textChanged);
    }


    private class SeekBarProgressWatcher implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            UpdateResults();
        }


        public void onStartTrackingTouch(SeekBar seekBar) {

        }


        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }




    private class TextChangesWatcher implements TextWatcher{


        @Override
        public void afterTextChanged(Editable editable) { UpdateResults();}

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }


    }

    private void UpdateResults(){
        try{
            EditText etColorR = findViewById(R.id.etColorR);
            EditText etColorG = findViewById(R.id.etColorG);
            EditText etColorB = findViewById(R.id.etColorB);
            SeekBar sbAlpha = findViewById(R.id.sbAlpha);
            View viewResult = findViewById(R.id.viewResult);
            EditText hex = findViewById(R.id.tvHexCode);

            int alpha = sbAlpha.getProgress();
            if(correctAmount(etColorR) && correctAmount(etColorG) && correctAmount(etColorB)) {
                int red = Integer.parseInt(etColorR.getText().toString());

                int green = Integer.parseInt(etColorG.getText().toString());
                int blue = Integer.parseInt(etColorB.getText().toString());
                @ColorInt int color = Color.argb(alpha, red, green, blue);
                String hexColor = Integer.toHexString(color).substring(2);
                hex.setText(getString(R.string.hint_hex_value)+hexColor);


                viewResult.setBackgroundColor(color);
            }
            else {
                viewResult.setBackgroundColor(Color.WHITE);
                hex.setText("");
            }
        }catch (NumberFormatException e){
            return;
        }
    }

    private boolean correctAmount(EditText et){
        int i = Integer.parseInt(et.getText().toString());
        if(i>= 0 && i <= 255)
        return true;
        else{
            et.setText("");
            String newHint = getString(R.string.hint_error);
            et.setHint(newHint);

            return false;
        }
    }

}