package com.example.pascalso.tester;

import android.app.Activity;
<<<<<<< HEAD
=======
<<<<<<< HEAD
<<<<<<< HEAD
=======
import android.app.Activity;
import android.app.ActionBar;
>>>>>>> origin/master
>>>>>>> origin/master
=======
import android.app.Activity;
import android.app.ActionBar;
>>>>>>> origin/master
>>>>>>> origin/master
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
<<<<<<< HEAD
=======
import android.os.Bundle;
import android.os.Environment;
<<<<<<< HEAD
>>>>>>> origin/master
=======
>>>>>>> origin/master
import android.net.Uri;
<<<<<<< HEAD
=======
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> origin/master
>>>>>>> origin/master
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.view.SurfaceHolder;
>>>>>>> origin/master
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
<<<<<<< HEAD
=======
import android.widget.ImageView;
<<<<<<< HEAD
>>>>>>> origin/master
=======
>>>>>>> origin/master

public class MainActivity extends Activity{

    private Camera mCamera;
    public CameraPreviewFragment mCameraPreviewFragment;
    public String mCurrentPhotoPath;
    public OnSwipeTouchListener onSwipeTouchListener;
    private int REQUEST_IMAGE_CAPTURE = 1;
    private static final int MEDIA_TYPE_IMAGE = 3;
    private static final int MEDIA_TYPE_VIDEO = 4;
    private static final int RESULT_LOAD_IMAGE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createCameraPreview();
        takepicClick();
        galleryClick();
        receivedClick();
<<<<<<< HEAD
        findViewById(R.id.received).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReceivedFragment.class));
            }
        });
=======
>>>>>>> origin/master
        /**
         mViewPager = (ViewPager) findViewById(R.id.pager);                 Create the layout where left right swipe brings user to different interfaces
         mViewPager.setOnPageChangeListener(
         new ViewPager.SimpleOnPageChangeListener() {
        @Override public void onPageSelected(int position) {
        // When swiping between pages, select the
        // corresponding tab.
        getActionBar().setSelectedNavigationItem(position);
        }
        });
         */
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
        gallery.setOnClickListener(new OnClickListener(){
            public void onClick(View arg0){
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
<<<<<<< HEAD
        received.setOnClickListener(new OnClickListener(){
            public void onClick(View arg0){
                startActivity(new Intent(MainActivity.this, ReceivedFragment.class));
=======
        received.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                setContentView(R.layout.activity_received);
                mCamera.stopPreview();
            }
        });
    }

    public void sendClick(){
        ImageButton send = (ImageButton) findViewById(R.id.sendimage);
        send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setContentView(R.layout.activity_info);
            }
        });
    }

<<<<<<< HEAD
=======
    public void sendClick(){
        ImageButton send = (ImageButton) findViewById(R.id.sendimage);
        send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setContentView(R.layout.activity_info);
>>>>>>> origin/master
            }
        });
    }

>>>>>>> origin/master
    /**
    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }
     */

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
                setContentView(R.layout.activity_selectedimage);
                Bitmap image = BitmapFactory.decodeFile(imagePath);
                ImageView imageView = (ImageView) findViewById(R.id.selectedimage);
                imageView.setImageBitmap(image);
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
