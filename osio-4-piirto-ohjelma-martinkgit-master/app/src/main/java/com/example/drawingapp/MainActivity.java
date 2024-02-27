package com.example.drawingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class MainActivity extends AppCompatActivity {

    DrawingCanvas drawingCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        drawingCanvas = findViewById(R.id.canvas);
    }

    public DrawingCanvas getActiveCanvas(){
        return drawingCanvas;
    }

    public void newFile(View view){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);

        builder.setMessage("Do you want to create new drawing?");

        builder.setPositiveButton("Yes", ((dialogInterface, i) -> drawingCanvas.clearCanvas()));

        builder.setNegativeButton("No", ((dialogInterface, i) -> {}));

        builder.show();
    }

    public void saveFile(View view){
        drawingCanvas.saveBitmap();
    }

    public void printFile(View view){
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = drawingCanvas.getBitmapFromCanvas();
        photoPrinter.printBitmap("DrawingApp - print", bitmap);

    }

    public void undo(View view){

        if(drawingCanvas.undoLast());
        else{
            Toast.makeText(this, "Nothing to undo", Toast.LENGTH_SHORT).show();
        }
    }

    public void toggleTool(int id){

        drawingCanvas.changeTool(id);
    }


}