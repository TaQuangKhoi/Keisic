package com.taquangkhoi.keisic;

import android.view.View;

public interface ItemClickListener {
    void onClick(View view, int position, boolean isLongClick) throws InterruptedException;
}
