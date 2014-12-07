package com.wht.compareview;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	private CompareView compareView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		compareView = (CompareView) findViewById(R.id.compareView);
		init() ;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void init() {
		// TODO Auto-generated method stub
		List<Compare> list = new ArrayList<Compare>();
		Compare compare = new Compare();
		
		compare.setLeftText( "100杆");
		compare.setRightText( "80杆");
		compare.setCenterText("场均杆数");
		compare.setMax(120);// 120最大值
		list.add(compare);

		compare = new Compare();
		compare.setLeftText("73%");
		compare.setRightText("43%");
		compare.setCenterText("上球道率");
		compare.setMax(100);
		list.add(compare);
		

		compare = new Compare();
		compare.setLeftText("3");
		compare.setRightText("3");
		compare.setCenterText("平均推杆");
		compare.setMax(5);
		list.add(compare);

		compare = new Compare();
		compare.setLeftText("80");
		compare.setRightText("73");
		compare.setCenterText("标准杆上果岭");
		compare.setMax(100);
		list.add(compare);
		
		compare = new Compare();
		compare.setLeftText("98");
		compare.setRightText("98");
		compare.setCenterText("测试");
		compare.setMax(100);
		list.add(compare);
		compareView.addData(list);
	}
}
