package com.example.pascalso.tester;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by owner on 6/25/15.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera){
        super(context);
        mCamera = camera;
        mCamera.setDisplayOrientation(90);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

    }
    public void surfaceCreated(SurfaceHolder surfaceHolder){
        try{
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
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
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        }
        catch (IOException e){
            Log.d("ERROR", "Camera error on surfaceChanged" + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder){
        mCamera.stopPreview();
        mCamera.release();
    }
}
