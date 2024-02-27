package com.example.drawingapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import  android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.MotionEvent;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;

public class DrawingCanvas extends View {


    private ArrayList<PaintedPath> Paths = new ArrayList<>();
    private ArrayList<PaintedCircle> points = new ArrayList<>();
    private ArrayList<PaintedText> texts = new ArrayList<>();
    private ArrayList<Integer> orderList = new ArrayList<>();
    private PaintedPath activePath;
    private PaintedCircle activePoint;
    private PaintedText activeText;


    private int selectedPaint = 0;
    private float brushWidth = 15f;
    private Paint[] paints;
    static int selectedTool = 0;
    static float x, y;
    static String text = "";


    public DrawingCanvas(Context context, @Nullable AttributeSet attributeSet){
        super(context, attributeSet);
        initializePaints();

    }

    private void initializePaints(){
        int[] colorCodes = new int[]{Color.BLUE, Color.RED,Color.GREEN, Color.CYAN
        ,Color.YELLOW, Color.MAGENTA, Color.BLACK, Color.WHITE
        };

        paints = new Paint[colorCodes.length];

        for(int i = 0; i<colorCodes.length; i++){
            int colorCode = colorCodes[i];
            Paint p = new Paint();
            p.setColor(colorCode);
            p.setAntiAlias(true);
            p.setStrokeWidth(15f);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeJoin(Paint.Join.ROUND);


            paints[i] = p;
        }
    }

    @Override
    protected void onDraw(Canvas target){

    super.onDraw(target);
    int indexT = 0;
    int indexC = 0;
    int indexP = 0;

    for(int i : orderList) {
        if (i == 0) {
            if(Paths.size()>0) {
                PaintedPath p = Paths.get(indexP);
                target.drawPath(p.path, p.paint);
                indexP++;
            }
        } else if (i == 1) {

            if(texts.size()>0) {
                PaintedText t = texts.get(indexT);
                int tx = t.point.x;
                int ty = t.point.y;
                target.drawText(t.content, tx, ty, t.paint);
                indexT++;
            }
        } else if (i == 2) {

            if(points.size()>0) {
                PaintedCircle po = points.get(indexC);
                int cx = po.point.x;
                int cy = po.point.y;
                target.drawCircle(cx, cy, po.radius, po.paint);
                indexC++;
            }
        }
    }


    }

    public boolean undoLast(){
        if(orderList.size()>0) {
            int last = orderList.get(orderList.size() - 1);
            if (last == 0) {
                Paths.remove(Paths.size() - 1);
                System.out.println("Size: "+Paths.size());
            } else if (last == 1) {
                texts.remove(texts.size() - 1);
            } else if (last == 2) {
                points.remove(points.size() - 1);
            }
            orderList.remove(orderList.size()-1);
            invalidate();
            return true;

        }
        else{
            return false;
        }
    }

    private Bitmap createBitmap(){
        Bitmap bitmap = Bitmap.createBitmap(
                getWidth(),
                getHeight(),
                Bitmap.Config.ARGB_8888);
        return bitmap;
    }

    public Bitmap getBitmapFromCanvas(){
        Canvas canvas = new Canvas();

        Bitmap bitmap = createBitmap();
        canvas.setBitmap(bitmap);

        canvas.drawColor(Color.WHITE);
        draw(canvas);
        return bitmap;
    }

    public void saveBitmap(){
        SimpleDateFormat dateFormat =  new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        String date = dateFormat.format(System.currentTimeMillis());
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "Drawing_"+ date+ ".png");
        contentValues.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

        Bitmap bitmap = getBitmapFromCanvas();

        Uri url = null;

        ContentResolver cr = getContext().getContentResolver();

        try {
            url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

            OutputStream out = cr.openOutputStream(url);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.flush();
            out.close();

            Toast.makeText(getContext(), "Saved image successfully as" +contentValues.get(MediaStore.Audio.Media.DISPLAY_NAME), Toast.LENGTH_SHORT).show();

        }catch (IOException e){
            if(url != null)cr.delete(url, null, null);
            url = null;

            Toast.makeText(getContext(), "Saving image failed", Toast.LENGTH_SHORT).show();
        }


    }

    public void changeTool(int tool){
        selectedTool = tool;
        if(tool == 1){
            textInput();
        }
    }

    public void clearCanvas() {
        Paths = new ArrayList<PaintedPath>();
        points = new ArrayList<PaintedCircle>();
        texts = new ArrayList<PaintedText>();
        orderList = new ArrayList<Integer>();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){


         x = event.getX();
         y = event.getY();


            switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                            if(selectedTool == 0){
                                Paint paint = new Paint(paints[selectedPaint]);
                                paint.setStrokeWidth(brushWidth);
                                activePath = new PaintedPath(paint);
                                activePath.path.moveTo(x, y);
                                Paths.add(activePath);
                                orderList.add(0);
                                }
                            else if(selectedTool == 2){
                                Paint paint = new Paint(paints[selectedPaint]);
                                paint.setStrokeWidth(15f);
                                activePoint = new PaintedCircle(paint, brushWidth);
                                activePoint.point.set(Math.round(x), Math.round(y));
                                points.add(activePoint);
                                orderList.add(2);
                            }
                            else if(selectedTool == 1) {

                                String content = text;
                                Paint paint = new Paint(paints[selectedPaint]);
                                paint.setStrokeWidth(5);
                                paint.setTextSize(brushWidth*2);
                                paint.setStyle(Paint.Style.FILL);
                                activeText = new PaintedText(paint, content);
                                activeText.point.set(Math.round(x), Math.round(y));
                                texts.add(activeText);
                                orderList.add(1);
                            }
                            break;

                case MotionEvent.ACTION_MOVE:
                    if(selectedTool == 0) {
                        activePath.path.lineTo(x, y);
                        break;
                    }
            }

        invalidate();
        return true;
    }

    public Paint[] getPaints(){
        return paints;
    }

    public boolean textInput(){

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());

        final TextInputEditText input = new TextInputEditText(getContext());
        input.setHint("Set text, touch canvas to insert");
        builder.setView(input);

        builder.setPositiveButton("Save text", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                text = input.getText().toString();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
        return true;

    }

    public void setActivePaintIndex(int paintNumber){
        selectedPaint=paintNumber;
    }

    public void setBrushWidth(Float f){
        System.out.println("setBrushWidth: "+f);
        brushWidth  = f;
    }

    private class PaintedCircle{
        public PaintedCircle(Paint paint, float radius){
            this.radius = radius;
            this.point = new Point();
            this.paint = paint;
        }
        public float radius;
        public Point point;
        public Paint paint;
    }

    private class PaintedText{
        public PaintedText(Paint paint, String content){
            this.content = content;
            this.point = new Point();
            this.paint = paint;
        }

        public String content;
        public Point point;
        public Paint paint;
    }

    private class PaintedPath {
        public PaintedPath(Paint paint){
            this.path = new Path();
            this.paint = paint;
        }
        public Path path;
        public Paint paint;

    }
}
