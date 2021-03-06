package com.example.obo.androidsensor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by obo on 2017/8/18.
 */

public class RoundImageSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "RoundImageSurfaceView";

    SurfaceHolder mHolder;
    Bitmap mBitmap;
    int mViewWidth;
    int mViewHeight;
    float mScaleRate;
    float mScaledHeight;
    float mScaledWidth;
    Matrix mMatrix = new Matrix();
    Paint mPaint = new Paint();

    Bitmap mBitmapNew;

    public RoundImageSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.round_image);

        mBitmapNew = Bitmap.createBitmap(mBitmap.getWidth() * 3, mBitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(mBitmapNew);
        // 绘制三张连接的图片
        canvas.drawBitmap(mBitmap, 0,0, new Paint());
        canvas.drawBitmap(mBitmap, mBitmap.getWidth(),0, new Paint());
        canvas.drawBitmap(mBitmap, mBitmap.getWidth() * 2,0, new Paint());

        Log.i(TAG, "RoundImageSurfaceView init mBitmap.width = " + mBitmap.getWidth() + " mBitmap.height = " + mBitmap.getHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mViewWidth = right - left;
            mViewHeight = bottom - top;

            mScaleRate = mViewHeight * 1f / mBitmap.getHeight() * 2;
            mScaledHeight = mViewHeight * 2;
            mScaledWidth = mBitmap.getWidth() * mScaledHeight / mBitmap.getHeight();
            // 让界面显示的高度刚好为图片高度的一半
            mMatrix.setScale(mScaleRate, mScaleRate, 0, 0);
            // 视角移动到图片的正中间
            mMatrix.postTranslate(- mScaledWidth, - mScaledHeight / 4);
            Log.i(TAG,"");
        }
        Log.i(TAG, "onLayout  mViewWidth = " + mViewWidth + "  mViewHeight = " + mViewHeight + " mScaledWidth = " + mScaledWidth + " mScaledHeight = " + mScaledHeight);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.i(TAG, "surfaceCreated");

        mHolder = surfaceHolder;
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawBitmap(mBitmapNew, mMatrix, mPaint);
        mHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
    float resultX = 0;
    float resultY = 0;
    public void setSensorValue(float x, float y , float z) {
        if (mHolder != null) {
            resultX += y * 100;
            resultY += x * 40;
            mMatrix.postTranslate(y * 100, x * 40);
            if (resultX > mScaledWidth) {
                mMatrix.postTranslate(-mScaledWidth, 0);
                resultX -= mScaledWidth;
            } else if (resultX < - mScaledWidth) {
                mMatrix.postTranslate(mScaledWidth, 0);
                resultX += mScaledWidth;
            }

            Canvas canvas = mHolder.lockCanvas();
            canvas.drawBitmap(mBitmapNew, mMatrix, mPaint);
            mHolder.unlockCanvasAndPost(canvas);
        }
    }
}
