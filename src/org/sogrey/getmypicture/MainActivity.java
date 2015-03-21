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

	private final int IMAGE_CODE = 0;   //�����IMAGE_CODE���Լ����ⶨ���
	
	
	
	public void getPic(View v){
		//���������ͼƬ�Ļ�ȡ��


		//ʹ��intent����ϵͳ�ṩ����Ṧ�ܣ�ʹ��startActivityForResult��Ϊ�˻�ȡ�û�ѡ���ͼƬ

		Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);

		getAlbum.setType(IMAGE_TYPE);

		startActivityForResult(getAlbum, IMAGE_CODE);
	}
	
	
	
	//��дonActivityResult�Ի������Ҫ����Ϣ

	@Override

	protected void onActivityResult(int requestCode, int resultCode, Intent data){

	    if (resultCode != RESULT_OK) {        //�˴��� RESULT_OK ��ϵͳ�Զ����һ������

	        Log.e(TAG,"ActivityResult resultCode error");
	        txt.setText("ActivityResult resultCode error");
	        return;

	    }

	    Bitmap bm = null;

	    //���ĳ������ContentProvider���ṩ���� ����ͨ��ContentResolver�ӿ�

	    ContentResolver resolver = getContentResolver();

	    //�˴��������жϽ��յ�Activity�ǲ�������Ҫ���Ǹ�

	    if (requestCode == IMAGE_CODE) {

	        try {

	            Uri originalUri = data.getData();        //���ͼƬ��uri 

	            bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);        //�Եõ�bitmapͼƬ

//	���￪ʼ�ĵڶ����֣���ȡͼƬ��·����

	            String[] proj = {MediaStore.Images.Media.DATA};

	            //������android��ý�����ݿ�ķ�װ�ӿڣ�����Ŀ�Android�ĵ�

	            Cursor cursor = managedQuery(originalUri, proj, null, null, null); 

	            //���Ҹ������ ����ǻ���û�ѡ���ͼƬ������ֵ

	            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

	            //�����������ͷ ���������Ҫ����С�ĺ���������Խ��

	            cursor.moveToFirst();

	            //����������ֵ��ȡͼƬ·��

	            String path = cursor.getString(column_index);
	            txt.setText(path);
	        }catch (IOException e) {

	            Log.e(TAG,e.toString()); 
	            txt.setText(e.toString());
	        }

	    }

	}

}
