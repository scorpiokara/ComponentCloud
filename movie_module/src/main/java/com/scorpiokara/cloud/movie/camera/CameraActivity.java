package com.scorpiokara.cloud.movie.camera;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scorpiokara.base.app.AppConfig;
import com.scorpiokara.cloud.movie.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by .
 *
 * @author TaoYJ
 * @date 2018/8/21
 */

public
@Route(path = AppConfig.CameraActivityPath)
class CameraActivity extends AppCompatActivity implements View.OnClickListener, Camera.PictureCallback {
    private static final String TAG = CameraActivity.class.getSimpleName();
    private ImageView mIvCamera;
    private FrameLayout mFlCameraPreview;

    private SurfacePreview mSurfacePreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity_camera);
        initView();
    }

    public void initView() {
        mIvCamera = findViewById(R.id.iv_camera);
        mFlCameraPreview = findViewById(R.id.fl_camera_preview);
        mIvCamera.setOnClickListener(this);

        mSurfacePreview = new SurfacePreview(this);
        mFlCameraPreview.addView(mSurfacePreview);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_camera) {
            mIvCamera.setEnabled(false);
            mSurfacePreview.takePicture(this);
        } else {

        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        File picFile = getOutputFile();
        if (picFile == null) {
            Log.d(TAG, "can not create file,check storage permission");
            return;
        }

        try {
            FileOutputStream fos = new FileOutputStream(picFile);
            fos.write(data);
            fos.close();
        } catch (java.io.IOException e) {
            Log.d(TAG, "Error accessing file " + e.getMessage());
        }

        camera.startPreview();
        mIvCamera.setEnabled(true);
        Toast.makeText(this, "拍照成功", Toast.LENGTH_SHORT).show();

    }

    private File getOutputFile() {
        File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return new File(picDir.getPath() + File.separator + time + ".jpg");
    }
}
