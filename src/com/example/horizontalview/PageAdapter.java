package com.example.horizontalview;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PageAdapter extends BaseAdapter{
	private Context context;
	List<Emotions> list;
	
	public PageAdapter(Context context){
		this.context = context;
	}
	
	public List<Emotions> getList() {
		return list;
	}

	public void setList(List<Emotions> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(context);
		imageView.setImageDrawable(list.get(position).getEmotion());
		
		return imageView;
	}

}
