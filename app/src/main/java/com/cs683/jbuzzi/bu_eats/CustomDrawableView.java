package com.cs683.jbuzzi.bu_eats;

import android.view.View;
import android.graphics.drawable.ShapeDrawable;
import android.content.Context;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.Canvas;

public class CustomDrawableView extends View {
    private ShapeDrawable face;
    private ShapeDrawable leftEye;
    private ShapeDrawable rightEye;
    private ShapeDrawable mouth;

    public CustomDrawableView(Context context) {
        super(context);
        final int yellow = 0xffffff00;
        final int black = 0xff000000;

        face = new ShapeDrawable(new OvalShape());
        face.getPaint().setColor(yellow);
        face.setBounds(370, 370, 670, 670);

        leftEye = new ShapeDrawable(new OvalShape());
        leftEye.getPaint().setColor(black);
        leftEye.setBounds(450, 450, 480, 480);

        rightEye = new ShapeDrawable(new OvalShape());
        rightEye.getPaint().setColor(black);
        rightEye.setBounds(550, 450, 580, 480);

        mouth = new ShapeDrawable(new ArcShape(180, 180));
        mouth.getPaint().setColor(black);
        mouth.setBounds(470, 550, 570, 650);
    }

    protected void onDraw(Canvas canvas) {
        face.draw(canvas);
        leftEye.draw(canvas);
        rightEye.draw(canvas);
        mouth.draw(canvas);
    }
}