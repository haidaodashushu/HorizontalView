package com.example.horizontalview;

import java.util.ArrayList;
import java.util.List;

import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import android.R.integer;
import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	private final static String TGA = "MainActivity";
	Button getEmotionsButton;
	ViewPager viewPager;
	ImageView[] dots = null;
	int[] location = new int[2];
	ImageView dotScroll;
	// 选中的圆点，即页面序号
	private int curDot;
	/** 屏幕宽度 */
	int screenWidth;
	/** 两点之间间隔 */
	int dotInterval;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getEmotionsButton = (Button) findViewById(R.id.getEmotionBtn);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		List<View> list = createViews();
		ViewPagerAdapter adapter = new ViewPagerAdapter();
		adapter.setList(list);
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new PageLinstener());

		createDot(adapter.getCount());
		getEmotionsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// GetEmotionsTask.getEmotions();
			}
		});
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		screenWidth = outMetrics.widthPixels;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		// int[] location = new int[2];
		dots[0].getLocationOnScreen(location);
		int[] location1 = new int[2];
		dots[1].getLocationOnScreen(location1);
		dotInterval = location1[0] - location[0];
		Log.i(TGA, location[0] + "##" + location[1]);

		// android.widget.AbsoluteLayout.LayoutParams params = new
		// android.widget.AbsoluteLayout.LayoutParams(dotScroll.getLayoutParams());
		// params.x = location[0];
		// params.y = location[1];
		// ((ViewGroup) this.getWindow().getDecorView()
		// .findViewById(android.R.id.content)).addView(dotScroll, params);
		// ((ViewGroup) this.getWindow().getDecorView()
		// .findViewById(android.R.id.content)).updateViewLayout(dotScroll,
		// params);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	/**
	 * 创建表情
	 * 
	 * @return
	 */
	private List<View> createViews() {
		List<View> list = new ArrayList<View>();
		int pageCount = 0;
		for (int i = 0; i < (107 / 20 + 1); i++) {
			FrameLayout frameLayout = null;
			frameLayout = (FrameLayout) LayoutInflater.from(MainActivity.this)
					.inflate(R.layout.page, null);
			GridView gridView = (GridView) frameLayout
					.findViewById(R.id.gridView);
			PageAdapter adapter = new PageAdapter(MainActivity.this);

			List<Emotions> emotions = new ArrayList<Emotions>();
			for (int j = i * 20; pageCount < 20 && j < 107; j++) {
				Emotions emotion = new Emotions();
				if (j < 10) {
					emotion.setValue("f_static_00" + j);
				} else if (j >= 10 && j < 100) {
					emotion.setValue("f_static_0" + j);
				} else if (j >= 100 && j < 1000) {
					emotion.setValue("f_static_" + j);
				}
				if (this.getResources().getIdentifier(emotion.getValue(),
						"drawable", "com.example.horizontalview") != 0) {
					pageCount++;
					emotion.setEmotion(this.getResources().getDrawable(
							(this.getResources().getIdentifier(
									emotion.getValue(), "drawable",
									"com.example.horizontalview"))));
					emotions.add(emotion);
				}

			}
			// 在设置完20个表情之后，设置删除按钮
			Emotions emotion = new Emotions();
			emotion.setEmotion(getResources().getDrawable(
					R.drawable.emotion_del_normal));
			emotions.add(emotion);
			pageCount = 0;
			adapter.setList(emotions);
			gridView.setAdapter(adapter);
			list.add(frameLayout);
		}

		return list;
	}

	/**
	 * 创建圆点
	 * 
	 * @param dotCount
	 */
	public void createDot(int dotCount) {
		dots = new ImageView[dotCount];
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.dot);
		for (int i = 0; i < dotCount; i++) {
			ImageView imageView = new ImageView(MainActivity.this);
			// LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
			// LayoutParams.WRAP_CONTENT);
			LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			imageView.setLayoutParams(params);
			imageView.setPadding(0, 0, 15, 0);

			imageView.setImageDrawable(getResources().getDrawable(
					R.drawable.dot_unselected));
			linearLayout.addView(imageView, params);
			dots[i] = imageView;
		}
		// 默认第一个选中
		((ImageView) linearLayout.getChildAt(0))
				.setImageDrawable(getResources().getDrawable(
						R.drawable.dot_selected));
		;
		curDot = 0;
		// dotScroll = new ImageView(MainActivity.this);
		// dotScroll.setImageDrawable(getResources().getDrawable(
		// R.drawable.dot_selected));
		dotScroll = (ImageView) findViewById(R.id.dotScroll);
		// LayoutParams layoutParams = new
		// LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		// MarginLayoutParams params = new
		// MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT,MarginLayoutParams.WRAP_CONTENT);
		// params.setMargins(location[0], location[1], location[0]+params.width,
		// location[1]+params.height);
		// params.setMargins(10, 100, 20, 110);
		// android.widget.LinearLayout.LayoutParams linearParams = new
		// android.widget.LinearLayout.LayoutParams(
		// android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
		// android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
		// linearParams.setMargins(0, 0, -18,0);
		// RelativeLayout rela = (RelativeLayout)findViewById(R.id.guide);
		// rela.addView(dotScroll, 0,linearParams);
		// dotScroll.setLayoutParams(layoutParams);
		// ((ViewGroup) this.getWindow().getDecorView()
		// .findViewById(android.R.id.content)).addView(dotScroll,
		// linearParams);

		// dotScroll.setVisibility(View.GONE);
	}

	/**
	 * 设置选中的圆点
	 * 
	 * @param index
	 */
	public void setCurrentDot(int index) {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.dot);
		if (index > linearLayout.getChildCount()) {
			return;
		}
		dots[index].setImageDrawable(getResources().getDrawable(
						R.drawable.dot_selected));
		dots[index].setImageDrawable(getResources().getDrawable(
						R.drawable.dot_unselected));
		curDot = index;
	}

	public void removeCurrentDot(int index) {
		dots[index].setImageDrawable(getResources().getDrawable(
				R.drawable.dot_unselected));
	}

	class ViewPagerAdapter extends PagerAdapter {
		List<View> list;

		public ViewPagerAdapter() {
			// TODO Auto-generated constructor stub
		}

		public List<View> getList() {
			return list;
		}

		public void setList(List<View> list) {
			this.list = list;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(list.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(list.get(position));
			return list.get(position);
		}
	}

	class PageLinstener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			currentDotScroll(arg0, arg1, arg2);
		}

		@Override
		public void onPageSelected(int arg0) {
			setCurrentDot(arg0);
		}

	}

	public void currentDotScroll(int position, float positionOffset,
			int positionOffsetPixels) {
		dotScroll.setVisibility(View.VISIBLE);
		removeCurrentDot(position);

		if (positionOffset != 0) {
			float offsetPixels = positionOffset * dotInterval + position
					* dotInterval;
			// ViewPropertyAnimator.animate(dotScroll).translationX(offsetPixels).setListener(null);
			ViewHelper.setTranslationX(dotScroll, offsetPixels);
		}
		// MarginLayoutParams params = new MarginLayoutParams()
	}

	public void setDotScroll(int position) {
		android.widget.LinearLayout.LayoutParams linearParams = new android.widget.LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);

		// linearParams.setMargins(100, 100, 100, 100);
		Log.i(TGA + "My", dotScroll.getLeft() + "");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
