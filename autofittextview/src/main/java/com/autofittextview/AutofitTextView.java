package com.autofittextview;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * A TextView in which font size is automatically adjusted so that the entire text is in the area.
 * Created by hiroshi on 2015/05/19.
 */
public class AutofitTextView extends TextView {
    private static final int DEFAULT_MIN_TEXT_SIZE = 8; // in sp
    private int minTextSize = DEFAULT_MIN_TEXT_SIZE; // in sp

    public AutofitTextView(Context context) {
        super(context);
    }

    public AutofitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutofitTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setMinTextSize(int textSize) {
        this.minTextSize = textSize;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        this.resize();
    }

    private void resize() {
        final String text = this.getText().toString();
        final int padding = this.getPaddingLeft() + this.getPaddingRight();

        final float minTextSizeInPixels = this.getContext().getResources().getDisplayMetrics().scaledDensity * this.minTextSize; // in pixels
        final Paint paint = new Paint();

        // 1 pixel for safety. The value after the decimal point in textWidth may be cut off.
        final int viewWidth = this.getWidth() - padding - 1;

        float textSize = getTextSize();

        paint.setTextSize(textSize);
        float textWidth = paint.measureText(text);
        while (viewWidth <  textWidth) {
            if (minTextSizeInPixels >= textSize) {
                textSize = minTextSizeInPixels;
                break;
            }

            textSize--;

            paint.setTextSize(textSize);
            textWidth = paint.measureText(this.getText().toString());
        }

        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }
}