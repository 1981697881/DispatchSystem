package com.dispatch.system.module.home.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


/**
 * 签名自定义view
 * <p>
 *
 * @author chenjunxu
 * @date 2020/7/23
 */
public class SignView extends View {

    private Paint paint = new Paint();
    private boolean hasSign = false;

    public SignView(Context context) {
        this(context, null);
    }

    public SignView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint.setColor(Color.BLACK);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    private Path path = new Path();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                hasSign = true;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }

    public void clear() {
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        path.reset();
        hasSign = false;
//        paint.reset();
        invalidate();
    }

    public boolean isHasSign() {
        return hasSign;
    }

//    public Bitmap buildBitmap() {
//        Bitmap bm = getDrawingCache();
//        Bitmap result = Bitmap.createBitmap(bm);
//        destroyDrawingCache();
//        return result;
//    }
}
