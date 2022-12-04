package com.dodon.purupu;

import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfRenderer;
import android.graphics.pdf.PdfRenderer.Page;

public class PdfChapterViewer implements ChapterViewer{

    private PdfRenderer renderer;

    PdfChapterViewer(PdfRenderer renderer){
        this.renderer = renderer;
    }

    @Override
    public Drawable getPage(int i) {
        Page page = renderer.openPage(i);
        return null;
    }

    @Override
    public int getPageCount() {
        return renderer.getPageCount();
    }
}
