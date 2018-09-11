package com.scorpiokara.cloud.movie.camera;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.nfc.Tag;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * Created by .
 *
 * @author TaoYJ
 * @date 2018/8/21
 */
public class SurfacePreview extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = SurfacePreview.class.getSimpleName();
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Camera.Parameters mParameters;

    public SurfacePreview(Context context) {
        super(context);
        mHolder = getHolder();
        //设置透明
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        mHolder.addCallback(this);
        //不赞成设置，在3。0之前需要
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated is called");
        try {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged is called");
        try {
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {
                    if (success) {
                        initCamera();
                        camera.cancelAutoFocus();
                    }
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Error : starting camera preview" + e.getMessage());
        }
    }

    private void initCamera() {
        int previewWidth = 0;
        int previewHeight = 0;
        //获取窗口的管理器
        WindowManager wm = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        //获得窗口里面的屏幕
        Display display = wm.getDefaultDisplay();
        Camera.Parameters parameters = mCamera.getParameters();
        // 选择合适的预览尺寸
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();

            // 如果sizeList只有一个我们也没有必要做什么了，因为就他一个别无选择
        if (sizeList.size() > 1) {
            Iterator<Camera.Size> itor = sizeList.iterator();
            while (itor.hasNext()) {
                Camera.Size cur = itor.next();
                Log.d(TAG,"SupportedPictureSizes : " + cur.width + "x" + cur.height);
                if (cur.width >= previewWidth
                        && cur.height >= previewHeight) {
                    previewWidth = cur.width;
                    previewHeight = cur.height;
                    break;
                }
            }
        }

        mParameters = mCamera.getParameters();

        List<Camera.Size> pictureSizes = mParameters.getSupportedPictureSizes();
        int length = pictureSizes.size();
        for (int i = 0; i < length; i++) {
            Log.d(TAG,"SupportedPictureSizes : " + pictureSizes.get(i).width + "x" + pictureSizes.get(i).height);
        }

        mParameters.setPictureFormat(PixelFormat.JPEG);
        mParameters.setPictureSize(1920, 1080);
        //设置照片质量
        mParameters.set("jpeg-quality", 100);
        mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        setDisPaly(mParameters, mCamera);
        mCamera.setParameters(mParameters);
        mCamera.startPreview();
        mCamera.cancelAutoFocus();
    }

    private void setDisPaly(Camera.Parameters mParameters, Camera mCamera) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            setDisplayOrientation(mCamera, 90);
        } else {
            mParameters.setRotation(90);
        }

    }

    private void setDisplayOrientation(Camera mCamera, int degrees) {
        Method downPolymorphic;
        try {
            downPolymorphic = mCamera.getClass().getMethod("setDisplayOrientation", new Class[]{int.class});
            if (downPolymorphic != null) {
                downPolymorphic.invoke(mCamera, new Object[]{degrees});
            }
        } catch (Exception e) {
            Log.d(TAG, "Error : image error" + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        Log.d(TAG, "surfaceDestroyed is called");
    }

    public void takePicture(Camera.PictureCallback pictureCallback) {
        mCamera.takePicture(null, null, pictureCallback);
    }
}
