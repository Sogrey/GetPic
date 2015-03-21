package org.sogrey.getmypicture;

import java.io.IOException;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			initView(rootView);
			return rootView;
		}

		

		
	}
static TextView txt ;
	private static void initView(View rootView) {
		txt  =(TextView) rootView.findViewById(R.id.txt);
		
	}
	
	private final String IMAGE_TYPE = "image/*";
	private final String TAG = "TAG";

	private final int IMAGE_CODE = 0;   //这里的IMAGE_CODE是自己任意定义的
	
	
	
	public void getPic(View v){
		//首先是相册图片的获取：


		//使用intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图片

		Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);

		getAlbum.setType(IMAGE_TYPE);

		startActivityForResult(getAlbum, IMAGE_CODE);
	}
	
	
	
	//重写onActivityResult以获得你需要的信息

	@Override

	protected void onActivityResult(int requestCode, int resultCode, Intent data){

	    if (resultCode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量

	        Log.e(TAG,"ActivityResult resultCode error");
	        txt.setText("ActivityResult resultCode error");
	        return;

	    }

	    Bitmap bm = null;

	    //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口

	    ContentResolver resolver = getContentResolver();

	    //此处的用于判断接收的Activity是不是你想要的那个

	    if (requestCode == IMAGE_CODE) {

	        try {

	            Uri originalUri = data.getData();        //获得图片的uri 

	            bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);        //显得到bitmap图片

//	这里开始的第二部分，获取图片的路径：

	            String[] proj = {MediaStore.Images.Media.DATA};

	            //好像是android多媒体数据库的封装接口，具体的看Android文档

	            Cursor cursor = managedQuery(originalUri, proj, null, null, null); 

	            //按我个人理解 这个是获得用户选择的图片的索引值

	            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

	            //将光标移至开头 ，这个很重要，不小心很容易引起越界

	            cursor.moveToFirst();

	            //最后根据索引值获取图片路径

	            String path = cursor.getString(column_index);
	            txt.setText(path);
	        }catch (IOException e) {

	            Log.e(TAG,e.toString()); 
	            txt.setText(e.toString());
	        }

	    }

	}

}
