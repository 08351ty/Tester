package com.example.pascalso.tester;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
    public CameraPreviewFragment mCameraPreviewFragment;
    public String mCurrentPhotoPath;
    private static Bitmap selectedimage;
    private static final int MEDIA_TYPE_IMAGE = 3;
    private static final int MEDIA_TYPE_VIDEO = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createCameraPreview();
        takepicClick();
        galleryClick();
        receivedClick();

    }
    private Camera getCameraInstance(){
        Camera camera= null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            Log.d("ERROR", "Failed to get camera " + e.getMessage());
        }
        return camera;
    }

    private CameraPreviewFragment createCameraPreview(){
        mCamera = getCameraInstance();
        if(mCamera != null){
            mCameraPreviewFragment = new CameraPreviewFragment(this, mCamera);
            FrameLayout camera_preview = (FrameLayout) findViewById(R.id.camera_preview);
            camera_preview.addView(mCameraPreviewFragment);
        }
        return mCameraPreviewFragment;
    }

    private void galleryClick(){
        ImageButton gallery = (ImageButton)findViewById(R.id.gallery);
        gallery.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                startActivity(new Intent(MainActivity.this, AccessGalleryActivity.class));
            }
        });
    }

    private void takepicClick(){
        ImageButton takepic = (ImageButton)findViewById(R.id.takepic);
        takepic.setOnClickListener(new OnClickListener(){
            public void onClick(View arg0){
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                }catch (IOException e){

                }
                if (photoFile != null) {
                    mCamera.takePicture(null, null, mPicture);
                }
            }
        });
    }

    public void receivedClick(){
        ImageButton received = (ImageButton)findViewById(R.id.received);
        received.setOnClickListener(new OnClickListener(){
            public void onClick(View arg0){
                startActivity(new Intent(MainActivity.this, ReceivedFragment.class));
            }
        });
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
                Log.d("TAG", "Error creating picture file" /*+ e.getMessage()*/);
                return;
            }
            try{
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), "Tester");
                String imagePath = mediaStorageDir.getPath() + File.separator
                        + "IMG_" + timeStamp + ".jpg";
                MainActivity.this.sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri
                        .parse("file://" + imagePath)));
                selectedimage = BitmapFactory.decodeFile(imagePath);
                setImage(selectedimage);
                Intent startSelectedImageFragment = new Intent(MainActivity.this, SelectedImageFragment.class);
                startSelectedImageFragment.putExtra("calling-activity", ActivityConstants.MAIN_ACTIVITY);
                startActivity(startSelectedImageFragment);
            }
            catch (FileNotFoundException e){
                Log.d("TAG", "File not found" + e.getMessage());
            }
            catch (IOException e){
                Log.d("TAG", "Error accessing file" + e.getMessage());
            }
        }
    };

    public static Bitmap getImage(){
        return selectedimage;
    }

    public void setImage(Bitmap image){
        selectedimage = image;
    }

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

    protected void onStart(){
        super.onStart();
    }

    protected void onPause(){
        super.onPause();
        mCamera.stopPreview();
    }

    protected void onResume(){
        super.onResume();
        mCamera.startPreview();
    }

    protected void onRestart(){
        super.onRestart();
        setContentView(R.layout.activity_main);
        createCameraPreview();
        takepicClick();
        galleryClick();
        receivedClick();
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
