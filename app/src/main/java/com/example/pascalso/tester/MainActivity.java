package com.example.pascalso.tester;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity{
    private Camera mCamera;
    public CameraPreview mCameraPreview;
    private int REQUEST_IMAGE_CAPTURE = 1;
    private int REQUEST_GALLERY = 2;
    private static final int MEDIA_TYPE_IMAGE = 3;
    private static final int MEDIA_TYPE_VIDEO = 4;
    String mCurrentPhotoPath;
    public OnSwipeTouchListener onSwipeTouchListener;


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
        galleryClick();

        onSwipeTouchListener = new OnSwipeTouchListener(this);
    }

    public void galleryClick(){
        ImageButton gallery = (ImageButton)findViewById(R.id.gallery);
        gallery.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                dispatchGalleryIntent();
            }
        });
    }

    public void takepicClick(){
        ImageButton takepic = (ImageButton)findViewById(R.id.takepic);
        takepic.setOnClickListener(new OnClickListener(){
            public void onClick(View arg0){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                }
                catch (IOException e){

                }

                if (photoFile != null){
                    mCamera.takePicture(null, null, mPicture);

                }

            }
        });
        return;
    }

    public void receivedClick(){
        ImageButton received = (ImageButton)findViewById(R.id.received);
        //received.setOnClickListener(new );
    }
    /**
    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }
     */


    private void dispatchGalleryIntent(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(galleryIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = "file: " + image.getAbsolutePath();
        return image;
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback(){
        public void onPictureTaken(byte [] data, Camera camera){
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if(pictureFile == null){
                Log.d("TAG", "Error creating picture file"/* + e.getMessage()*/);
                return;
            }
            try{
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            }
            catch (FileNotFoundException e){
                Log.d("TAG", "File not found" + e.getMessage());
            }
            catch (IOException e){
                Log.d("TAG", "Error accessing file" + e.getMessage());
            }
        }
    };

    private static File getOutputMediaFile(int type){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Tester");
        if(!mediaStorageDir.exists()){
            if(!mediaStorageDir.mkdirs()){
                Log.d("Tester", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if(type == MEDIA_TYPE_IMAGE)
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        else if(type == MEDIA_TYPE_VIDEO)
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        else
            return null;
        return mediaFile;
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
