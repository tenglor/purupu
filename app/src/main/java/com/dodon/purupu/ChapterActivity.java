package com.dodon.purupu;

import static java.lang.String.format;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ChapterActivity extends AppCompatActivity {

    ChapterViewer chapterViewer;
    private ImageView pageView;
    int page_number = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        pageView = findViewById(R.id.page_view);
        pageView.setOnTouchListener(this::onTouch);

        String path = getIntent().getExtras().getString("path");

        Log.d("A", path);

        File file = new File(path);
        Log.d("A", String.valueOf(file.exists()));



        try {
            ParcelFileDescriptor parcel = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            PdfRenderer renderer = new PdfRenderer(parcel);
            chapterViewer = new PdfChapterViewer(getBaseContext(), renderer);
            pageView.setImageDrawable(chapterViewer.getPage(page_number));
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    private boolean onTouch(View view, MotionEvent motionEvent) {
        boolean result = true;
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("A", "Action down");
                int old_number = page_number;
                if (motionEvent.getX() < 0.2 * view.getWidth()) {
                    page_number--;
                }
                if (motionEvent.getX() > 0.8 * view.getWidth()) {
                    page_number++;
                }
                Drawable draw = chapterViewer.getPage(page_number);
                if(draw != null){
                    pageView.setImageDrawable(draw);
                } else{
                    page_number = old_number;
                    result = false;
                }
                break;
            case MotionEvent.ACTION_SCROLL:
                Log.d("A", "Action scroll");
                break;
            default:
                break;
        }
        view.performClick();
        return result;
    }
}