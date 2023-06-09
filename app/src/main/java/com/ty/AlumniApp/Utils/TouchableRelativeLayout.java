package com.ty.AlumniApp.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;



public class TouchableRelativeLayout extends RelativeLayout {
    public TouchableRelativeLayout(Context context) {
        super(context);
    }

    public TouchableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
