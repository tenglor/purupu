package com.dodon.purupu;

import android.graphics.drawable.Drawable;

public interface ChapterViewer {
    Drawable getPage(int i);
    Drawable getFirst();
    Drawable getLast();
    int getPageCount();
}
