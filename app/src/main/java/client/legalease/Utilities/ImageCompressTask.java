package client.legalease.Utilities;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import client.legalease.Interface.IImageCompressTaskListener;


public class ImageCompressTask implements Runnable {

    private Context mContext;
    private List<String> originalPaths = new ArrayList<>();
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private List<File> result = new ArrayList<>();
    private IImageCompressTaskListener mIImageCompressTaskListener;


    public ImageCompressTask(Context context, String path, IImageCompressTaskListener compressTaskListener) {

        originalPaths.add(path);
        mContext = context;

        mIImageCompressTaskListener = compressTaskListener;
    }
    public ImageCompressTask(Context context, List<String> paths, IImageCompressTaskListener compressTaskListener) {
        originalPaths = paths;
        mContext = context;
        mIImageCompressTaskListener = compressTaskListener;
    }
    @Override
    public void run() {

        try {

            //Loop through all the given paths and collect the compressed file from Util.getCompressed(Context, String)
            for (String path : originalPaths) {
                File file = Util.getCompressed(mContext, path);
                //add it!
                result.add(file);
            }
            //use Handler to post the result back to the main Thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    if(mIImageCompressTaskListener != null)
                        mIImageCompressTaskListener.onComplete(result);
                }
            });
        }catch (final IOException ex) {
            //There was an error, report the error back through the callback
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(mIImageCompressTaskListener != null)
                        mIImageCompressTaskListener.onError(ex);
                }
            });
        }
    }



//    private void rotateImage(File bitmap){
//        ExifInterface exifInterface=null;
//        try {
//            exifInterface=new ExifInterface(mediaPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
//        Matrix matrix=new Matrix();
//        switch (orientation){
//            case ExifInterface.ORIENTATION_ROTATE_90:
//                matrix.setRotate(90);
//                break;
//            case ExifInterface.ORIENTATION_ROTATE_180:
//                matrix.setRotate(180);
//                break;
//            case ExifInterface.ORIENTATION_ROTATE_270:
//                matrix.setRotate(270);
//                break;
//            default:
//        }
//        Bitmap rotetedBitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
//        img_addImage.setImageBitmap(rotetedBitmap);
//
//    }

}
