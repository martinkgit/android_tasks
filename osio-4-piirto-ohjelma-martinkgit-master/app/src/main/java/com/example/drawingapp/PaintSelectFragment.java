package com.example.drawingapp;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;


public class PaintSelectFragment extends Fragment {


    LinearLayout paintContainer;
    HorizontalScrollView paintSelector;


    public PaintSelectFragment() {
        super(R.layout.fragment_paint_select);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_paint_select, container, false);
        paintContainer = view.findViewById(R.id.paintContainer);
        paintSelector = view.findViewById(R.id.paintSelector);
        SeekBar seekBar = view.findViewById(R.id.seekBar);
        MaterialButtonToggleGroup toggleGroup = view.findViewById(R.id.toggleButton);
        toggleGroup.setSingleSelection(true);

        ToggleGroupWatcher groupWatcher = new ToggleGroupWatcher();
        toggleGroup.addOnButtonCheckedListener(groupWatcher);

        SeekBarProgressWatcher sbChanged = new SeekBarProgressWatcher();
        seekBar.setOnSeekBarChangeListener(sbChanged);

        MainActivity main = (MainActivity) getActivity();
        initializePaintSelection(main.getActiveCanvas());
        toggleGroup.check(R.id.btnToolDraw);
        return view;
    }

    private class ToggleGroupWatcher implements MaterialButtonToggleGroup.OnButtonCheckedListener{

        @Override
        public void onButtonChecked (MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
            MainActivity main = (MainActivity) getActivity();
            System.out.println("Id:" + checkedId);

            if(isChecked){
            if (checkedId == R.id.btnToolDraw) {
                group.uncheck(R.id.btnToolRectangle);
                group.uncheck(R.id.btnToolCircle);
                main.toggleTool(0);

            }

            if (checkedId == R.id.btnToolRectangle) {
                group.uncheck(R.id.btnToolCircle);
                group.uncheck(R.id.btnToolDraw);
                main.toggleTool(1);


            }
            if (checkedId == R.id.btnToolCircle) {
                group.uncheck(R.id.btnToolRectangle);
                group.uncheck(R.id.btnToolDraw);
                main.toggleTool(2);

            }
        }

        }
    }


    private class SeekBarProgressWatcher implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            UpdateResults(seekBar.getProgress());
        }


        public void onStartTrackingTouch(SeekBar seekBar) {

        }


        public void onStopTrackingTouch(SeekBar seekBar) {
        seekBarStop(seekBar.getProgress());
        }
    }


    public void initializePaintSelection(DrawingCanvas canvas) {

        Paint[] paints = canvas.getPaints();


        for (int i = 0; i < paints.length; i++) {
            Paint p = paints[i];
            Button b = new Button(getContext());
            b.setBackgroundColor(p.getColor());
            b.setTag(i);
            b.setTextSize(28f);
            b.setOnClickListener(view -> {
                SelectPaint((int) view.getTag());
            });

            double contrast = ColorUtils.calculateContrast(p.getColor(), Color.BLACK);
            if (contrast < 5f) b.setTextColor(Color.WHITE);

            paintContainer.addView(b);
        }


    }

    private void seekBarStop(int i){
        MainActivity mainActivity = (MainActivity) getActivity();
        Toast.makeText(mainActivity, "Brush width is now "+i, Toast.LENGTH_SHORT).show();
    }

    private void UpdateResults(int i){
        float f = (float) i;
        System.out.println("Updateresult: "+ f);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getActiveCanvas().setBrushWidth(f);

    }

    private void SelectPaint(int paintNumber) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getActiveCanvas().setActivePaintIndex(paintNumber);

        for (int i = 0; i< paintContainer.getChildCount(); i++){
            Button b = (Button) paintContainer.getChildAt(i);
            b.setText(i == paintNumber ? "✓" : "");
        }

    }




}