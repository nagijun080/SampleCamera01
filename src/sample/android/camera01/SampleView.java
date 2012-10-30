package sample.android.camera01;

import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.hardware.Camera.PictureCallback;

public class SampleView extends SurfaceView implements Callback, PictureCallback{

	private Camera camera;
	
	public SampleView(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceChanged(SurfaceHolder holder, int f, int w, int h) {
		// TODO 自動生成されたメソッド・スタブ
		Camera.Parameters p = camera.getParameters();
		p.setPreviewSize(w, h);
		camera.setParameters(p);
		camera.startPreview();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO 自動生成されたメソッド・スタブ
		try {
			camera = Camera.open();
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			
		}
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO 自動生成されたメソッド・スタブ
		camera.stopPreview();
		camera.release();
	}

	public void onPictureTaken(byte[] data, Camera c) {
		// TODO 自動生成されたメソッド・スタブ
		Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length, null);
		MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bmp, "", null);
		camera.startPreview();
	}

	@Override
	public boolean onTouchEvent(MotionEvent me) {
		// TODO 自動生成されたメソッド・スタブ
		if (me.getAction() == MotionEvent.ACTION_DOWN) {
			camera.takePicture(null,null,this);
		}
		return true;
	}

}
