package com.example.horizontalview;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.AsyncWeiboRunner;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

import android.os.AsyncTask;
import android.util.Log;

public class GetEmotionsTask{
	private static String url = "https://api.weibo.com/2/emotions.json";
	private static String access_token = "2.00nTLAPCszEGRC3c05ab72daN2xu_B";
	public static void main(String[] args){
		getEmotions();
	}
	public static void getEmotions(){
		WeiboParameters params = new WeiboParameters();
		params.put("type", "face");
        params.put("language", "cnname");
		params.put("access_token", access_token);
		
		AsyncWeiboRunner.requestAsync(url, params, "GET", new RequestListener() {
			
			@Override
			public void onWeiboException(WeiboException arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public void onComplete(String arg0) {
				List<Emotions> list = new ArrayList<Emotions>();
				try {
					JSONArray jArray = new JSONArray(arg0);
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject  jObject = jArray.getJSONObject(i);
						Emotions emotions = new Emotions();
						emotions.setIcon(jObject.getString("icon"));
						emotions.setValue(jObject.getString("value"));
						list.add(emotions);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Log.i("listLength", list.size()+"");
//				new GetEmotionsTask().new MyGetEmotions(list).start();
				new GetEmotionsTask().new GetEmotionPicTask().execute(list);
			}
		} );
	}
	class MyGetEmotions extends Thread{
		List<Emotions> lists = null;
		MyGetEmotions(List<Emotions> list){
			this.lists = list;
		}
		@Override
		public void run() {
//			List<Emotions> lists = params[0];
//			String path = "/mnt/sdcard/emotions/";
			String path = "C:/emotions/";
			for (int i = 0; i < lists.size(); i++) {
				try {
					Emotions emotions = lists.get(i);
					URL url = new URL(emotions.getIcon());
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					
					File file = new File(path+emotions.getValue()+"i.gif");
					System.out.println(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
					BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String input="";
					while ((input=br.readLine())!=null) {
						bw.write(input);
					}
					bw.close();
					br.close();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			super.run();
		}
	}
	class GetEmotionPicTask extends AsyncTask<List<Emotions>, Integer, Void>{

		@Override
		protected Void doInBackground(List<Emotions>... params) {
			List<Emotions> lists = params[0];
			String path = "/mnt/sdcard/emotions/";
			File dir = new File(path);
			dir.mkdirs();
//			String path = "C:/emotions/";
			for (int i = 0; i < lists.size(); i++) {
				try {
					Emotions emotions = lists.get(i);
					
					URL url = new URL(emotions.getIcon());
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					
					File file = new File(dir,emotions.getValue()+".gif");
					file.createNewFile();
					System.out.println(file.getAbsoluteFile());
					FileOutputStream fos = new FileOutputStream(file);
					InputStream is =connection.getInputStream();
//					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter());
//					BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String input="";
					byte[] buffer = new byte[512];
					int length = 0;
					while ((length=is.read(buffer,0,buffer.length))!=-1) {
						fos.write(buffer, 0, length);
					}
					fos.close();
					is.close();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

		
	}
	/**
     * 获取微博官方表情的详细信息。
     * 
     * @param type      表情类别，表情类别，face：普通表情、ani：魔法表情、cartoon：动漫表情，默认为face。可为以下几种： 
     *                  <li> {@link #EMOTION_TYPE_FACE}
     *                  <li> {@link #EMOTION_TYPE_ANI}
     *                  <li> {@link #EMOTION_TYPE_CARTOON}
     * @param language  语言类别，cnname：、twname：，默认为cnname。 
     *                  <li> {@link #LANGUAGE_CNNAME}
     *                  <li> {@link #LANGUAGE_TWNAME}
     * @param listener  异步请求回调接口
     */
    public void emotions(String type, String language, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.put("type", type);
        params.put("language", language);
//      requestAsync(API_SERVER + "/emotions.json", params, HTTPMETHOD_GET, listener);
    }
}
