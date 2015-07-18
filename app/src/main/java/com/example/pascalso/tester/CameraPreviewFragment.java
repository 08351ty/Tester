package com.example.pascalso.tester;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * Created by owner on 6/25/15.
 */
public class CameraPreviewFragment extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreviewFragment(Context context, Camera camera){
        super(context);
        mCamera = camera;
        mCamera.setDisplayOrientation(90);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
        setFocusable(true);
        setFocusableInTouchMode(true);

    }
    public void surfaceCreated(SurfaceHolder surfaceHolder){
        try {
            //mCamera.setPreviewDisplay(surfaceHolder);
            //mCamera.startPreview();
            Camera.Parameters p = mCamera.getParameters();
            p.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

            mCamera.setParameters(p);
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
            mCamera.autoFocus(null);
        }
        catch (IOException e){
            Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
        }
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int a, int b, int c){
        if(mHolder.getSurface() == null)
            return;


        try{
            mCamera.stopPreview();
        }
        catch (Exception e){
        }

        try{
            //mCamera.setPreviewDisplay(mHolder);
            //mCamera.startPreview();
            Camera.Parameters p = mCamera.getParameters();
            Camera.Size previewBestSize = getBestPreviewSize(b, c, p);
            //Camera.Size previewBestSize = getBiggestPreviewSize(b, c, p);
            if (previewBestSize!= null){
                p.setPreviewSize(previewBestSize.width, previewBestSize.height);
                p.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                mCamera.setParameters(p);
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.startPreview();
                mCamera.autoFocus(null);
            }
        }
        catch (IOException e){
            Log.d("ERROR", "Camera error on surfaceChanged" + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.getHolder().removeCallback(this);
        mCamera.stopPreview();
        mCamera.release();
    }

    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters){
        Camera.Size bestSize = null;
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();

        bestSize = sizeList.get(0);

        for(int i = 1; i < sizeList.size(); i++){
            if((sizeList.get(i).width * sizeList.get(i).height) >
                    (bestSize.width * bestSize.height)){
                bestSize = sizeList.get(i);
            }
        }

        return bestSize;
    }
    private Camera.Size getBiggestPreviewSize(int width, int height, Camera.Parameters p){
        Camera.Size result = null;
        for(Camera.Size size : p.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }
        return(result);
    }
}
