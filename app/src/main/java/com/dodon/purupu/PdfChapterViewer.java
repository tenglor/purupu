package com.dodon.purupu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfRenderer;
import android.graphics.pdf.PdfRenderer.Page;

public class PdfChapterViewer implements ChapterViewer{

    private PdfRenderer renderer;
    private Context context;
    private Page currentPage;

    PdfChapterViewer(Context context, PdfRenderer renderer){
        this.renderer = renderer;
        this.context = context;
    }

    @Override
    public Drawable getPage(int i) {
        if(currentPage != null) currentPage.close();
        currentPage = renderer.openPage(i);
        Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(), Bitmap.Config.ARGB_8888);
        currentPage.render(bitmap, null, null, Page.RENDER_MODE_FOR_DISPLAY);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        return bitmapDrawable;
    }

    @Override
    public Drawable getFirst() {
        return getPage(0);
    }

    @Override
    public Drawable getLast() {
        return getPage(getPageCount() - 1);
    }

    @Override
    public int getPageCount() {
        return renderer.getPageCount();
    }
}
