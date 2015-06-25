package com.example.pascalso.tester;

import android.content.Intent;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.app.Activity;
import android.widget.ImageButton;
import android.view.View.OnClickListener;


public class MainActivity extends Activity{
    private Camera mCamera;
    private CameraPreview mCameraPreview;
    Camera.ShutterCallback shutter;
    Camera.PictureCallback raw;
    Camera.PictureCallback jpeg;
    private int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            Log.d("ERROR", "Failed to get camera " + e.getMessage());
        }

        if (mCamera != null) {
            mCameraPreview = new CameraPreview(this, mCamera);
            FrameLayout camera_preview = (FrameLayout) findViewById(R.id.camera_preview);
            camera_preview.addView(mCameraPreview);
        }

        takepicClick();
    }

    public void galleryClick(){
        ImageButton gallery = (ImageButton)findViewById(R.id.gallery);
        //gallery.setOnClickListener(new onClickListener(){

        //};
    }

    public void takepicClick(){
        ImageButton takepic = (ImageButton)findViewById(R.id.takepic);
        takepic.setOnClickListener(new OnClickListener(){
            public void onClick(View arg0){
                dispatchTakePictureIntent();
            }
        });
    }

    public void receivedClick(){
        ImageButton received = (ImageButton)findViewById(R.id.received);
        //received.setOnClickListener(new );
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    /**
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
