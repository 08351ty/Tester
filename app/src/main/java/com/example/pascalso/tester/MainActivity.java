package com.example.pascalso.tester;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.parse.ParseUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends Activity{

    private PreviewSurfaceView camView;
    private CameraPreview cameraPreview;
    private DrawingView drawingView;
    private Camera mCamera;
    public CameraPreviewFragment mCameraPreviewFragment;
    public String mCurrentPhotoPath;
    private static Bitmap selectedimage;
    private static final int MEDIA_TYPE_IMAGE = 3;
    private static final int MEDIA_TYPE_VIDEO = 4;
    int width;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createCameraPreview();
        //autoFocus();
        takepicClick();
        galleryClick();
        receivedClick();
    }

    private Camera getCameraInstance(){
        Camera camera= null;
        try {
            camera = Camera.open();
            Camera.Parameters params = camera.getParameters();
            List<Camera.Size> sizes = params.getSupportedPictureSizes();
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
            camera_preview.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    mCamera.autoFocus(null);
                }
            });
            camera_preview.addView(mCameraPreviewFragment);
        }
        return mCameraPreviewFragment;
    }

    private Camera.Size getBiggestPictureSize(Camera.Parameters p) {
        Camera.Size result = null;
        for (Camera.Size size : p.getSupportedPictureSizes()) {
            if (result == null) {
                result = size;
            } else {
                int resultArea = result.width * result.height;
                int newArea = size.width * size.height;
                if (size.width == 1920 && size.height == 1080) {
                    result = size;
                    width = size.width;
                    height = size.height;
                }
            }
        }
        return result;
    }

    private void galleryClick(){
        ImageButton gallery = (ImageButton)findViewById(R.id.gallery);
        gallery.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                startActivity(new Intent(MainActivity.this, AccessGalleryActivity.class));
            }
        });
    }

    private void takepicClick() {
        ImageButton takepic = (ImageButton) findViewById(R.id.takepic);
        takepic.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {

                }
                if (photoFile != null) {
                    Camera.Parameters p = mCamera.getParameters();
                    Camera.Size sizePicture = (getBiggestPictureSize(p));
                    p.setPictureSize(sizePicture.width, sizePicture.height);
                    mCamera.setParameters(p);
                    mCamera.takePicture(null, null, mPicture);
                }
            }
        });
    }

    public void receivedClick(){
        ImageButton received = (ImageButton)findViewById(R.id.received);
        received.setOnClickListener(new OnClickListener(){
            public void onClick(View arg0){
                ParseUser user = ParseUser.getCurrentUser();
                String identity = user.getString("usertype");
                if(identity.equals("tutor")){
                    startActivity(new Intent(MainActivity.this, TutorActivity.class));
                }
                else {
                    startActivity(new Intent(MainActivity.this, StudentActivity.class));
                }
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
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(selectedimage,width,height,true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                setImage(rotatedBitmap);
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

    /**
    protected void focusOnTouch(MotionEvent event) {
        if (mCamera != null) {

            mCamera.cancelAutoFocus();
            Rect focusRect = calculateTapArea(event.getX(), event.getY(), 1f);
            Rect meteringRect = calculateTapArea(event.getX(), event.getY(), 1.5f);

            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            parameters.setFocusAreas(Lists.newArrayList(new Camera.Area(focusRect, 1000)));


            if (meteringAreaSupported) {
                parameters.setMeteringAreas(Lists.newArrayList(new Camera.Area(meteringRect, 1000)));
            }


            mCamera.setParameters(parameters);
            mCamera.autoFocus(this);
        }
    }*/



    private int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
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
