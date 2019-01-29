package com.example.liamkelly.patient_android.studies;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.liamkelly.patient_android.R;

/**
 * TODO: document your custom view class.
 */
public class StudyPreview extends View {

    private Study mStudy;
    private Paint mTextPaint;

    public StudyPreview(Context context) {
        super(context);
        init(null, 0);
    }

    public StudyPreview(Context context, Study study) {
        super(context);
        mStudy = study;
        init(null, 0);
        invalidate();
    }

    public StudyPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public StudyPreview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(50);
    }

    public void setStudy(Study study) {
        mStudy = study;
    }

    public Study getStudy() {
        return mStudy;
    }

    public void refresh() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        if (mStudy != null) {
            canvas.drawARGB(0, 225, 225, 225);
            // canvas.drawARGB(0, 255, 0, 0);
            int yOffset = drawLineOfText(canvas, "", 0);
            yOffset = drawLineOfText(canvas, mStudy.getName(), yOffset + 10);
            yOffset = drawLineOfText(canvas, mStudy.getInstitution(), yOffset + 10);
            drawLineOfText(canvas, mStudy.getResearcher(), yOffset + 10);
        }
    }

    private int drawLineOfText(Canvas canvas, String text, int yOffset) {
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
        int start = getWidth() - bounds.width() / 2;
        int height = bounds.height();
        canvas.drawText(text, start, yOffset, mTextPaint);
        return yOffset + height;
    }

}
