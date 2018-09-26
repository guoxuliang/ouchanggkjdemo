package com.example.ouc.demo.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.ouc.demo.log.LogManager;
import com.example.ouc.demo.log.LogProxy;


/**
 * @类名称: BitmapHelper
 * @类描述: TODO(Bitmap处理的工具类)
 * @作者 fengxian
 * @日期 2013-9-6 下午1:50:16
 * 
 */
public class BitmapHelper {
    private static final LogProxy LOG_PROXY = LogManager.getLog("BitmapHelper");

    /**
     * 解码字节数组为Bitmap，按指定的高宽
     * 
     * @param data
     * @param width
     * @param height
     * @return
     */
    public static Bitmap decodeByteArray(final byte[] data, final int width, final int height) {
        try {
            BitmapFactory.Options o1 = new BitmapFactory.Options();
            o1.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, o1);

            o1.inSampleSize = calculateInSampleSize(o1, width, height);
            o1.inJustDecodeBounds = false;
            Bitmap theResult = null;
            try {
                theResult = BitmapFactory.decodeByteArray(data, 0, data.length, o1);
            } catch (OutOfMemoryError e) {
                if (theResult != null) {
                    theResult.recycle();
                    theResult = null;
                }
                System.gc();
                System.runFinalization();

                // theResult = BitmapFactory.decodeByteArray(data, 0,
                // data.length, o1);
                return null;
            }

            return theResult;
        } catch (Throwable e) {
            LOG_PROXY.error("Exception when decode bitmap. ", e);
            return null;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        LOG_PROXY.debug("reqWith: " + reqWidth + ", reqHeight: " + reqHeight);
        final int sourceHeight = options.outHeight;
        final int sourceWidth = options.outWidth;
        reqHeight = sourceHeight * reqWidth / sourceWidth;
        int inSampleSize = 1;
        LOG_PROXY.debug("Adjusted! reqWith: " + reqWidth + ", reqHeight: " + reqHeight + ", source width: " + sourceWidth
                + ", source height: " + sourceHeight);
        if (sourceHeight > reqHeight || sourceWidth > reqWidth) {
            if (sourceWidth > sourceHeight) {
                inSampleSize = Math.round((float) sourceHeight / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) sourceWidth / (float) reqWidth);
            }
        }
        final float totalPixels = sourceWidth * sourceHeight;

        // Anything more than 2x the requested pixels we'll sample down
        // further.
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        LOG_PROXY.debug("The Sample size: " + inSampleSize);
        return inSampleSize;
    }

    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        if (bitmap == null || bitmap.getWidth() == 0 || bitmap.getHeight() == 0) {
            return null;
        }

        Bitmap output = null;
        try {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);

            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            bitmap.recycle();
            return output;
        } catch (OutOfMemoryError e) {
            if (bitmap != null) {
                bitmap.recycle();
                bitmap = null;
            }
            System.gc();
            System.runFinalization();
            return null;
        }
    }

    public static Bitmap toRoundCorner1(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

}
