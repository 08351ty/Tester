package com.example.pascalso.tester;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.app.Activity;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.view.SurfaceHolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.widget.ImageView;
import android.widget.Spinner;


public class MainActivity extends Activity{

    private Camera mCamera;
    public CameraPreviewFragment mCameraPreviewFragment;
    public String mCurrentPhotoPath;
    public OnSwipeTouchListener onSwipeTouchListener;
    public ViewPager mViewPager;
    private SurfaceHolder mHolder;
    public boolean previewing = false;

    private int REQUEST_IMAGE_CAPTURE = 1;
    private int REQUEST_GALLERY = 2;
    private static final int MEDIA_TYPE_IMAGE = 3;
    private static final int MEDIA_TYPE_VIDEO = 4;
    private static final int RESULT_LOAD_IMAGE = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createCameraPreview();
        takepicClick();
        galleryClick();
        receivedClick();

        onSwipeTouchListener = new OnSwipeTouchListener(this);
        /**
        mViewPager = (ViewPager) findViewById(R.id.pager);                 Create the layout where left right swipe brings user to different interfaces
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
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
        gallery.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                dispatchGalleryIntent();
            }
        });
    }

    private void takepicClick(){
        ImageButton takepic = (ImageButton)findViewById(R.id.takepic);
        takepic.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {

                }

                if (photoFile != null) {
                    mCamera.takePicture(null, null, mPicture);
                }
            }
        });
    }

    private void homeClick(){
        ImageButton home = (ImageButton)findViewById(R.id.returnhome);
        home.setOnClickListener(new OnClickListener(){
            public void onClick(View arg0){

            }
        });
    }

    public void receivedClick(){
        ImageButton received = (ImageButton)findViewById(R.id.received);
        received.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                setContentView(R.layout.activity_received);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        //if(requestCode == REQUEST_GALLERY && resultCode == MEDIA_TYPE_IMAGE){
            Uri pickedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
            cursor.close();
            Bitmap image = BitmapFactory.decodeFile(imagePath);
            setContentView(R.layout.activity_selectedimage);
            ImageView imageView = (ImageView) findViewById(R.id.selectedimage);
            imageView.setImageBitmap(image);
            SelectedImageFragment mSelectedImageFragment = new SelectedImageFragment();

        //}
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
                setContentView(R.layout.activity_selectedimage);
                Bitmap image = BitmapFactory.decodeFile(imagePath);
                ImageView imageView = (ImageView) findViewById(R.id.selectedimage);
                imageView.setImageBitmap(image);
                sendClick();

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
